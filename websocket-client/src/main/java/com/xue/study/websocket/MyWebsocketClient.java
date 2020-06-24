package com.xue.study.websocket;

import com.xue.study.service.ClientService;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class MyWebsocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebsocketClient.class);

    private static WebSocketClient webSocketClient;

    @Autowired
    private ClientService clientService;

    private String clientName;

    public MyWebsocketClient(String clientName) {
        this.clientName = clientName;
    }

    public MyWebsocketClient() {
    }

    @Bean
    public WebSocketClient webSocketClient() {
        System.out.println("start create websocket-client");
        try {//47.105.136.58:31081
            URI uri = new URI("ws://192.168.3.67:8085/websocket/" + (StringUtils.isEmpty(this.clientName) ? "MF000006": this.clientName));

            webSocketClient = new WebSocketClient(uri, new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("shake-hands success.");
                }

                @Override
                public void onMessage(String content) {
                    System.out.println(String.format("client recv msg is [%s]", content));

                    System.out.println("clientService-"+clientService);
                    // FIXME: 2019/8/15
                    //  新连接的时候有个 webSocketClient==null 的问题，待修改
                    //  0015
                    //客户端处理新消息
                    clientService.handle(content);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("client exit connect.");
                    //重新连接
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("client occurs error :" + e.getMessage());
                    e.printStackTrace();
                    //重新连接
                }
            };

            webSocketClient.connect();

            while (!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("connecting...");
            }

            System.out.println("###### connect-state open ######");

            //阻塞式连接
            //webSocketClient.connectBlocking();

            /**
             * org.java_websocket.drafts.Draft_10@5489c777
             * ws://192.168.3.67:8090/websocket
             * /192.168.3.67:62071
             * CLOSING
             */
//            System.out.println(webSocketClient.getDraft());
//            System.out.println(webSocketClient.getURI());
//            System.out.println(webSocketClient.getRemoteSocketAddress(webSocketClient.getConnection()));
//            System.out.println(webSocketClient.getReadyState());
            return webSocketClient;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
