package com.xkxaxn.minio_super.common.constant.code;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum RCodeEnum implements RCode{

    TO_LOGIN(false, 13, "重新登录"),
    IP_DENIED(false, 15, "IP禁止访问"),
    PERMISSION_DENIED(false, 401, "没有权限"),

    Blacklist(false, 402, "黑名单用户，禁止使用"),
    SUCCESS(true, 200, "成功"),
    FAIL(false, 601, "请求失败"),
    FAIL_SELECT(false, 602, "查询失败"),
    FAIL_DELETE(false, 603, "删除失败"),
    FAIL_UPDATE(false, 604, "修改失败"),
    FAIL_INSERT(false, 605, "添加失败"),
    ;

    public final boolean status;
    public final int code;
    public final String message;

    RCodeEnum(boolean status, int code, String message) {
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
