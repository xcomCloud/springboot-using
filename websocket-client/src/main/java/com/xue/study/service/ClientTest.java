package com.xue.study.service;

import com.xue.study.websocket.MyClient;
import org.springframework.util.ObjectUtils;

import java.nio.channels.NotYetConnectedException;

/**
 * 方式二 （预留）
 */
public class ClientTest {

    private static MyClient myClient;

    public void initClient(MyClient client){
        try {
            myClient = client;
            if(!ObjectUtils.isEmpty(myClient)){
                myClient.connect();

                System.out.println(myClient.getReadyState());

                myClient.send("我是客户端.");
            }
        } catch (NotYetConnectedException e) {
            e.printStackTrace();
        }
    }
}
