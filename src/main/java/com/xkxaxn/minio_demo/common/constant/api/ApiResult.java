package com.xkxaxn.minio_demo.common.constant.api;

import com.alibaba.fastjson2.function.impl.ToString;
import com.xkxaxn.minio_demo.common.constant.code.RCode;
import com.xkxaxn.minio_demo.common.constant.code.RCodeEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * 通用返回对象
 */
@Getter
public class ApiResult extends ToString implements Serializable {

    /**
     * 状态
     */
    private boolean status;
    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public ApiResult(boolean status, int code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(RCode rCode) {
        this.status = rCode.status();
        this.code = rCode.code();
        this.message = rCode.message();
        this.data = null;
    }

    public ApiResult(RCode rCode, Object data) {
        this.status = rCode.status();
        this.code = rCode.code();
        this.message = rCode.message();
        this.data = data;
    }

    //public ApiResult put(String key, Object value) {
    //    if (this.data == null) {
    //        this.data = new HashMap<>();
    //    }
    //    ((Map) this.data).put(key, value);
    //    return this;
    //}

    public static ApiResult ok() {
        return new ApiResult(RCodeEnum.SUCCESS, null);
    }

    public static ApiResult ok(Object data) {
        return new ApiResult(RCodeEnum.SUCCESS, data);
    }

    public static ApiResult ok(RCodeEnum rCodeEnum, Object data) {
        return new ApiResult(rCodeEnum, data);
    }

    public static ApiResult error(RCodeEnum rCodeEnum) {
        return ApiResult.error(rCodeEnum.code, rCodeEnum.message);
    }

    public static ApiResult error() {
        return ApiResult.error(RCodeEnum.FAIL);
    }

    public static ApiResult error(String message) {
        return ApiResult.error(RCodeEnum.FAIL.code, message);
    }

    public static ApiResult error(int code, String message) {
        return new ApiResult(false, code, message, null);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
