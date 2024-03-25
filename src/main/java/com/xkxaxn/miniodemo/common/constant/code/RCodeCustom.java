package com.xkxaxn.miniodemo.common.constant.code;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RCodeCustom implements RCode {

    boolean status;
    int code;
    String message;

    public RCodeCustom(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public boolean status() {
        return this.status;
    }
    public int code() {
        return this.code;
    }
    public String message() {
        return this.message;
    }
}
