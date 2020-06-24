package com.xue.study.tablestore;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.core.protocol.Search;
import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;
import com.alicloud.openservices.tablestore.model.search.*;
import com.alicloud.openservices.tablestore.model.search.query.ExistsQuery;
import com.xue.study.config.TSConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * -- SDK
 * https://help.aliyun.com/document_detail/43013.html?spm=a2c4g.11186623.4.6.61573505VQSFNi
 *
 */

@Component
public class TableStoreHelper {
    private static String endPoint;
    private static String accessId;
    private static String accessKey;
    private static String instanceName;
    private static ClientConfiguration clientConfiguration = TSConfig.getClientConfiguration();

    static {
        endPoint = TSConfig.TS_ENDPOINT;
        accessId = TSConfig.TS_ACCESSID;
        accessKey = TSConfig.TS_ACCESSKEY;
        instanceName = TSConfig.TS_INSTANCENAME;
    }

    // TODO: 2019/9/12 0012 采用池化 
    private static SyncClient syncClient;

    public static SyncClient getSyncClient(){
        if(null == syncClient){
            synchronized (SyncClient.class){
                if(null == syncClient){
                    syncClient = new SyncClient(endPoint, accessId, accessKey, instanceName, clientConfiguration);
                }
            }
        }
        return syncClient;
    }

    public boolean createTable(SyncClient client, String tabName){
        return false;
    }


//    ******************************************************************************************************************

//    /**
//     *
//     * 创建表
//     * 异步插入数据
//     * 按时间范围（属性列）查找数据
//     *
//     */
//
//    public void createTable1(SyncClient client, String tabName){
//        TableMeta tableMeta = new TableMeta(tabName);
//
//        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("day", PrimaryKeyType.STRING));
//        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("id", PrimaryKeyType.INTEGER, PrimaryKeyOption.AUTO_INCREMENT));
//
//        int livingTime = -1;
//        int maxVersion = 1;
//        TableOptions tableOptions = new TableOptions(livingTime, maxVersion);
//        CreateTableRequest ctr = new CreateTableRequest(tableMeta, tableOptions);
//        client.createTable(ctr);
//
//        //**********************************************************
////        client.search();// TODO: 2019/9/12 0012 索引查询
////        client.getRange();// TODO: 2019/9/12 0012 范围查询
//
////        ListTableResponse listTableResponse = client.listTable();
////        List<String> tabs = listTableResponse.getTableNames();
////        for (String tab : tabs) {
////            System.out.println("name-*"+tab);
////        }
////        if(tabs.contains("ts_tlgf3d942jhl00000")){
////            System.out.println("ts_tlgf3d942jhl00000");
////        }
//
////        client.listTable();
////        client.batchGetRow();// TODO: 2019/9/12 0012
////        client.getRange();// TODO: 2019/9/12 0012
////        client.search();// TODO: 2019/9/12 0012
//
////        client.createSearchIndex();
////        client.deleteSearchIndex();
//
////        client.asAsyncClient();
////        client.computeSplitsBySize();
////        client.shutdown();
//
//    }
    
    //统计一个表中的数据量
    public void countTableRows(){
        // TODO: 2020/3/19 0019      
    }


    public void findMsg(SyncClient syncClient ,String startDay, String endDay){
        RangeRowQueryCriteria rangeRowQueryCriteria = new RangeRowQueryCriteria("ts_test");

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("day", PrimaryKeyValue.fromString(startDay));
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.INF_MIN);
        rangeRowQueryCriteria.setInclusiveStartPrimaryKey(primaryKeyBuilder.build());

        primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("day", PrimaryKeyValue.fromString(endDay));
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.INF_MAX);
        rangeRowQueryCriteria.setExclusiveEndPrimaryKey(primaryKeyBuilder.build());
