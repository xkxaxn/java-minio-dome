package com.xkxaxn.miniodemo.config.minio;

import io.minio.MinioClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class MinioClientPoolFactory extends BasePooledObjectFactory<MinioClient> {
    private String endpoint;
    private String accessKey;
    private String secretKey;

    public MinioClientPoolFactory(String endpoint, String accessKey, String secretKey) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public MinioClient create() throws Exception {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Override
    public PooledObject<MinioClient> wrap(MinioClient minioClient) {
        return new DefaultPooledObject<>(minioClient);
    }
}
