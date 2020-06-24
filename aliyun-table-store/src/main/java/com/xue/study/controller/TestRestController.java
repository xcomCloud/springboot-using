package com.xue.study.controller;

import com.alicloud.openservices.tablestore.SyncClient;
import com.xue.study.service.TableStoreService;
import com.xue.study.tablestore.TableStoreHelper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestRestController {

    @Autowired
    private TableStoreHelper tableStoreHelper;

    @Autowired
    private TableStoreService tableStoreService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String saveTest(@RequestBody JSONObject params){
        SyncClient syncClient = TableStoreHelper.getSyncClient();
        System.out.println("**"+syncClient);
        tableStoreHelper.putRow(syncClient);
        return "success";
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    public String save(@RequestBody JSONObject params){
        SyncClient syncClient = TableStoreHelper.getSyncClient();
        System.out.println("**"+syncClient);

        //long id, String day, String msgId
        long id = params.getLong("id");
        String day = params.getString("day");
        String msgId = params.getString("msgId");

        tableStoreHelper.getRow1(syncClient);

        return "success";
    }

    /**
     * 新建表
     *          新建字段
     * @param params
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@RequestBody JSONObject params){
        SyncClient syncClient = TableStoreHelper.getSyncClient();
        System.out.println("**"+syncClient);

        //long id, String day, String msgId
        String tabName = params.getString("tabName");

        tableStoreHelper.createTable(syncClient, tabName);

        return "success";
    }


    @RequestMapping(value = "find-msg", method = RequestMethod.POST)
    public String findMsg(@RequestBody JSONObject params){
        String start = params.getString("start");
        String end = params.getString("end");

        SyncClient client = TableStoreHelper.getSyncClient();
        tableStoreHelper.findMsg(client, start, end);
        return "success";
    }


    @RequestMapping(value = "save-msg-test", method = RequestMethod.POST)
    public JSONObject saveMsg1(@RequestBody JSONObject params){
        return tableStoreService.saveMsgS(params);
    }

    @RequestMapping(value = "test-coming", method = RequestMethod.POST)
    public String test101(@RequestBody String params){

        try {
            JSONObject paramsJson = JSONObject.fromObject(params);
            System.out.println(paramsJson);
        } catch (Exception e) {
            e.printStackTrace();
            return "[params error]"+params;
        }

        return "success";
    }

    @RequestMapping(value = "test-getrange", method = RequestMethod.GET)
    public JSONObject testGetRange(String params){

        SyncClient client = TableStoreHelper.getSyncClient();
        return tableStoreHelper.testGetRange(client, params);
    }


    @GetMapping("test-cols")
    public String testGetCols(){
        SyncClient client = TableStoreHelper.getSyncClient();
//        tableStoreHelper.getAllCols(client);
        tableStoreHelper.cols1(client);
        return "ok";
    }

    @GetMapping("add-index")
    public String createIndex(){
        SyncClient client = TableStoreHelper.getSyncClient();
        tableStoreHelper.addIndex(client);
        return "ok";
    }
}
