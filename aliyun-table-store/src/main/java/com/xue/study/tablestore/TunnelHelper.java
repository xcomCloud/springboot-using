package com.xue.study.tablestore;

import com.alicloud.openservices.tablestore.TunnelClient;
import com.alicloud.openservices.tablestore.model.tunnel.*;
import com.xue.study.config.TSConfig;


/**
 * Author: mf015
 * Date: 2019/12/5 0005
 *
 * stream api (已废弃)
 * OTS数据同步现使用tunnel通道服务SDK --见TunnelUsing.java
 */

public class TunnelHelper {

    public static void main(String[] args) {
        TunnelClient client = new TunnelClient(TSConfig.TS_ENDPOINT, TSConfig.TS_ACCESSID, TSConfig.TS_ACCESSKEY, TSConfig.TS_INSTANCENAME);

//        createTunnel(client);
//        listTunnel(client, "ts_MF999001");
        describeTunnel(client, "ts_msg", "tun_msg");

    }

    /**
     *  描述通道详情
     *
     * @param client
     * @param tableName
     * @param tunnelName
     */
    // 数据消费时间位点（ConsumePoint）和RPO（Recovery Point Objective）为增量阶段专用属性值，全量阶段无此概念。
    // Tunnel增量：TunnelInfo中Stage的值为ProcessStream；Channel增量：ChannelInfo中的ChannelType为Stream。
    private static void describeTunnel(TunnelClient client, String tableName, String tunnelName) {
        DescribeTunnelRequest request = new DescribeTunnelRequest(tableName, tunnelName);
        DescribeTunnelResponse resp = client.describeTunnel(request);
        System.out.println("RequestId: " + resp.getRequestId());
        // 通道消费增量数据的最新时间点，其值等于Tunnel中消费最慢的Channel的时间点，默认为1970年1月1日（UTC）。
        System.out.println("TunnelConsumePoint: " + resp.getTunnelConsumePoint());
        System.out.println("TunnelInfo: " + resp.getTunnelInfo());
        for (ChannelInfo ci : resp.getChannelInfos()) {
            System.out.println("ChannelInfo::::::");
            System.out.println("\tChannelId: " + ci.getChannelId());
            // Channel的类型, 目前支持BaseData（全量）和增量（Stream）两类。
            System.out.println("\tChannelType: " + ci.getChannelType());
            // 客户端的ID标识, 默认由客户端主机名和随机串拼接而成。
            System.out.println("\tClientId: " + ci.getClientId());
            // Channel消费增量数据的最新时间点。
            System.out.println("\tChannelConsumePoint: " + ci.getChannelConsumePoint());
            // Channel同步的数据条数。
            System.out.println("\tChannelCount: " + ci.getChannelCount());
        }
    }

    /**
     *  获取单表的通道信息
     *
     * @param client
     * @param tableName
     */
    private static void listTunnel(TunnelClient client, String tableName) {
        ListTunnelRequest request = new ListTunnelRequest(tableName);
        ListTunnelResponse resp = client.listTunnel(request);
        System.out.println("RequestId: " + resp.getRequestId());
        for (TunnelInfo info : resp.getTunnelInfos()) {
            System.out.println("TunnelInfo::::::");
            System.out.println("\tTunnelName: " + info.getTunnelName());
            System.out.println("\tTunnelId: " + info.getTunnelId());
            // 通道的类型，有全量(BaseData)、增量(Stream)和全量加增量(BaseAndStream)三类。
            System.out.println("\tTunnelType: " + info.getTunnelType());
            System.out.println("\tTableName: " + info.getTableName());
            System.out.println("\tInstanceName: " + info.getInstanceName());
            // 通道所处的阶段，有初始化(InitBaseDataAndStreamShard)、全量处理(ProcessBaseData)和增量处理(ProcessStream)三类。
            System.out.println("\tStage: " + info.getStage());
            // 数据是否超期，若该值返回true，请及时在钉钉上联系表格存储技术支持。
            System.out.println("\tExpired: " + info.isExpired());
        }
    }

    /**
     * 创建新通道
     *
     * @param client
     */
    private static void createTunnel(TunnelClient client) {
        CreateTunnelRequest request = new CreateTunnelRequest("ts_MF999001", "tun_test_1", TunnelType.Stream);
        CreateTunnelResponse resp = client.createTunnel(request);
        System.out.println("RequestId: " + resp.getRequestId());
        System.out.println("TunnelId: " + resp.getTunnelId());
        //RequestId: 01f9c805-fd4b-47e0-8e32-4225f7ce3ac4
        //TunnelId: 375ffc68-2076-448c-bbb4-224d17b5b2a3
    }

    /**
     * 删除通道
     *
     * @param client
     * @param tableName
     * @param tunnelName
     */
    private static void deleteTunnel(TunnelClient client, String tableName, String tunnelName) {
        DeleteTunnelRequest request = new DeleteTunnelRequest(tableName, tunnelName);
        DeleteTunnelResponse resp = client.deleteTunnel(request);
        System.out.println("RequestId: " + resp.getRequestId());
    }
}
