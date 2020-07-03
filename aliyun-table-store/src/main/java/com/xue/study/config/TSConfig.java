package com.xue.study.config;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.model.AlwaysRetryStrategy;

public class TSConfig {

    public static final String TS_ENDPOINT = "xxx"; //实例访问地址即为endpoints
    public static final String TS_ACCESSID = "xxx";//id
    public static final String TS_ACCESSKEY = "xxx";//pwd
    public static final String TS_INSTANCENAME = "xxx";//实例

    private static ClientConfiguration clientConfiguration = null;
    public static ClientConfiguration getClientConfiguration(){
        if(null == clientConfiguration){
            synchronized (ClientConfiguration.class){
                if(null == clientConfiguration){
                    clientConfiguration = new ClientConfiguration();

                    // 设置建立连接的超时时间。
                    clientConfiguration.setConnectionTimeoutInMillisecond(10000);
                    // 设置socket超时时间。
                    clientConfiguration.setSocketTimeoutInMillisecond(10000);
                    // 设置重试策略，若不设置，采用默认的重试策略。
                    clientConfiguration.setRetryStrategy(new AlwaysRetryStrategy());
                }
            }
        }
        return clientConfiguration;
    }

}
