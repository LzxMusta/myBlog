package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.util.QiniuUtils;
import com.lzxmusta.myblog.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Author: Lzxmusta
 * @Date: 2022-10-16-18:05
 * @Description:图片上传
 */
@RestController
@RequestMapping("/upload")
//@CrossOrigin
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile multipartFile) {
//        原始文件名称 比如aa.png
        String name = multipartFile.getOriginalFilename();
//        System.out.println(name+"=============multipartFile.name========================================");
//        唯一文件名称
        UUID uuid = UUID.randomUUID();
//        System.out.println(uuid.toString()+"+===============uuid=====================");

        String fileName =uuid.toString()+ "." + StringUtils.substringAfterLast(name, ".");
        //上传文件上传到　七牛云　云服务器
        //降低自身应用服务器的带宽消耗
        boolean upload = qiniuUtils.upload(multipartFile, fileName);
        if (upload) {
            System.out.println(fileName+"=============fileName================================");
            System.out.println(QiniuUtils.url+"=============QiniuUtils.url================================");
            return Result.success("http://"+QiniuUtils.url+"/"+ fileName);
        }
        return Result.fail(20001, "上传失败");
    }
}
