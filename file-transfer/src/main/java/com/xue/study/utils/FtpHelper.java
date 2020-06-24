package com.xue.study.utils;

import com.xue.study.pools.FtpPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class FtpHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpHelper.class);

    // TODO: 2019/8/19
    //  创建目录
    //  上传（前台、后台）
    //  下载
    //
    //  0019

    @Autowired
    private FtpPool ftpPool;

    /**
     * 初始化
     * @return
     */
    public FTPClient getFtpClientIns(){
        FTPClient ftpClient = ftpPool.getFTPClient();
        try {
            ftpClient.connect("47.105.195.242", 21);
            ftpClient.setControlEncoding("UTF-8");

            int replyCode = ftpClient.getReplyCode();
            boolean login = false;
            if(FTPReply.isPositiveCompletion(replyCode)){
                login = ftpClient.login("ftpmf", "ftpmf000");
            }
//            if(!login){
//                return null;
//            }

            ftpClient.setDataTimeout(12000);
            ftpClient.changeWorkingDirectory("/pub/test/");

//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            System.out.println("登录成功.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ftpClient;
    }

    /**
     * 初始化
     * @return
     */
    public FTPClient getFtpClientIns1(){
        FTPClient ftpClient = ftpPool.getFTPClient();
        System.out.println(ftpClient.toString()+"登录成功");
        return ftpClient;
    }

    public boolean returnFtpClient(FTPClient ftpClient){
        ftpPool.returnToPool(ftpClient);
        return true;
    }

    /**
     *
     * @param dir 目标目录，矿区/标段/路径编号/{上行，下行，上料，下料}
     * @param ftpClient
     */
    public boolean createDir(String dir, FTPClient ftpClient){
        if (StringUtils.isEmpty(dir)) {
            LOGGER.error(String.format("aim dir[%s] is empty.", dir));
            return false;
        }

        String menu = null;
        try {
            menu = new String(dir.trim().getBytes(StandardCharsets.ISO_8859_1));
            //如果目录存在，返回
            if(ftpClient.changeWorkingDirectory(menu)){
                System.out.println("目录已存在.");
                return true;
            }
            //不存在，创建
            String trimStr = "/";
            menu = menu.replaceAll("^（"+trimStr+"）", "");
            menu = menu.replaceAll("（"+trimStr+"）+$", "");

            StringBuilder sb = new StringBuilder();
            for (String str : menu.split("/")){
                sb.append(str);
                sb.append("/");

                menu = new String(sb.toString().getBytes(StandardCharsets.UTF_8));
                if(ftpClient.changeWorkingDirectory(menu)){
                    continue;
                }
                if(!ftpClient.makeDirectory(menu)){
                    LOGGER.error(String.format("mkdir[%s] error.", menu));
                    return false;
                }
            }
            return ftpClient.changeWorkingDirectory(menu);

        } catch (IOException e) {
            LOGGER.error(String.format("create [%s] exception:", menu), e);
        }
        return false;
    }


    public boolean upload(String fileName, InputStream is, FTPClient ftpClient, String remote){
        try {
            System.out.println(ftpClient.printWorkingDirectory());
            System.out.println(remote);
            OutputStream out = ftpClient.storeFileStream(new String(remote.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            byte[] byteArray = new byte[4096];

            int read = 0;
            while ((read = is.read(byteArray)) != -1) {
                out.write(byteArray, 0, read);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean download(String remotePath, String fileName, FTPClient ftpClient){
        File downloadFile = new File(fileName);
        OutputStream os = null;
        boolean dResult = false;
        try {
            os = new FileOutputStream(downloadFile);
            dResult = ftpClient.retrieveFile(remotePath, os); //new String(remotePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
            if(!dResult){
                System.out.println("下载失败.");
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // TODO: 2019/8/19 0019  sun.net.ftp.FtpClient使用
    public void readFile(String remote, FtpClient ftpClient){
        InputStream is = null;
        try {
            is = ftpClient.getFileStream(remote);
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void close() throws IOException {
//
//    }
}
