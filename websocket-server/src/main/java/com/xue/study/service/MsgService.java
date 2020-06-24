package com.xue.study.service;

import com.xue.study.websocket.MyWebsocket;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgService {

    @Autowired
    private MyWebsocket myWebsocket;

    /**
     * 群发消息
     * @return
     */
    public JSONObject sendMsgByGroup(JSONObject msg){
        JSONObject result = new JSONObject();

        if (!msg.containsKey("msg")) {
            result.put("message", "参数中不包含msg字段");
            return result;
        }

        String msgStr = msg.getString("msg");
        System.out.println("server start to send group-msg.");
        try {
            myWebsocket.groupSendMsg(msgStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("message", "send success.");
        return result;
    }

    /**
     * 指定客户方发送
     * @return
     */
    public JSONObject sendMsgByPoint(JSONObject params){
        JSONObject result = new JSONObject();

        String message = params.getString("message");

        String clients = params.getString("clients");
        String[] clientArr = clients.split(",");
        for (String client: clientArr) {
            try {
                myWebsocket.pointSend(client, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        result.put("message", "point send success.");
        return result;
    }

//    /**
//     * 指定客户方发送 --多线程
//     * @return
//     */
//    public JSONObject sendMsgByPoint(JSONObject params){
//        JSONObject result = new JSONObject();
//
//        final String message = params.getString("message");
//
//        String clients = params.getString("clients");
//        String[] clientArr = clients.split(",");
//        for (String client: clientArr) {
//            final String clientname = client;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    myWebsocket.pointSend(clientname, message);
//                }
//            }).start();
//        }
//
//        result.put("message", "point send success.");
//        return result;
//    }
}
