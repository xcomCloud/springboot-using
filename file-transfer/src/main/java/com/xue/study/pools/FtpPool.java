package com.xue.study.pools;

import com.xue.study.config.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class FtpPool {

    FTPClientFactory ftpClientFactory;
    private final GenericObjectPool<FTPClient> pool;

    /**
     * 初始化连接池
     * @param ftpClientFactory
     */
    // TODO: 2019/8/13 0013 Autowired用在形参之外
    public FtpPool(@Autowired FTPClientFactory ftpClientFactory) {
        this.ftpClientFactory = ftpClientFactory;
        FTPConfig ftpConfig = ftpClientFactory.getFtpConfig();

        // TODO: 2019/8/13 0013 配置可以从config获取
        GenericObjectPoolConfig  poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(ftpConfig.getMaxtotal());
        poolConfig.setMaxIdle(ftpConfig.getMaxidle());
        poolConfig.setMinIdle(ftpConfig.getMinidle());
        poolConfig.setMaxWaitMillis(ftpConfig.getMaxwaitmillis());

        this.pool = new GenericObjectPool<FTPClient>(ftpClientFactory, poolConfig);
    }

    /**
     * 从池中获取连接对象
     */
    public FTPClient getFTPClient(){
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 归还连接到连接池
     * @param ftpClient
     */
    public void returnToPool(FTPClient ftpClient){
        pool.returnObject(ftpClient);
    }

    /**
     * 销毁连接池
     */
    public void destoryPool(){
        pool.close();
    }
}
