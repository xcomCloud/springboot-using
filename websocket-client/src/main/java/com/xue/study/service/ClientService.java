package com.xue.study.service;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private WebSocketClient webSocketClient;

//    /***
//     * 指定发送
//     * @param name
//     * @param msg
//     */
//    public void pointSendMsg(String name, String msg){
//        webSocketClient.send("##"+name+";+"+msg);
//    }
//
    /**
     * 群发
     * @param msg
     */
    public void groupSendMsg(String msg){
        System.out.println("客户端连接状态："+webSocketClient.getReadyState());

        if(!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            System.out.println("客户端连接not ready.");
        }

        webSocketClient.send(msg);
        // TODO: 2019/8/14 0014 需要的是这块发送成接口调用; 所以服务端是不是需要考虑处理接口形式
    }


    /**
     * 客户端处理新消息
     */
    public void handle(String msg){
        System.out.println("client start handle msg...");
        try {
            Thread.sleep(1000);

            //登录成功
            if(msg.equalsIgnoreCase("00")){
                System.out.println("recv 00 from server, login success.");
                return;
            }

            //接收到下发指令
            if(msg.equalsIgnoreCase("##download")){
                String sendMsg = "{\"number\":\"MF000006\",\"command\":\"download\"}";
                webSocketClient.send(sendMsg);
                return;
            }

            //接收到存储目录
            String menu = msg;
            System.out.println("文件存储目录："+menu);
            Thread.sleep(1000);
            System.out.println("client handle msg over.");
        } catch (InterruptedException e) {
            System.out.println("client handle msg error.");
            e.printStackTrace();
        }
    }
}
