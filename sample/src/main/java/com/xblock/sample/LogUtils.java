package com.xblock.sample;

import android.util.Log;

/**
 * Util class to for logging
 */

public class LogUtils {
    private static final String LOG_PREFIX = "Mckinsey_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX
                    + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH
                    - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * WARNING: Don't use this when obfuscating class names with Proguard!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(final String tag, String message) {
        // if (Log.isLoggable(tag, Log.DEBUG)) {
        Log.d(tag + "", message + "");
        // }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        if (Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message, cause);
        }
    }

    public static void LOGV(final String tag, String message) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        //		if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
        Log.v(tag, message);
        //		}
    }

    public static void LOGV(final String tag, String message, Throwable cause) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void LOGI(final String tag, String message) {
        Log.i(tag, message);
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        Log.i(tag, message, cause);
    }

    public static void LOGW(final String tag, String message) {

        Log.w(tag, message);
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        Log.w(tag, message, cause);
    }

    public static void LOGE(final String tag, String message) {
        Log.e(tag, message);
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        Log.e(tag, message, cause);
    }

    /**
     * Prints an error log in case of a Error
     * @param tag - Tag of class where this error occured
     * @param error - error
     */
    public static void LOGE(final String tag, Throwable error) {
        String message = "";
        if(error != null && error.getMessage() != null) {
            message = error.getMessage();
        }

        Log.e(tag, "Error: " + message + " toString(): " + error);
    }

    private LogUtils() {
    }
}