//        rangeRowQueryCriteria.setTimeRange();

        rangeRowQueryCriteria.setMaxVersions(1);

        while (true){
            GetRangeResponse getRangeResponse = syncClient.getRange(new GetRangeRequest(rangeRowQueryCriteria));
            for (Row row : getRangeResponse.getRows()) {
                System.out.println("row-"+row.toString() + "*msg-"+row.getColumn("msg").get(0).getValue());
            }

            if (null != getRangeResponse.getNextStartPrimaryKey()) {
                rangeRowQueryCriteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            }else {
                break;
            }
        }
    }

    /**
     * 插入一行
     * @param client
     * @param pkValue
     */

    /***************************************
     *  day id + msgId msg datetime
     *
     *******************************************/
    public void putRow(SyncClient client) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();


        //主键列
        primaryKeyBuilder.addPrimaryKeyColumn("day", PrimaryKeyValue.fromString("20190912")); //PRIMARY_KEY_NAME
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.AUTO_INCREMENT);
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange("ts_tlgf3d942jhl00000", primaryKey); //TABLE_NAME

        //加入一些属性列
        String datetime = "2019-09-12 12:58:58";
        String msg = "84 43 30 12 a8 59 59 59 xue";
        primaryKeyBuilder.addPrimaryKeyColumn("msgId", PrimaryKeyValue.fromString("18000BD1")); //PRIMARY_KEY_NAME
        rowPutChange.addColumn(new Column("msg", ColumnValue.fromString(msg)));
        rowPutChange.addColumn(new Column("datetime", ColumnValue.fromString(datetime)));

        client.putRow(new PutRowRequest(rowPutChange));
        System.out.println("++++++++++++++++success+++++++++++++++++++");
    }

    /**
     * 条件过滤查询
     * @param client
     *
     */
    public void getRow1(SyncClient client) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("day", PrimaryKeyValue.fromString("2019012"));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 读一行
