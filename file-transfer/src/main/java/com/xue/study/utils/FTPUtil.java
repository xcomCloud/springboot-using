package com.xue.study.utils;

import com.xue.study.pools.FtpPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPUtil {

    public static FTPClient login(){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("47.105.195.242", 21);
            boolean login = ftpClient.login("ftpuser",
                    "ftp000");
            if(!login){
                System.out.println("登录失败！");
                return null;
            }

            ftpClient.setControlEncoding("UTF-8");
            System.out.println("登录成功。");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ftpClient;
    }

    /***
     * ftp
     * 上传(新上传直接覆盖)，下载（按路径和文件名），修改名称（文件路径和名称），读取（取出），
     * @param ftpPool
     */
    public static void upload(FtpPool ftpPool) {
//        System.out.println("filename is:"+filename);

        FTPClient ftp = ftpPool.getFTPClient();
        try {
            File newFile = new File("F:\\test\\test_01.track");
            InputStream io = new FileInputStream(newFile);

            String uploadFile = "test0821.track";

//            ftp.connect("47.105.195.242", 21);
//            ftp.setControlEncoding("UTF-8");
//
//            int reply = ftp.getReplyCode();
//            System.out.println("返回码:" + reply);
//            boolean login = false;
//            if (FTPReply.isPositiveCompletion(reply)) {
//                login = ftp.login("ftpmf", "ftpmf000");
//            }
//            if (!login) {
//                System.out.println("登录失败！");
//                return;
//            }
//            System.out.println("登录成功");
//
//            ftp.setDataTimeout(120000);

            for (FTPFile ftpFile : ftp.listFiles()) {
                System.out.println(ftpFile.getName());
            }
            System.out.println("-------");

            System.out.println(ftp.printWorkingDirectory());
//            boolean change = ftp.changeWorkingDirectory("/pub/test/ct202/001标段/测试1号路/");

            boolean change = false;
            for (int i = 0; i < 2; i++) {
                change = ftp.changeWorkingDirectory("/pub/test/ct202/001标段/测试1号路/");
                if(!change){
                    ftpPool.returnToPool(ftp);
                    ftp = ftpPool.getFTPClient();
                    continue;
                }
                break;
            }

            System.out.println(change);
            System.out.println(ftp.printWorkingDirectory());

            // TODO: 2019/8/16 0016 *** api 测试整理
//            ftp.storeFile("", new FileInputStream(new File("")));
//            ftp.storeFileStream("");
//            ftp.appendFile("", new FileInputStream(new File("")));
//
//            ftp.listDirectories();
//            ftp.listDirectories("");
//            ftp.listFiles();
//            ftp.listFiles("");
//
//            ftp.makeDirectory("");
//            ftp.rename("", "");
//
//            ftp.deleteFile("");
//
//            ftp.doCommandAsStrings("", "");
//            ftp.doCommand("","");
//
//            ftp.features();//判断状态
//            ftp.getStatus("");
//            ftp.remoteStore("");
//
//            ftp.getSendDataSocketBufferSize();
//            ftp.getListHiddenFiles();
//
//            ftp.isRemoteVerificationEnabled();
//
//            ftp.listHelp("");   //命令
//            ftp.mlistDir("");
//            ftp.mlistFile("");
//            ftp.mdtmFile("");
//            ftp.structureMount("");
            // TODO: 2019/8/16 0016 *** api 测试整理

//            ftp.setBufferSize(1024);
//            ftp.setControlEncoding("GBK");
//            boolean type = ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            // FIXME: 2019/8/12 0012
            boolean upload = ftp.storeFile(uploadFile, io);
//            boolean rename = ftp.rename("pub/test/ce_test.track", "pub/test/ce_test101.track");
            System.out.println("upload:" + upload);
//            System.out.println("rename:" + rename);

            io.close();
            ftpPool.returnToPool(ftp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
