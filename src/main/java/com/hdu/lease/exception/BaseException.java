package com.hdu.lease.exception;

import com.hdu.lease.constant.ExceptionLogTypeEnum;
import com.hdu.lease.utils.ErrorFormatter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author chenyb46701
 * @date 2022/9/24
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -6853310712844466349L;
    private String returnCode = "";
    private String errorCode = "-1";
    private String errorMessage = "";
    private ExceptionLogTypeEnum exceptionLogType;
    private List<String> errorPropNames;
    private Map<String, Object> errorProperties;

    public BaseException(String errorCode) {
        this.exceptionLogType = ExceptionLogTypeEnum.UNKNOWN;
        this.errorPropNames = new ArrayList();
        this.errorProperties = new HashMap();
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, String... messages) {
        this.exceptionLogType = ExceptionLogTypeEnum.UNKNOWN;
        this.errorPropNames = new ArrayList();
        this.errorProperties = new HashMap();
        this.errorCode = errorCode;
        this.errorMessage = ErrorFormatter.getInstance().format(errorCode, messages);
    }

    public BaseException(String errorCode, Throwable cause) {
        super(cause);
        this.exceptionLogType = ExceptionLogTypeEnum.UNKNOWN;
        this.errorPropNames = new ArrayList();
        this.errorProperties = new HashMap();
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, Throwable cause, String... messages) {
        super(cause);
        this.exceptionLogType = ExceptionLogTypeEnum.UNKNOWN;
        this.errorPropNames = new ArrayList();
        this.errorProperties = new HashMap();
        this.errorCode = errorCode;
        this.errorMessage = ErrorFormatter.getInstance().format(errorCode, messages);
    }

    public void setErrorMessage(String... messages) {
        this.errorMessage = ErrorFormatter.getInstance().format(this.errorCode, messages);
    }

    public void setErrorMessageNoFormat(String messages) {
        this.errorMessage = messages;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrorPropNames() {
        return this.errorPropNames;
    }

    public void logAsSystemError() {
        this.exceptionLogType = ExceptionLogTypeEnum.SYSTEM;
    }

    public void logAsBizError() {
        this.exceptionLogType = ExceptionLogTypeEnum.BUSINESS;
    }

    public boolean isLogAsSystemError() {
        return ExceptionLogTypeEnum.SYSTEM.equals(this.exceptionLogType);
    }

    public boolean isLogAsBizError() {
        return ExceptionLogTypeEnum.BUSINESS.equals(this.exceptionLogType);
    }

    public void putErrorProperty(String name, Object prop) {
        if (!StringUtils.isEmpty(name)) {
            if (!this.errorProperties.containsKey(name)) {
                this.errorPropNames.add(name);
            }

            this.errorProperties.put(name, prop);
        }
    }

    public void putErrorProperty(Map<String, Object> errorProperties) {
        if (errorProperties != null) {
            Iterator var2 = errorProperties.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var2.next();
                this.putErrorProperty((String)entry.getKey(), entry.getValue());
            }
        }

    }

    public Map<String, Object> getErrorProperties() {
        return this.errorProperties;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder("[" + this.errorCode + "]");
        if (!StringUtils.isEmpty(this.errorMessage)) {
            sb.append("[").append(this.errorMessage).append("]");
        } else if (!StringUtils.isEmpty(super.getMessage())) {
            sb.append("[").append(super.getMessage()).append("]");
        }

        int errorPropSize = this.errorPropNames.size();
        if (errorPropSize > 0) {
            sb.append("[");

            for(int i = 0; i < errorPropSize; ++i) {
                String propName = (String)this.errorPropNames.get(i);
                Object propValue = this.errorProperties.get(propName);
                if (i == 0) {
                    sb.append(propName + "=" + propValue);
                } else {
                    sb.append(", " + propName + "=" + propValue);
                }
            }

            sb.append("]");
        }

        return sb.toString();
    }
}
