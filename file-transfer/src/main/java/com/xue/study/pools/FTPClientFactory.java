package com.xue.study.pools;

import com.xue.study.config.FTPConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 对象池：
 *      使用时借出，用完归还的对象容器；
 * 使用场景：
 *      通过减少对象生成的次数，减少花费在对象初始化上面的开销，从而提高性能；反之，维护对象池也要造成一定开销
 *     （生成某种对象很麻烦费时的时候考虑）
 * 参考链接：
 *      https://www.cnblogs.com/fenglie/p/3858141.html
 *
 *
 *
 */

/**
 * PooledObjectFactory  --对象工厂，用于产生一个新的连接对象;
 *
 */
@Component
public class FTPClientFactory implements PooledObjectFactory<FTPClient> {

//    GenericObjectPool

    @Autowired
    private FTPConfig ftpConfig;

    /**
     * 产生一个连接对象
     *
     *  1. 连接池初始化最小连接数时，生成新连接;
     *  2. 驱逐完过期连接后池中连接数 < 最小连接数时，生成新连接;
     *  3. 获取新连接时，池中对象均被占用；且当前连接数 < 总连接数，生成新连接;
     */
    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        System.out.println("生成新的连接对象");

        FTPClient ftpClient = new FTPClient();
        PooledObject<FTPClient> ftpObj;
        ftpObj = new DefaultPooledObject<>(ftpClient);
        return ftpObj;
    }


    /**
     * 销毁一个连接对象：
     * @param pooledObject
     * @throws Exception
     *
     * 需要考虑异常情况下的销毁;
     * 对象实例与垃圾回收器失去联系时则永远不会被销毁
     *
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> pooledObject) throws Exception {
        System.out.println("销毁连接对象");

        FTPClient ftpObj = pooledObject.getObject();
        ftpObj.logout();
        if(ftpObj.isConnected()){
            ftpObj.disconnect();
        }
    }

    /**
     * 校验方法
     * @param pooledObject
     * @return
     *
     * 校验激活的对象;
     * 校验连接是否可用;
     * 在连接对象空闲时，进行校验;  --影响性能
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> pooledObject) {
        System.out.println("校验对象连接可用");

        FTPClient ftpObj = pooledObject.getObject();
        try {
            return ftpObj.sendNoOp();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 重新激活一个对象
     * @param pooledObject
     * @throws Exception
     *
     * 激活一个对象(初始化一个连接对象）
     * 向对象池归还被钝化对象时调用 --这块说法是不是有问题 --应该是借用时调用
     */
    @Override
    public void activateObject(PooledObject<FTPClient> pooledObject) throws Exception {

        FTPClient ftpObj = pooledObject.getObject();
        ftpObj.connect(ftpConfig.getHost(), ftpConfig.getPort());
        boolean login = false;
        if(FTPReply.isPositiveCompletion(ftpObj.getReplyCode())){
            login = ftpObj.login(ftpConfig.getUsername(), ftpConfig.getPassword());
        }
        if(!login){
            System.out.println("登录失败...");
        }
        ftpObj.setControlEncoding(ftpConfig.getEncoding());
//        ftpObj.changeWorkingDirectory(ftpConfig.getWorkdir());
        ftpObj.setFileType(FTP.BINARY_FILE_TYPE);
        ftpObj.setDataTimeout(12000);
        System.out.println("激活连接对象成功");
    }

    /**
     * 钝化一个对象
     * @param pooledObject
     * @throws Exception
     *
     * 向对象池归还一个对象时调用;
     */
    @Override
    public void passivateObject(PooledObject<FTPClient> pooledObject) throws Exception {
        FTPClient ftpObj = pooledObject.getObject();
        ftpObj.changeWorkingDirectory(ftpConfig.getRoot());
        ftpObj.logout();
        if(ftpObj.isConnected()){
            ftpObj.disconnect();
        }
        System.out.println("钝化连接对象");
    }

    //连接池中获取pool属性
    public FTPConfig getFtpConfig(){
        return ftpConfig;
    }
}
