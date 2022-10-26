package com.lzxmusta.myblog.util;

import com.alibaba.fastjson2.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.lettuce.core.dynamic.annotation.Value;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author: Lzxmusta
 * @Date: 2022-10-16-18:37
 * @Description:图片服务器
 */
@Component
//通过注解@ConfigurationProperties(prefix = "person") 绑定yaml中的参数给实体类赋值
@ConfigurationProperties(prefix = "qiniu")
@PropertySource(value = "classpath:application.yml")
@Data
public class QiniuUtils {
//一个月换一次   七牛云
    public static  final String url = "rjud6wr5p.hn-bkt.clouddn.com";

    //修改以下两个值放到proprietarties中，在密钥管理中获取
//    @Value("${qiniu.accessKey}")
    private  String accessKey;
//    @Value("${qiniu.accessSecretKey}")
    private  String accessSecretKey;

    public QiniuUtils() {
    }

    public  boolean upload(MultipartFile file, String fileName){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传，修改上传名称为自己创立空间的空间名称
        String bucket = "lzxmusta";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
//            System.out.println(accessKey+accessSecretKey+"=========== Auth.create=============================");
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
