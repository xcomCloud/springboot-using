package com.xue.study.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Iterator;

/**
 * 方式二 （预留）
 */
public class MyClient extends WebSocketClient {

    public MyClient(URI serverURI, Draft_6455 draft_6455) {
        super(serverURI, draft_6455);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("握手。。");
        Iterator<String> it = serverHandshake.iterateHttpFields();
        while (it.hasNext()){
            System.out.println("value ="+serverHandshake.getFieldValue(it.next()));
        }

    }

    @Override
    public void onMessage(String s) {
        System.out.println("客户端接受到消息："+s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("连接关闭。");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("发送错误。");
    }
}
