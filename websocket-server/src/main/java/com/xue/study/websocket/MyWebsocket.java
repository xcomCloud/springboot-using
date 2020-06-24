package com.xue.study.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//@ServerEndpoint(value = "/myWebsocket")
@ServerEndpoint(value = "/websocket/{name}")
@Component
public class MyWebsocket {

    private Session session;
    private String name;

    //使用map可以指定给具体的客户端
    private static ConcurrentHashMap<String, MyWebsocket> websocketMap = new ConcurrentHashMap<>();

    //set是群发形式
//    private static CopyOnWriteArraySet<MyWebsocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "name") String name) {
        this.session = session;
        this.name = name;
        //map测试
        websocketMap.put(name, this);
        System.out.println("连接时**"+this.name);// TODO: 2019/9/3 0003 连接时获取客户端地址 
        System.out.println("server connect success, connecting-num is:" + websocketMap.size());

        //set测试
//        webSocketSet.add(this);
//        System.out.println("websocket连接成功， 当前连接人数："+webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        //map测试
        websocketMap.remove(this);
        System.out.println("断开时**"+this.name);// TODO: 2019/9/3 0003 关闭时得知连接客户端地址
        System.out.println("server exit connection, connecting-num is:" + websocketMap.size());

        //set测试
//        webSocketSet.remove(this);
//        System.out.println("websocket 退出成功，当前连接人数："+webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("[server]this session is :" + session);
        System.out.println("server recv resp is :" + message);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("server start next..");

        // TODO: 2019/8/14
        //  服务端主动推送文件给客户端，
        //  建立连接后，
        //  服务端给客户端发送一个字符创，内容是是一个路径编号；
        //  客户端接收到该信息后，返回给服务度该路径编号；
        //  客户端去下载对应的文件；
        //
        //  *****************
        //  客户端定时发心跳，
        //  服务端发送带有标记的文件存储路径，客户端接收到后返回给服务端；
        //
        //  服务端除了对于接受的消息响应处理；
        //  还需要主动推送消息给客户端，并处理之后的响应；
        //
        //  和socket的优势，有没有必要使用；(websocket 和 socket的对比与区别)
        //
        //  下发时候，如果是服务端下载，就提供一个接口的url（websocket负责提供接口的url、参数，所以接口使用的是get请求方式）, 然后客户端调借口去下载多个文件；
        //  可以带有上下上行下行标记;
        //
        //  0014


        //服务端的响应  ++ 主动推送（考虑处理name的问题）
        //需要指定发送
//        if(0 == message.indexOf("##")){
//            String name = message.substring(2, message.indexOf(";"));
//            pointSendMsg(name, message.substring(message.indexOf(";")+1, message.length()));
//        }else {
//            groupSendMsg(message);
//        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("server occurs error.");
        error.printStackTrace();
    }


    /***
     * 群发功能
     * @param message
     */

    public void groupSendMsg(String message) {
        //map测试
        try {
            for (String name : websocketMap.keySet()) {
                websocketMap.get(name).session.getBasicRemote().sendText(message);
//                System.out.println("session=="+session.toString());
            }
            System.out.println("server send group-msg over.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set测试
//        try {
//            for (MyWebsocket item : webSocketSet) {
//                item.session.getBasicRemote().sendText(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 指定发送
     *
     * @param client
     * @param message
     */
    public void pointSend(String client, String message) throws Exception{
//            message = ""+message;
        System.out.println("client is :" + client);
        websocketMap.get(client).session.getBasicRemote().sendText(message);

    }

//    /**
//     * 指定发送--多线程
//     *
//     * @param client
//     * @param message
//     */
//    public void pointSend(String client, String message) {
////            message = ""+message;
//        try {
//            System.out.println("client is :" + client);
//            websocketMap.get(client).session.getBasicRemote().sendText(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
