package com.xue.study.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: mf015
 * Date: 2020/5/5 0005
 */
@Api(tags = "knife4j测试")
@RestController
public class TestController {


    /**
     * knife4j 测试接口，
     * 浏览器访问地址，http://localhost:8098/doc.html
     *
     * @param str
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str",value = "字符串参数", dataType = "String", example = "MF000001")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "成功状态"),
            @ApiResponse(code = 1, message = "失败状态")
    })
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "params", value = "参数", example = "{}", dataTypeClass = JSONObject.class),
            @DynamicParameter(name = "arr", value = "数组", example = "[]", dataTypeClass = JSONArray.class)}
    )
    @ApiOperation(value = "测试knife4j接口", notes = "呵呵，这是备注", response = JSONObject.class)
    @GetMapping(value = "/test-get", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject testGet(String str){
        JSONObject resp = new JSONObject();
        resp.put("retcode", 0);

        JSONArray arr = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("a", "aa");
        json.put("b", "bb");

        JSONObject json1 = new JSONObject();
        json1.put("x", "xx");
        json1.put("y", "yy");
        arr.add(json);
        arr.add(json1);

        resp.put("params", json);
        resp.put("arr", arr);
        resp.put("message", "testGet success");
        return resp;
    }
}
