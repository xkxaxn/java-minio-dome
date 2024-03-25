package com.xkxaxn.minio_super;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MinioSuperApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioSuperApplication.class, args);
    }

}
