package com.hdu.lease.exception;

/**
 * 通用异常返回类
 *
 * @author chenyb46701
 * @date 2022/9/24
 */
public class BaseBizException extends BaseCommonException {
    private static final long serialVersionUID = -1654620276470609163L;

    public BaseBizException(int errorCode) {
        super(errorCode);
        this.setErrorMessage(new String[]{""});
    }

    public BaseBizException(int errorCode, Throwable cause) {
        super(errorCode, cause);
        this.setErrorMessage(new String[]{""});
    }

    public BaseBizException(int errorCode, String... messages) {
        super(errorCode, messages);
    }

    public BaseBizException(int errorCode, Throwable cause, String... messages) {
        super(errorCode, cause, messages);
    }

    public BaseBizException(String errorCode) {
        super(errorCode);
        this.setErrorMessage(new String[]{""});
    }

    public BaseBizException(String errorCode, String... messages) {
        super(errorCode, messages);
    }

    public BaseBizException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.setErrorMessage(new String[]{""});
    }

    public BaseBizException(String errorCode, Throwable cause, String... messages) {
        super(errorCode, cause, messages);
    }
}
