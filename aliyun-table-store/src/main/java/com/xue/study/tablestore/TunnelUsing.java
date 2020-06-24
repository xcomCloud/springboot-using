package com.xue.study.tablestore;

import com.alicloud.openservices.tablestore.TunnelClient;
import com.alicloud.openservices.tablestore.model.StreamRecord;
import com.alicloud.openservices.tablestore.model.tunnel.CreateTunnelRequest;
import com.alicloud.openservices.tablestore.model.tunnel.CreateTunnelResponse;
import com.alicloud.openservices.tablestore.model.tunnel.TunnelType;
import com.alicloud.openservices.tablestore.tunnel.worker.IChannelProcessor;
import com.alicloud.openservices.tablestore.tunnel.worker.ProcessRecordsInput;
import com.alicloud.openservices.tablestore.tunnel.worker.TunnelWorker;
import com.alicloud.openservices.tablestore.tunnel.worker.TunnelWorkerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Author: mf015
 * Date: 2019/12/11 0011
 */
@Component
public class TunnelUsing {
    private static final Logger LOGGER = LoggerFactory.getLogger(TunnelUsing.class);

    private static class SimpleProcessor implements IChannelProcessor {
        //处理
        @Override
        public void process(ProcessRecordsInput input) {
            System.out.println(String.format("Process %d records, NextToken: %s", input.getRecords().size(), input.getNextToken()));
            LOGGER.info(String.format("Process %d records, NextToken: %s", input.getRecords().size(), input.getNextToken()));

            for (StreamRecord record : input.getRecords()) {
                LOGGER.info("********************coming**************************************");
                LOGGER.info("primary-key ==="+ record.getPrimaryKey());
                LOGGER.info("columns ==="+ record.getColumns());
            }

            try {
                // Mock Record Process.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //关闭
        @Override
        public void shutdown() {
            LOGGER.info("============================ shutdown ============================");
        }
    }

//    public void asyn() {
//        final String tableName = "ts_MF999001";
//        final String tunnelName = "tun_test_11";
//
//        // 1. 初始化Tunnel Client。TunnelWorkerConfig
//        final String endPoint = "https://ct-ori-msg-data.cn-hangzhou.ots.aliyuncs.com";
//        final String accessKeyId = "LTAI4Fw6StHoE2kSFCPLsCdp";
//        final String accessKeySecret = "4xqYaKE2YH5Ga7nl2gt7UiaLYo6meM";
//        final String instanceName = "ct-ori-msg-data";
//        TunnelClient tunnelClient = new TunnelClient(endPoint, accessKeyId, accessKeySecret, instanceName);
//
//        // 2. 创建Tunnel（此步骤需要提前建好一张测试表，可以使用SyncClient的createTable或者使用官网控制台等方式来创建）
//        CreateTunnelRequest request = new CreateTunnelRequest(tableName, tunnelName, TunnelType.Stream);
//        CreateTunnelResponse resp = tunnelClient.createTunnel(request);
//        LOGGER.info("resp=="+resp.toString()+"*resp.getTunnelId=="+resp.getTunnelId());
//
//        // tunnelId会用于后续TunnelWorker的初始化, 该值同样可以通过ListTunnel或者DescribeTunnel获取
//        String tunnelId = resp.getTunnelId();
//        LOGGER.info("Create Tunnel, Id: " + tunnelId);
//
//        while (true) {
//            test(tunnelClient, tunnelId);
//        }
//    }

    public void asyn() {
        final String tableName = "ts_msg";
        final String tunnelName = "tun_test_11";

        // 1. 初始化Tunnel Client。TunnelWorkerConfig
        final String endPoint = "https://ct-ori-msg-data.cn-hangzhou.ots.aliyuncs.com";
        final String accessKeyId = "LTAI4Fw6StHoE2kSFCPLsCdp";
        final String accessKeySecret = "4xqYaKE2YH5Ga7nl2gt7UiaLYo6meM";
        final String instanceName = "ct-ori-msg-data";
        TunnelClient tunnelClient = new TunnelClient(endPoint, accessKeyId, accessKeySecret, instanceName);

        // 2. 创建Tunnel（此步骤需要提前建好一张测试表，可以使用SyncClient的createTable或者使用官网控制台等方式来创建）
        CreateTunnelRequest request = new CreateTunnelRequest(tableName, tunnelName, TunnelType.Stream);
        CreateTunnelResponse resp = tunnelClient.createTunnel(request);
        LOGGER.info("resp=="+resp.toString()+"*resp.getTunnelId=="+resp.getTunnelId());

        // tunnelId会用于后续TunnelWorker的初始化, 该值同样可以通过ListTunnel或者DescribeTunnel获取
        String tunnelId = resp.getTunnelId();
        LOGGER.info("Create Tunnel, Id: " + tunnelId);

        while (true) {
            test(tunnelClient, tunnelId);
        }
    }

    public static void test(TunnelClient tunnelClient, String tunnelId){

        // 3. 用户自定义数据消费Callback, 开始自动化的数据消费。
        // TunnelWorkerConfig里面还有更多的高级参数，这里不做展开，会有专门的文档介绍
        TunnelWorkerConfig config = new TunnelWorkerConfig(new SimpleProcessor()); //单台机器建议共用一个
        TunnelWorker worker = new TunnelWorker(tunnelId, tunnelClient, config);
        try {
            worker.connectAndWorking();
        } catch (Exception e) {
            e.printStackTrace();
            worker.shutdown();
            tunnelClient.shutdown();
        }
    }
}
