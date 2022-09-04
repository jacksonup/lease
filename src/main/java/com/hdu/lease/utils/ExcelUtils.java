package com.hdu.lease.utils;

import java.io.File;
import java.io.InputStream;

/**
 * @author Jackson
 * @date 2022/5/5 20:09
 * @description:
 */
public class ExcelUtils {


        public static InputStream getResourcesFileInputStream(String fileName) {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
        }

        public static String getPath() {
            return ExcelUtils.class.getResource("/").getPath();
        }

        public static File createNewFile(String pathName) {
            File file = new File(getPath() + pathName);
            if (file.exists()) {
                file.delete();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            return file;
        }

        public static File readFile(String pathName) {
            return new File(getPath() + pathName);
        }

        public static File readUserHomeFile(String pathName) {
            return new File(System.getProperty("user.home") + File.separator + pathName);
        }
}
