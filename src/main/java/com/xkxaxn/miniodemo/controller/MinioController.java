package com.xkxaxn.miniodemo.controller;

import com.xkxaxn.miniodemo.common.constant.api.ApiResult;
import com.xkxaxn.miniodemo.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MinioController {
    @Autowired
    private MinioService minioService;

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public ApiResult uploadFile(MultipartFile file) throws Exception {
        return minioService.upload(file);
    }

    /**
     * 删除文件
     */
    @PostMapping("/common/deletefile")
    public ApiResult uploadFile(String fileName ) throws Exception {
        return minioService.deleteFile(fileName);
    }

    @GetMapping("/test")
    public ApiResult test(@RequestParam String a) {
        return ApiResult.ok(a);
    }
}
