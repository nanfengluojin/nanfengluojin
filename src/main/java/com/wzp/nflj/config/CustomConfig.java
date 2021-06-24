package com.wzp.nflj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/28 17:19
 */
@Order(2)
@Component
@ConfigurationProperties(prefix = "custom-config")
public class CustomConfig {

    public static String withClient;
    public static String secret;
    public static String resourceId;
    public static String ipData;
    public static String originalPassword;
    public static String fileSave;
    public static String publicNetWork;
    public static String excelSavePath;


    public String getWithClient() {
        return withClient;
    }

    public void setWithClient(String withClient) {
        CustomConfig.withClient = withClient;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        CustomConfig.secret = secret;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        CustomConfig.resourceId = resourceId;
    }

    public String getIpData() {
        return ipData;
    }

    public void setIpData(String ipData) {
        CustomConfig.ipData = ipData;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        CustomConfig.originalPassword = originalPassword;
    }

    public String getFileSave() {
        return fileSave;
    }

    public void setFileSave(String fileSave) {
        CustomConfig.fileSave = fileSave;
    }

    public String getPublicNetWork() {
        return publicNetWork;
    }

    public void setPublicNetWork(String publicNetWork) {
        CustomConfig.publicNetWork = publicNetWork;
    }

    public String getExcelSavePath() {
        return excelSavePath;
    }

    public void setExcelSavePath(String excelSavePath) {
        CustomConfig.excelSavePath = excelSavePath;
    }
}
