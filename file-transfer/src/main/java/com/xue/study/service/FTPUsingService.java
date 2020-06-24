package com.xue.study.service;

import com.xue.study.pools.FtpPool;
import com.xue.study.utils.FtpHelper;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FTPUsingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPUsingService.class);

    private FTPClient ftp;

    @Autowired
    private FtpHelper ftpHelper;

    public JSONObject multiFilesUpload(String fileName, InputStream is, String pathId, String act, FtpPool ftpPool){
        String path = pathId + act +"cloudtruck/001标段/测试1号路/"+fileName;// TODO: 2019/8/19 0019 业务处理

        String trimStr = "/";
        path = path.replaceAll("^（"+trimStr+"）", "");
        path = path.replaceAll("（"+trimStr+"）+$", "");

        FTPClient ftp = ftpPool.getFTPClient();
        try {
//            //二次登陆
//            ftp.connect("47.105.195.242", 21);
//            int replyCode = ftp.getReplyCode();
//            if(FTPReply.isPositiveCompletion(replyCode)){
//                ftp.login("ftpmf", "ftpmf000");
//            }
//            ftp.enterLocalPassiveMode();
            boolean change = ftp.changeWorkingDirectory("pub/test");
            System.out.println("change-"+change);
        } catch (IOException e) {
           e.printStackTrace();
        }

        boolean ret = ftpHelper.upload(fileName, is, ftp, path);
        System.out.println(ret);
        ftpPool.returnToPool(ftp);

        JSONObject result = new JSONObject();
        if(ret){
            result.put("message", "success");
            return result;
        }
        result.put("message", "upload fail.");
        return result;
    }

    public JSONObject multiFilesDownload(String remotePath, String fileName, FtpPool ftpPool){
        JSONObject ret = new JSONObject();

        FTPClient ftp = ftpPool.getFTPClient();

//        boolean result = ftpHelper.download(remotePath, fileName, ftp);

        boolean result = false;
        for (int i = 0; i < 2; i++) {
            result = ftpHelper.download(remotePath, fileName, ftp);
            if (!result) {
                ftpPool.returnToPool(ftp);
                ftp = ftpPool.getFTPClient();
                continue;
            }
            break;
        }


        System.out.println(result);
        ftpPool.returnToPool(ftp);

//        FTPClient ftp = ftpHelper.getFtpClientIns();
//        boolean result = ftpHelper.download(remotePath, fileName, ftp);

        if(result){
            ret.put("retcode", 0);
            ret.put("message", "success");
            return ret;
        }

        ret.put("retcode", 1);
        ret.put("message", String.format("download file[%s] fail.", remotePath));
        return ret;
    }

    public void downloadByWeb(HttpServletResponse response, String remotePath, String fileName){
        InputStream is;
        OutputStream os = null;
        try {
            FTPClient ftp = ftpHelper.getFtpClientIns();
            is = ftp.retrieveFileStream(remotePath);

            os = response.getOutputStream();
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);
            IOUtils.copy(is, os);
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject createNewDirs(String menu){
        JSONObject ret = new JSONObject();

//        ftp = ftpHelper.getFtpClientIns();
        ftp = ftpHelper.getFtpClientIns1();
        if(null == ftp){
            ret.put("retcode", 1);
            ret.put("message", "connect to ftp fail.");
            return ret;
        }

        if(ftpHelper.createDir(menu, ftp)){
            //归还对象
            ftpHelper.returnFtpClient(ftp);
            ret.put("retcode", 0);
            ret.put("message", "success");
            return ret;
        }

        ftpHelper.returnFtpClient(ftp);
        ret.put("retcode", 1);
        ret.put("message", String.format("create menu[%s] error.", menu));
        return ret;
    }


}
