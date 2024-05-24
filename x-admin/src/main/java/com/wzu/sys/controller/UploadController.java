package com.wzu.sys.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import com.wzu.common.vo.Result;
import com.wzu.sys.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：阿里云文件上传前端控制器
 * 作者：菠萝
 * 日期：2024/4/9 15:26
 */
@RestController
@Slf4j
@RequestMapping("/aliyun")
public class UploadController {
    //阿里云存储文件
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        log.info("上传文件，文件名称：{}",file.getOriginalFilename());
        //调用阿里云OSS工具类，将上传上来的文件存入阿里云
        //上传文件到阿里云oss  获取上传文件
        String url = uploadService.upload(file);
        //将图片上传完成后的url返回，用于浏览器回显展示
        System.out.println(url);
        Map<String, Object> resMap = new HashMap<>();
        // wangEditor上传图片成功后， 需要返回的参数
        resMap.put("errno", 0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", url)));
        return resMap;
    }

}
