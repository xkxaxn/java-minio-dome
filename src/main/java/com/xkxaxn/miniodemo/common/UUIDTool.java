package com.xkxaxn.miniodemo.common;

import java.util.UUID;

public class UUIDTool {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
