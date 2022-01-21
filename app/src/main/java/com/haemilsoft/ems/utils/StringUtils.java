package com.haemilsoft.ems.utils;

import android.util.Base64;


public class StringUtils {

    /**
     * Base64 인코딩
     * Base64.NO_WRAP : 개행문자 미삽입.
     */
    public static String getBase64encode(String content){
        return Base64.encodeToString(content.getBytes(), Base64.NO_WRAP);
    }

    /**
     * Base64 디코딩
     */
    public static String getBase64decode(String content){
        return new String(Base64.decode(content, 0));
    }
}
