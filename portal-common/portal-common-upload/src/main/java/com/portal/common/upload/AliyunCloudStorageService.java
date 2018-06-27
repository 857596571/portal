package com.portal.common.upload;

import com.portal.common.utils.exception.SystemException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 16:22
 */
public class AliyunCloudStorageService extends CloudStorageService{
    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config){
        this.config = config;

        //初始化
        init();
    }

    private void init(){
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }


    @Override
    public String upload(InputStream inputStream, String path) {
        PutObjectResult putResult =null;
        try {
             putResult =      client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e){
            throw new SystemException("上传文件失败，请检查配置信息", e);
        }
        System.out.println(putResult.getETag());
        return config.getAliyunDomain() + "/" + path;
    }

    @Override
    public String upload(byte[] data,String fileExtension) {
        return upload(new ByteArrayInputStream(data), getPath(config.getAliyunPrefix())+fileExtension);
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(config.getAliyunPrefix()));
    }
}
