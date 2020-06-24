package com.xue.study.mongo;

import com.mongodb.client.result.UpdateResult;
import com.xue.study.constants.MongoConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoHelper.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public void studyMongoTemplateMethod() {
//        mongoTemplate.getDb();
//
//        mongoTemplate.aggregate();
//        mongoTemplate.aggregateStream();
//        mongoTemplate.aggregateAndReturn();
//
//        mongoTemplate.bulkOps();
//
//        mongoTemplate.count();
//
//        mongoTemplate.createCollection();
//        mongoTemplate.dropCollection();
//
//        mongoTemplate.execute();
//        mongoTemplate.executeQuery();
//        mongoTemplate.executeCommand();
//
//        mongoTemplate.exists();// TODO: 2019/9/6 0006
//        mongoTemplate.collectionExists();
//
//        mongoTemplate.find();// TODO: 2019/9/6 0006
//        mongoTemplate.findAll();
//        mongoTemplate.findById();// TODO: 2019/9/6 0006
//        mongoTemplate.findOne();// TODO: 2019/9/6 0006
//        mongoTemplate.findDistinct();
//        mongoTemplate.findAndModify();// TODO: 2019/9/6 0006
//        mongoTemplate.findAndReplace();// TODO: 2019/9/6 0006
//        mongoTemplate.findAllAndRemove();// TODO: 2019/9/6 0006
//
//        mongoTemplate.geoNear();
//
//        mongoTemplate.getCollection();
//        mongoTemplate.getCollectionName();
//        mongoTemplate.getCollectionNames();
//
//        mongoTemplate.getConverter();
//
//        mongoTemplate.getExceptionTranslator();
//
//        mongoTemplate.getMongoDbFactory();
//
//        mongoTemplate.indexOps();
//        mongoTemplate.scriptOps();
//
//        mongoTemplate.group();
//
//        mongoTemplate.mapReduce();
//
//        mongoTemplate.insert();// TODO: 2019/9/6 0006
//        mongoTemplate.insertAll();
//
//        mongoTemplate.query();// TODO: 2019/9/6 0006
//        mongoTemplate.remove();// TODO: 2019/9/6 0006
//        mongoTemplate.save();// TODO: 2019/9/6 0006
//
//        mongoTemplate.setApplicationContext();
//        mongoTemplate.setSessionSynchronization();
//        mongoTemplate.setReadPreference();
//        mongoTemplate.setWriteResultChecking();
//        mongoTemplate.setWriteConcern();
//        mongoTemplate.setWriteConcernResolver();
//
//        mongoTemplate.stream();
//
//        mongoTemplate.withSession();
    }


    public void apiUsing() {
//        //判断是否存在
//        mongoTemplate.exists();// TODO: 2019/9/6 0006 不清楚 --貌似其他函数中使用exists作为参数
//        mongoTemplate.collectionExists();
//
//        //查询
//        mongoTemplate.findOne();
//        mongoTemplate.find();
//        mongoTemplate.findById();
//
//        mongoTemplate.query(); // TODO: 2019/9/6 0006  不清楚 --貌似find()中使用query作为参数
//        mongoTemplate.executeQuery();

        //插入
        /**
         * insert函数
         *  -- 如主键存在则报异常，不插入数据；
         *  -- 可一次插入整个列表，不便利，效率高；
         *      mongoTemplate.insert();
         *
         * save函数
         *  -- 如主键存在，执行修改操作；
         *  -- 不支持批量插入，需要遍历，效率低*
         *     mongoTemplate.save();
         */

        //更新
//        mongoTemplate.update();

        /**
         *
         * 更新查询到的第一条
         * -- mongoTemplate.updateFirst();
         *
         * 查询到的全部更新
         * -- mongoTemplate.updateMulti();
         */


        /**
         * 存在则更新，不存在就插入
         *  -- mongoTemplate.upsert();
         */


//        mongoTemplate.findAndModify();
//        mongoTemplate.findAndReplace();
//        mongoTemplate.findAndRemove();
    }


    /**
     * {
     * "path": {
     * "id":"",
     * "command":"upgoing",
     * "points":{
     * "p1":[{"long":"118.4847575","lat":"44.8134086"},{},{},{}],
     * "p2":[{},{},{},{}],
     * "p3":[{},{},{},{}]
     * }
     * }
     * }
     * <p>
     * 保存经纬度坐标点
     *
     * @param pathId
     * @param command
     */
    public void upsertPointsData(String pathId, String command) {
        Query query = new Query();
        String mongoPathId = MongoConstant.MONGO_PATH_ID;
        String mongoCommand = MongoConstant.MONGO_PATH_COMMAND;
        query.addCriteria(Criteria
                .where(mongoPathId).is(pathId)
                .and(mongoCommand).is(command));

        try {
            JSONObject pathObj = mongoTemplate.findOne(query, JSONObject.class, MongoConstant.COL_NAME);

            // TODO: 2019/9/9 0009 结合ftp组装points参数
            JSONObject pointsJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(new JSONObject().put("test", "ttt"));
            jsonArray.add(new JSONObject().put("test1", "ttt1"));
            pointsJson.put("p2", jsonArray);

            //插入
            if (JSONUtils.isNull(pathObj)) {
                JSONObject dataJson = new JSONObject();
                dataJson.put("id", pathId);
                dataJson.put("command", command);
                dataJson.put("points", pointsJson);

                JSONObject mongoJson = new JSONObject();
                mongoJson.put("path", dataJson);

                mongoTemplate.insert(mongoJson, MongoConstant.COL_NAME);
            }
            //更新
            else {
                Update update = Update.update(MongoConstant.MONGO_PATH_POINTS, pointsJson);
                mongoTemplate.updateFirst(query, update, JSONObject.class, MongoConstant.COL_NAME);
            }
        } catch (Exception e) {
            LOGGER.error(String.format("[pathId = %s, command = %s] upsert path-points-data exception : ", pathId, command), e);
        }
    }

    /**
     * 查找path points;
     *
     * @param pathId
     * @param command
     * @return
     */
    public JSONObject findPointsById(String pathId, String command) {
        Query query = new Query();
        query.addCriteria(Criteria
                .where(MongoConstant.MONGO_PATH_ID).is(pathId)
                .and(MongoConstant.MONGO_PATH_COMMAND).is(command));

        JSONObject result = null;
        try {
            result = mongoTemplate.findOne(query, JSONObject.class, MongoConstant.COL_NAME);
        } catch (Exception e) {
            LOGGER.error(String.format("[pathId = %s, command = %s] select path-points-data exception :", pathId, command), e);
            JSONObject ret = new JSONObject();
            ret.put("retcode", 1);
            ret.put("message", String.format("[pathId = %s, command = %s] select path-points-data exception :", pathId, command));
            return ret;
        }

        if (JSONUtils.isNull(result) ||
                !result.containsKey("path") ||
                !result.getJSONObject("path").containsKey("points")) {

            LOGGER.error(String.format("[pathId = %s, command = %s] path-points-data miss points ", pathId, command));
            JSONObject ret = new JSONObject();
            ret.put("retcode", 1);
            ret.put("message", String.format("[pathId = %s, command = %s] path-points-data miss points ", pathId, command));
            return ret;
        }

        return result.getJSONObject("path").getJSONObject("points");
    }

    //##################################################################################################################
    //upsert --test版本
    public void upsertData(String pathId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("pathId." + pathId).exists(true));
            JSONObject pathInfo = mongoTemplate.findOne(query, JSONObject.class, "t_path_file");
            if (JSONUtils.isNull(pathInfo)) {
                JSONObject pathObj = new JSONObject();
                pathObj.put("upgoing", "upgoing ...");
                pathObj.put("downgoing", "downgoing ...");
                pathObj.put("loading", "loading ...");
                pathObj.put("discharging", "discharging ...");

                JSONObject insertObj = new JSONObject();
                insertObj.put(pathId, pathObj);
                insertObj.put("id", pathId);

                JSONObject lastObj = new JSONObject();
                lastObj.put("pathId", insertObj);

                //insert
                mongoTemplate.insert(lastObj, "t_path_file");
            } else {

                Update update = Update.update("pathId." + pathId + "." + "downgoing", "downgoing upupup");

                //update
                UpdateResult updateResult = mongoTemplate.updateFirst(query, update, JSONObject.class, "t_path_file");
                System.out.println(updateResult.isModifiedCountAvailable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //查询 --test版本
    public JSONObject findPointsById(String pathId) {
        System.out.println("pathid is :" + pathId);

        Query query = new Query();
        query.addCriteria(Criteria.where("pathId.id").is(pathId));
        JSONObject result = mongoTemplate.findOne(query, JSONObject.class, "t_path_file");

        System.out.println("#######start.");
        assert result != null;
        result = result.getJSONObject("pathId").getJSONObject(pathId);
        System.out.println(result);
        System.out.println("#######end.");

        return result;
    }


    /**
     * 读取文件测试
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static JSONObject saveData(File file) throws Exception {
        List<String> arr = new ArrayList<String>();
        BufferedReader buff = new BufferedReader(new FileReader(file));

        //读取数据
        while (buff.ready()) {
            String s = buff.readLine();
            if (s.length() > 22 || s.equals("<TRACKPOINTS>")) {
                arr.add(s);
            }
        }

        String s1 = arr.toString();

        String s = s1.substring(1, s1.length() - 1) + ",";

        String[] split = s.split("<TRACKPOINTS>, ");

        JSONObject json = new JSONObject(); //局部路径
        JSONObject pathway = new JSONObject(); //全局路径

        for (int i = 1; i < split.length; i++) {
            String[] split1 = split[i].split(" 0 0 0 0 0 0, ");

            JSONObject everyPath = new JSONObject(); //局部路径
            //JSONArray 存一条路径
            JSONArray localArr = new JSONArray();

            for (String s2 : split1) {
                String[] s3 = s2.split(" ");

                //JSON存点
                JSONObject local = new JSONObject();

                local.put("Long", s3[1]);
                local.put("Lat", s3[0]);
                localArr.add(local);
            }
            everyPath.put(String.format("P%s", i), localArr);
            pathway.putAll(everyPath);

        }
        json.put("rangeList", pathway);
        buff.close();

        return json;
    }
}
