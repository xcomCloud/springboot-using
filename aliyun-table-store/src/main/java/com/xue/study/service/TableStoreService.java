package com.xue.study.service;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.TableStoreException;
import com.alicloud.openservices.tablestore.model.*;
import com.xue.study.tablestore.TableStoreHelper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class TableStoreService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableStoreService.class);

    public JSONObject saveMsgS(JSONObject params){
        JSONObject result = new JSONObject();

        String[] requiredParams = {"tabName"};
        for (String requiredParam : requiredParams) {
            if(!params.containsKey(requiredParam) || ObjectUtils.isEmpty(params.get(requiredParam))){
                LOGGER.error("message", "miss");

                result.put("message", "miss");
                return result;
            }
        }

        String tabName = params.getString("tabName");
        if (!exists(tabName)) {
           //错误
            return result;
        }


        return result;
    }

    /**
     * 判断表是否存在，不存在则创建
     *
     * @param tabName
     * @return
     */
    public boolean exists(String tabName){
        try {
            SyncClient client = TableStoreHelper.getSyncClient();
            ListTableResponse listTableResponse = client.listTable();
            if(listTableResponse.getTableNames().contains(tabName)){
                System.out.println("ins has it.");
            }else {
                TableMeta tableMeta = new TableMeta(tabName);
                tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("day", PrimaryKeyType.STRING));
                tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("id", PrimaryKeyType.INTEGER, PrimaryKeyOption.AUTO_INCREMENT));
                int livingTime = -1;
                int maxVersion = 1;

                TableOptions tos = new TableOptions(livingTime, maxVersion);
                CreateTableRequest ctr = new CreateTableRequest(tableMeta, tos);
                client.createTable(ctr);
            }

            return true;
        } catch (TableStoreException e) {
            e.printStackTrace();
            return false;
        }
    }
}
