package com.hdu.lease.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

/**
 * @author chenyb46701
 * @date 2022/9/24
 */
public class ErrorFormatter {
    private Properties errorInfos = new Properties();
    private ConcurrentHashMap<String, Properties> cacheErrorInfos = new ConcurrentHashMap();
    private ThreadLocal<String> threadLocal = new ThreadLocal();
    private static ErrorFormatter INSTANCE = new ErrorFormatter();
    private static String CUSTOM_DEFAULT_FORMAT = "{%message}";
    private static FormatScopeEnum CUSTOM_DEFAULT_FORMAT_SCOPE;
    public static final String FORMAT_REPLACE_CODE = "{%code}";
    public static final String FORMAT_REPLACE_CODE_REGEXP = "\\{%code\\}";
    public static final String FORMAT_REPLACE_MESSAGE = "{%message}";
    public static final String FORMAT_REPLACE_MESSAGE_REGEXP = "\\{%message\\}";
    public static final String FORMAT_REPLACE_PREFIX = "{%";
    public static final String FORMAT_REPLACE_SUFFIX = "}";
    public static final String DEFAULT_ERROR_PATTERN = "{0}";
    private static final String LOCALE_ZH_HANS_CN = "zh-Hans-CN";
    private static final String LOCALE_ZH_CN = "zh-CN";

    private ErrorFormatter() {
        InputStream stream = null;
        UnicodeReader ur = null;

        try {
            stream = ErrorFormatter.class.getResourceAsStream("/sysErrorFormat.properties");
            ur = new UnicodeReader(stream, "UTF-8");
            this.errorInfos.load(ur);
        } catch (IOException var16) {
            var16.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException var15) {
                }
            }

            if (ur != null) {
                try {
                    ur.close();
                } catch (IOException var14) {
                }
            }

        }

    }

    public static void setCustomDefaultFormat(String customDefaultFormat) {
        CUSTOM_DEFAULT_FORMAT = customDefaultFormat;
    }

    public static void setCustomDefaultFormatScope(FormatScopeEnum customDefaultFormatScope) {
        CUSTOM_DEFAULT_FORMAT_SCOPE = customDefaultFormatScope;
    }

    public static ErrorFormatter getInstance() {
        return INSTANCE;
    }

    public String getLocale() {
        return (String)this.threadLocal.get();
    }

    public void setLocale(String locale) {
        this.threadLocal.set(this.getTopLocale(locale));
    }

    public void removeLocale() {
        this.threadLocal.remove();
    }

    public String format(String errorNo, Object... message) {
        if (errorNo != null) {
            String pattern;
            if (this.getLocale() != null && this.cacheErrorInfos.containsKey(this.getLocale())) {
                pattern = ((Properties)this.cacheErrorInfos.get(this.getLocale())).getProperty(errorNo);
                if (pattern != null) {
                    return this.formatWithCustom(errorNo, pattern, message);
                }
            } else {
                pattern = this.errorInfos.getProperty(errorNo);
                if (pattern != null) {
                    return this.formatWithCustom(errorNo, pattern, message);
                }
            }
        }

        return this.formatWithCustom(errorNo, "{0}", message);
    }

    private String formatWithCustom(String errorNo, String pattern, Object... message) {
        String formatMessage = MessageFormat.format(pattern, message);

        try {
            switch(CUSTOM_DEFAULT_FORMAT_SCOPE) {
                case SYSTEM:
                    try {
                        if (ExceptionUtils.isSystemError(Integer.parseInt(errorNo))) {
                            return this.customFormat(errorNo, formatMessage);
                        }
                    } catch (Exception var6) {
                    }

                    return formatMessage;
                case ALL:
                    return this.customFormat(errorNo, formatMessage);
                default:
                    return formatMessage;
            }
        } catch (Exception var7) {
            return formatMessage;
        }
    }

    private String customFormat(String code, String message) {
        String newMessage = CUSTOM_DEFAULT_FORMAT.replaceAll("\\{%code\\}", code);
        return newMessage.replaceAll("\\{%message\\}", Matcher.quoteReplacement(message));
    }

    public void putErrorMap(Map<String, Object> errorMap) {
        this.errorInfos.putAll(errorMap);
    }

    public void putCacheErrorMap(String locale, Map<String, Object> errorMap) {
        Properties properties = (Properties)this.cacheErrorInfos.get(locale);
        if (null == properties) {
            properties = new Properties();
        }

        properties.putAll(errorMap);
        this.cacheErrorInfos.put(locale, properties);
    }

    private String getTopLocale(String locale) {
        if (StringUtils.isEmpty(locale)) {
            return null;
        } else {
            String localeRet = "";
            float q = 1.0F;
            String[] languages = locale.split(",");

            for(int i = 0; i < languages.length; ++i) {
                String[] lqs = languages[i].split(";");
                if ("zh-Hans-CN".equals(lqs[0])) {
                    lqs[0] = "zh-CN";
                }

                lqs[0] = lqs[0].trim().replace("-", "_");
                float temp = 1.0F;
                if (lqs.length == 2 && lqs[1].trim().substring(2).length() >= 3) {
                    if (!lqs[1].trim().startsWith("q=")) {
                        continue;
                    }

                    try {
                        temp = Float.parseFloat(lqs[1].trim().substring(2));
                    } catch (NumberFormatException var9) {
                        continue;
                    }
                }

                if (this.cacheErrorInfos.containsKey(lqs[0])) {
                    if (StringUtils.isEmpty(localeRet)) {
                        localeRet = lqs[0];
                        if (0.0F < temp && temp <= 1.0F) {
                            q = temp;
                        }
                    } else if (temp != 0.0F && 0.0F < temp && temp <= 1.0F && q < temp) {
                        localeRet = lqs[0];
                        q = temp;
                    }
                }
            }

            return localeRet;
        }
    }

    static {
        CUSTOM_DEFAULT_FORMAT_SCOPE = FormatScopeEnum.NONE;
    }
}
