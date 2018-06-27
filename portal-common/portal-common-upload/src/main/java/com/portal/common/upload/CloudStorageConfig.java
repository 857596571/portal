package com.portal.common.upload;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * 云存储配置信息
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 16:12
 */
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //阿里云绑定的域名
    @NotBlank(message="阿里云绑定的域名不能为空")
    @URL(message = "阿里云绑定的域名格式不正确")
    private String aliyunDomain;
    //阿里云路径前缀
    private String aliyunPrefix;
    //阿里云EndPoint
    @NotBlank(message="阿里云EndPoint不能为空")
    private String aliyunEndPoint;
    //阿里云AccessKeyId
    @NotBlank(message="阿里云AccessKeyId不能为空")
    private String aliyunAccessKeyId;
    //阿里云AccessKeySecret
    @NotBlank(message="阿里云AccessKeySecret不能为空")
    private String aliyunAccessKeySecret;
    //阿里云BucketName
    @NotBlank(message="阿里云BucketName不能为空")
    private String aliyunBucketName;

    public String getAliyunDomain() {
        return aliyunDomain;
    }

    public void setAliyunDomain(String aliyunDomain) {
        this.aliyunDomain = aliyunDomain;
    }

    public String getAliyunPrefix() {
        return aliyunPrefix;
    }

    public void setAliyunPrefix(String aliyunPrefix) {
        this.aliyunPrefix = aliyunPrefix;
    }

    public String getAliyunEndPoint() {
        return aliyunEndPoint;
    }

    public void setAliyunEndPoint(String aliyunEndPoint) {
        this.aliyunEndPoint = aliyunEndPoint;
    }

    public String getAliyunAccessKeyId() {
        return aliyunAccessKeyId;
    }

    public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
        this.aliyunAccessKeyId = aliyunAccessKeyId;
    }

    public String getAliyunAccessKeySecret() {
        return aliyunAccessKeySecret;
    }

    public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
        this.aliyunAccessKeySecret = aliyunAccessKeySecret;
    }

    public String getAliyunBucketName() {
        return aliyunBucketName;
    }

    public void setAliyunBucketName(String aliyunBucketName) {
        this.aliyunBucketName = aliyunBucketName;
    }
}
