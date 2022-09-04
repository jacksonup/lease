package com.hdu.lease.utils;

/**
 * @author Jackson
 * @date 2022/5/1 11:22
 * @description:
 */
public class RandomNumberUtils {

    private RandomNumberUtils(){
    }

    /**
     * Create the n bit random numbers.
     *
     * @param n
     * @return
     */
    public static String createRandomNumber(int n) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            stringBuffer.append((int) (Math.random() * 9) + 1);
        }
        return stringBuffer.toString();
    }
}
