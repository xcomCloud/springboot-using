package com.xue.study.controller;

import com.xue.study.config.FTPConfig;
import com.xue.study.pools.FtpPool;
import com.xue.study.service.FTPUsingService;
import com.xue.study.utils.FTPUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/***
 * ftp的上传，下载
 * ftp指定上传目录，
 */

@RestController
public class FTPUsingController {

    @Autowired
    private FTPConfig ftpConfig;

//    @Autowired
//    private ResourceLoader resourceLoader;

    @Autowired
    private FtpPool ftpPool;

    @Autowired
    private FTPUsingService ftpUsingService;

    /**
     * 测试ftp服务端
     */
    @RequestMapping(value = "file-transfer/api/v1/ftp/ftp-using", method = RequestMethod.GET)
    public void ftpUsing(){
        FTPUtil.upload(ftpPool);
    }

    /**
     * 页面上传可调用
     * 测试OK ***
     *
     * @param file
     * @param pathId
     * @param act
     * @return
     */
    @RequestMapping(value = "file-transfer/api/v1/multi-files-upload", method = RequestMethod.POST)
    public JSONObject uploadFiles(@RequestPart("file") MultipartFile file, String pathId, String act){
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();

            JSONObject error = new JSONObject();
            error.put("message", e.getMessage());
            return error;
        }
        return ftpUsingService.multiFilesUpload(file.getOriginalFilename(),is, pathId, act, ftpPool);
    }

    //地图的实效性
    @RequestMapping(value = "api/v1/map-file-save", method = RequestMethod.GET)
    public JSONObject saveMapFile(String localFile){

        return null;
    }

    /**
     * 后台接口 --下载到指定目录
     *  {
     *     "remotePathName":"cloudtruck/001标段/测试1号路/主函数测试1号.track",
     *     "fileName":"F:\\test\\测试"
     *  }
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "file-transfer/api/v1/files-download", method = RequestMethod.POST)
    public JSONObject downloadFiles(@RequestBody JSONObject params){
        System.out.println("recv params :"+ params);

        String remotePathName = params.getString("remotePathName");
        String fileName = params.getString("fileName");
        return ftpUsingService.multiFilesDownload(remotePathName, fileName, ftpPool);
    }

    /**
     * http://192.168.3.67:8089/file-transfer/web/file-download?remote=cloudtruck/001标段/测试1号路/主函数测试1号.track&fileName=主函数测试001.track
     * # 获取ftp文件输入流     ***
     * # 测试ok
     *
     * @param response
     * @param remote
     * @param fileName
     */
    @RequestMapping(value = "file-transfer/web/file-download")
    public void downloadFiles(HttpServletResponse response, @RequestParam("remote") String remote, String fileName){
        ftpUsingService.downloadByWeb(response, remote, fileName);
    }

    // TODO: 2019/8/19 0019 下载一个目录下的多个文件


    /**
     * 创建文件夹
     * @param menu
     * @return
     */
    @RequestMapping(value = "test", method = RequestMethod.POST)
    public JSONObject test(@RequestBody String menu){
        System.out.println("recv params :"+menu);
        return ftpUsingService.createNewDirs(menu);
    }

}
