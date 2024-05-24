package com.wzu.config;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/9 15:49
 */
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class OSSConfig {

    @Value("${oss:endpoint}")
    private String endpoint;

    @Value("${oss:accessKeyId}")
    private String accessKeyId;

    @Value("${oss:accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}


