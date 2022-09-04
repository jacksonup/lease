package com.hdu.lease.utils;

import java.util.UUID;

/**
 * @author Jackson
 * @date 2022/4/30 20:49
 * @description: uuid utils
 */
public class UuidUtils {

    private UuidUtils(){
    }

    /**
     * create uuid.
     *
     * @return
     */
    public static String createUuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
