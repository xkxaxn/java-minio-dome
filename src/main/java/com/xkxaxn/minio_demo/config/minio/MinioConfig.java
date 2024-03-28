package com.xkxaxn.minio_demo.config.minio;

import io.minio.MinioClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MinioConfig {

    @Autowired
    MinioParam minioParam;

    @Bean
    public GenericObjectPool<MinioClient> minioClientPool() {


        GenericObjectPoolConfig<MinioClient> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(10); // 设置最大连接数
        config.setMaxIdle(5); // 设置最大空闲连接数
        config.setJmxEnabled(false);

        MinioClientPoolFactory factory = new MinioClientPoolFactory(minioParam.getEndpoint(), minioParam.getAccessKey(), minioParam.getSecretKey());
        return new GenericObjectPool<>(factory, config);
    }
}