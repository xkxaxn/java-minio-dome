package com.xkxaxn.minio_demo.config;

import com.xkxaxn.minio_demo.common.constant.api.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception e) {
        e.printStackTrace();
        return ApiResult.error(e.getMessage());
    }

}