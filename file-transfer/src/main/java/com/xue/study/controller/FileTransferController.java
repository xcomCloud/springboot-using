package com.xue.study.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileTransferController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTransferController.class);

    /**
     * RequestMapping
     * value
     * method
     * consumes 消费指定媒体格式
     * produces 生产指定媒体格式
     * headers
     * params
     */
    @RequestMapping(value = "file-transfer/api/v1/test-request-mapping", method = RequestMethod.POST, consumes = "appliaction/json", produces="application/json")
    public void test(@RequestBody JSONObject params, @RequestParam("strFile")String strFile, @RequestPart("file") MultipartFile file){
        // @RequestBody
        // @RequestParam
        // @RequestPart
    }

    @RequestMapping(value = "file-transfer/api/v1/upload-file", method = RequestMethod.POST)
    public String uploadFiles(@RequestPart("file")MultipartFile file){
//    public String uploadFiles(@RequestParam(value = "file") CommonsMultipartFile file){ //fixme 失败

        LOGGER.info("file size is :"+ file.getSize());
        LOGGER.info("file name is:"+file.getOriginalFilename());

        String newFileName = file.getOriginalFilename();
        File newFile = new File("/xTest/test/"+newFileName);
        LOGGER.info("newFile path is "+newFile.getAbsolutePath());

        long startTime = System.currentTimeMillis();
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        LOGGER.info("耗时："+((endTime - startTime)));
        return "success";
    }
}
