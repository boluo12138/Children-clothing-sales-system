package com.wzu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/14 20:19
 */
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    // 支付宝的AppId
    private String appId;
    // 应用私钥
    private String appPrivateKey;
    // 支付宝公钥
    private String appPublicKey;
    //支付宝通知本地的接口完整地址
    private String notifyUrl;

    public String getAppId(){
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAppPublicKey() {
        return appPublicKey;
    }

    public void setAppPublicKey(String appPublicKey) {
        this.appPublicKey = appPublicKey;
    }

    public String getNotifyUrl(){
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
