package com.xue.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketClientDemo {
    public static void main(String[] args) {

        SpringApplication.run(WebsocketClientDemo.class, args);

//        # 方式二测试ok（预留）
//        try {
//            URI uri = new URI("ws://192.168.3.67:8090/websocket/myClient");
//            MyClient myClient = new MyClient(uri, new Draft_6455());
//            myClient.connect();
//            myClient.send("hello，服务端，我是客户端");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

//        ClientTest clientTest = new ClientTest();
//        URI uri = null;
//        try {
//            uri = new URI("ws://192.168.3.67:8090/websocket/user1");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        clientTest.initClient(new MyClient(uri, new Draft_6455()));

    }
}