//        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria("ts_data_beta2", primaryKey);
//        // 设置读取最新版本
//        criteria.setMaxVersions(1);
//
//        // 设置过滤器, 当Col0的值为0时返回该行.
//        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter("msg",
//                SingleColumnValueFilter.CompareOperator.EQUAL, ColumnValue.fromLong(0));
//        // 如果不存在Col0这一列, 也不返回.
//        singleColumnValueFilter.setPassIfMissing(false);
//        criteria.setFilter(singleColumnValueFilter);
//
//        GetRowResponse getRowResponse = client.getRow(new GetRowRequest(criteria));
//        Row row = getRowResponse.getRow();
//
//        System.out.println("读取完毕, 结果为: ");
//        System.out.println(row);
    }

    //行转列
    public JSONObject testGetRange(SyncClient client, String params){
        RangeRowQueryCriteria rangeRowQueryCriteria = new RangeRowQueryCriteria("ts_eh02_beta");

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("partId", PrimaryKeyValue.fromString("MF000017201912"));
        primaryKeyBuilder.addPrimaryKeyColumn("sn", PrimaryKeyValue.fromString("MF000017"));
        primaryKeyBuilder.addPrimaryKeyColumn("datetime", PrimaryKeyValue.fromString("20191212182636"));
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.INF_MIN);
        rangeRowQueryCriteria.setInclusiveStartPrimaryKey(primaryKeyBuilder.build());

        primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("partId", PrimaryKeyValue.fromString("MF000017201912"));
        primaryKeyBuilder.addPrimaryKeyColumn("sn", PrimaryKeyValue.fromString("MF000017"));
        primaryKeyBuilder.addPrimaryKeyColumn("datetime", PrimaryKeyValue.fromString("20191212182716"));
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.INF_MAX);
        rangeRowQueryCriteria.setExclusiveEndPrimaryKey(primaryKeyBuilder.build());

        rangeRowQueryCriteria.addColumnsToGet(params.split(","));//添加column数组
        rangeRowQueryCriteria.setMaxVersions(1);

        Map<String, List<String>> points = new HashMap<>();//创建物理量的集合
        for (String s : params.split(",")) {
            points.put(s, new ArrayList<>());
        }

        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();

        while (true){
            GetRangeResponse getRangeResponse = client.getRange(new GetRangeRequest(rangeRowQueryCriteria)); //
            for (Row row : getRangeResponse.getRows()) {

                //保存至内存缓存
                String date = row.getPrimaryKey().getPrimaryKeyColumn("datetime").getValue().toString();
                for (Column column : row.getColumns()) {
                    for (String s : params.split(",")) {
                        if(s.equalsIgnoreCase(column.getName())){
                            json.put(s, column.getValue().toString()+"##"+date);

                            array.add(json);
                            json.clear();
                        }
                    }
                }
            }

            //必须要
            if (null != getRangeResponse.getNextStartPrimaryKey()) {
                rangeRowQueryCriteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            }else {
                break;
            }
        }

        //控制台打印内存数据
        System.out.println(array.toString());

        //组装绘图样式
        for (Object o : array) {
            JSONObject obj = (JSONObject)o;
            for (String s : params.split(",")) {
                if(obj.containsKey(s)){
                    points.get(s).add(obj.getString(s));
                }
            }
        }
        array.clear();

        //返回给前台
        JSONObject result = new JSONObject();
        for (Map.Entry<String, List<String>> map : points.entrySet()) {
            result.put(map.getKey(), map.getValue().toString());
        }
        points.clear();

        return result;
    }

    public static void main(String[] args) {
        SyncClient client = TableStoreHelper.getSyncClient();
        getSequence(client);
    }

    //时间序列 ok
    public static void getSequence(SyncClient client){
        RangeRowQueryCriteria rangeRowQueryCriteria = new RangeRowQueryCriteria("ts_msg_beta");

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("partId", PrimaryKeyValue.fromString("MF000001201912"));
        primaryKeyBuilder.addPrimaryKeyColumn("sn", PrimaryKeyValue.fromString("MF000001"));
        primaryKeyBuilder.addPrimaryKeyColumn("datetime", PrimaryKeyValue.fromString("20191215000000"));
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.INF_MIN);
        rangeRowQueryCriteria.setInclusiveStartPrimaryKey(primaryKeyBuilder.build());

        primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("partId", PrimaryKeyValue.fromString("MF000001201912"));
        primaryKeyBuilder.addPrimaryKeyColumn("sn", PrimaryKeyValue.fromString("MF000001"));
        primaryKeyBuilder.addPrimaryKeyColumn("datetime", PrimaryKeyValue.fromString("20191216232359"));
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.INF_MAX);
        rangeRowQueryCriteria.setExclusiveEndPrimaryKey(primaryKeyBuilder.build());

        rangeRowQueryCriteria.setMaxVersions(1);

        while (true){
            GetRangeResponse getRangeResponse = client.getRange(new GetRangeRequest(rangeRowQueryCriteria));
            for (Row row : getRangeResponse.getRows()) {
                System.out.println("#####start##############");
                System.out.println(row);
                System.out.println("##########end#############");
            }

            if (null != getRangeResponse.getNextStartPrimaryKey()) {
                rangeRowQueryCriteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            }else {
                break;
            }
        }

    }

    //查询一张表中的所有列
    public void getAllCols(SyncClient client){
        SearchQuery searchQuery = new SearchQuery();
        ExistsQuery existQuery = new ExistsQuery(); //设置查询类型为ExistsQuery。
        existQuery.setFieldName("VolFromAuxMotor");
        searchQuery.setQuery(existQuery);
        SearchRequest searchRequest = new SearchRequest("ts_operating_data_ct", "", searchQuery);

        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true); //设置为返回所有列。
        searchRequest.setColumnsToGet(columnsToGet);

        SearchResponse resp = client.search(searchRequest);

        System.out.println("TotalCount: " + resp.getTotalCount()); //打印匹配到的总行数，非返回行数。
        System.out.println("Row: " + resp.getRows());
    }

    //查询所有列1
    public void cols1(SyncClient client){
        DescribeTableRequest request = new DescribeTableRequest("ts_operating_data_ct");
        DescribeTableResponse response = client.describeTable(request);
        TableMeta tableMeta = response.getTableMeta();

        System.out.println(tableMeta.toString());
    }

    //加索引
    public void addIndex(SyncClient client){
        CreateSearchIndexRequest request = new CreateSearchIndexRequest();
        request.setTableName("ts_operating_data_beta"); //设置表名。
        request.setIndexName("index_operating_beta"); //设置索引名。
        IndexSchema indexSchema = new IndexSchema();
        indexSchema.setFieldSchemas(Arrays.asList(
                new FieldSchema("VolFromAuxMotor", FieldType.KEYWORD) //设置字段名和类型。
                        .setIndex(true) //开启索引。
                        .setEnableSortAndAgg(true), //开启排序和统计功能。
                new FieldSchema("VoltageFromISG", FieldType.KEYWORD)
                        .setIndex(true)
                        .setEnableSortAndAgg(true),
                new FieldSchema("EngineHours", FieldType.KEYWORD)
                        .setIndex(true)
                        .setEnableSortAndAgg(true)
                )
                );
        request.setIndexSchema(indexSchema);
        client.createSearchIndex(request); //调用client创建SearchIndex。
    }
}
