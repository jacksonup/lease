package com.hdu.lease.utils;

/**
 * @author chenyb46701
 * @date 2022/9/24
 */
public class StringUtils {
    public StringUtils() {
    }

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean equals(String str1, String str2) {
        try {
            return str1.equals(str2);
        } catch (Exception var3) {
            return false;
        }
    }
}
