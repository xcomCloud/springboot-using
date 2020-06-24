package com.xue.study.controller;

import com.xue.study.service.ClientService;
import com.xue.study.websocket.MyWebsocketClient;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/websocket")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * 新建连接
     * @param user
     * @return
     */
    @RequestMapping(value = "connect-to-server")
    public String connectServer(@RequestParam("user") String user){
        MyWebsocketClient myWebsocketClient = new MyWebsocketClient(user);
        try {
            myWebsocketClient.webSocketClient();
        } catch (Exception e) {
            e.printStackTrace();
            return "connect to server fail.";
        }
        return "connect success";
    }

//    暂时不考虑客户端发出，没有实际意义
    @RequestMapping(value = "group-send-msg", method = RequestMethod.POST)
    public String sendMsg(@RequestBody JSONObject params){
        String msg = params.getString("message");
        clientService.groupSendMsg(msg);
        return "success";
    }
//
//    /**
//     * 客户端指定发送
//     * @param name
//     * @return
//     */
//    @RequestMapping(value = "point-send-msg", method = RequestMethod.GET)
//    public String sendMsg(String name){
//        String message = "客户端指定发出：xxxx-yyyy-mm ddd :::"+System.currentTimeMillis();
//        clientService.pointSendMsg(name, message);
//        return message;
//    }
}
