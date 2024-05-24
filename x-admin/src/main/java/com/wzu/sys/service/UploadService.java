package com.wzu.sys.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 功能：阿里云oss
 * 作者：菠萝
 * 日期：2024/4/5 23:49
 */
public interface UploadService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
