package com.hdu.lease.utils;

import com.hdu.lease.exception.BaseException;

/**
 * @author chenyb46701
 * @date 2022/9/24
 */
public class ExceptionUtils {
    public ExceptionUtils() {
    }

    public static String getErrorCode(Throwable e, int defaultCode) {
        if (e instanceof BaseException) {
            String errorCode = ((BaseException)e).getErrorCode();
            return null == errorCode ? defaultCode + "" : errorCode;
        } else {
            return defaultCode + "";
        }
    }

    public static BaseException getBaseException(Throwable e, int defaultCode) {
        if (e instanceof BaseException) {
            return (BaseException)e;
        } else {
            BaseException be = new BaseException(defaultCode + "", e);
            if (!StringUtils.isEmpty(e.getMessage())) {
                be.setErrorMessage(new String[]{e.getMessage()});
            } else {
                be.setErrorMessage(new String[]{e.getClass().getName()});
            }

            return be;
        }
    }

    public static String parseExceptionToString(Throwable e) {
        try {
            String s = e.toString() + "\n";
            StackTraceElement[] trace = e.getStackTrace();
            StackTraceElement[] var3 = trace;
            int var4 = trace.length;

            int var5;
            for(var5 = 0; var5 < var4; ++var5) {
                StackTraceElement traceElement = var3[var5];
                s = s + "\tat " + traceElement + "\n";
            }

            Throwable[] var8 = e.getSuppressed();
            var4 = var8.length;

            for(var5 = 0; var5 < var4; ++var5) {
                Throwable se = var8[var5];
                s = s + parseExceptionToString(se);
            }

            Throwable ourCause = e.getCause();
            if (ourCause != null) {
                s = s + parseExceptionToString(ourCause);
            }

            s = s + "\n";
            return s;
        } catch (Exception var7) {
            var7.printStackTrace();
            return "parseExceptionToString转换异常 " + e.toString();
        }
    }

    public static boolean isSystemError(int code) {
        return code < 1999 && code != -1 && code != 0 || code >= 2600 && code < 2700;
    }

    public static int getErrorCode(BaseException e) {
        int code = 2147483647;

        try {
            if (e.getErrorCode() != null) {
                code = Integer.parseInt(e.getErrorCode());
            }
        } catch (Exception var3) {
            code = -1;
        }

        return code;
    }

    public static boolean isSystemError(BaseException e, String code) {
        if (null == e) {
            return false;
        } else if (e.isLogAsBizError()) {
            return false;
        } else {
            try {
                return e.isLogAsSystemError() || isSystemError(Integer.parseInt(code));
            } catch (Exception var3) {
                return false;
            }
        }
    }
}
