package com.xkxaxn.minio_super.service;

import com.xkxaxn.minio_super.common.UUIDTool;
import com.xkxaxn.minio_super.common.constant.api.ApiResult;
import com.xkxaxn.minio_super.config.minio.MinioParam;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class MinioService {

    @Autowired
    private GenericObjectPool<MinioClient> minioClientPool;

    @Autowired
    private MinioParam minioParam;

    /**
     * 文件上传至Minio
     */
    public ApiResult upload(MultipartFile file) throws Exception {

        String filePrefix = "";
        String fileSuffix = "";
        String fileContentType = "";
        String fileType = "";
        String fileSizeKb = "";
        String newFileName = "";
        String filePath = "";

        //文件处理
        //文件不能为空
        if (file.isEmpty()) {
            return ApiResult.error("文件为空");
        }

        {
            String fileName = file.getOriginalFilename();
            String fileExtension = "";
            int index = fileName.lastIndexOf('.');
            if (index > 0) {
                fileExtension = fileName.substring(index + 1);
                fileName = fileName.substring(0, index);
            }
            filePrefix = fileName;
            fileSuffix = fileExtension;
            fileContentType = file.getContentType();
            fileSizeKb = String.valueOf(file.getSize() / 1024);

            //获取文件类型
            String[] parts = fileContentType.split("/");
            if (parts.length > 0) {
                fileType = parts[0];
            }
            if (fileType == null){
                fileType = "other ";
            }
        }

        //文件检查
        {
            //单个文件不能超过200m
            long MAX_FILE_SIZE = 200 * 1024 * 1024;
            if (file.getSize() > MAX_FILE_SIZE) {
                return ApiResult.error("文件大小超过200MB限制");
            }
            //后缀不为空
            if (fileSuffix.equals("")) {
                return ApiResult.error("文件后缀不为空");
            }
        }


        ////minio处理
        ////创建Minio的连接对象
        //MinioClient minioClient = MinioClient.builder()
        //        .endpoint(minioParam.getEndpoint())
        //        .credentials(minioParam.getAccessKey(), minioParam.getSecretKey())
        //        .build();
        //
        ////判断文件存储的桶是否存在
        //boolean flag = minioClient.bucketExists(
        //        BucketExistsArgs.builder().bucket(minioParam.getBucketName()).build()
        //);
        //
        ////判断是否存在该桶
        //if (!flag) {
        //    //不存在就创建
        //    minioClient.makeBucket(
        //            MakeBucketArgs.builder().bucket(minioParam.getBucketName()).build()
        //    );
        //}

        //将连接持久化
        MinioClient minioClient = minioClientPool.borrowObject();

        //上传到桶中的文件的路径 使用当前日期作为文件存储路径
        String data = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        newFileName = UUIDTool.getUUID() + '.' + fileSuffix;
        // 文件类型/日期/uuid.后缀
        filePath = fileType + '/' + data + "/" + newFileName;

        //操作文件，文件上传
        minioClient.putObject(
                PutObjectArgs.builder().bucket(minioParam.getBucketName())
                        .object(filePath)
                        .stream(file.getInputStream(), -1, 10485760)
                        .contentType(file.getContentType())
                        .build());

        //封装访问的url给前端 http://127.0.0.1:9000/桶名/路径/文件名
        String url = minioParam.getEndpoint() + "/" + minioParam.getBucketName() + "/" + filePath;
        //fileName /桶名/路径/文件名
        String fileName = "/" + minioParam.getBucketName() + "/" + data + newFileName;
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("url", url);
        hashMap.put("fileName", fileName);
        return ApiResult.ok(hashMap);
    }

    public ApiResult deleteFile(String fileName) throws Exception {
        if (Objects.equals(fileName, "")){
            return ApiResult.error("文件不为空");
        }
        //将文件名设置为uuid，在并发数据少的时候是不会重复的
        //故小型项目可以直接用uuid来删除文件
        //大项目则需要用路径+文件名
        MinioClient minioClient = minioClientPool.borrowObject();
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(minioParam.getBucketName()).object(fileName).build()
        );
        return ApiResult.ok();
    }

}