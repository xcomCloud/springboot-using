package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: mf015
 * Date: 2020/6/11 0011
 */
@SpringBootApplication
public class RPCServerStarter {
    public static final Logger LOGGER = LoggerFactory.getLogger(RPCServerStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(RPCServerStarter.class, args);
        LOGGER.info("### welcome to rpc-server ###");

        // TODO: 2020/6/11 0011 没有现成的合适的
        /**
         * 这块使用了dubbo来实现rpc远程调用；
         *
         */
    }
}
