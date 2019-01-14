package com.peng.lottery.app.utils;

import android.util.Log;

import com.peng.lottery.app.config.AppConfig;


public class LogUtil {
    //不输出任和log
    private static final int LOG_LEVEL_NONE = 0;
    //调试 蓝色
    private static final int LOG_LEVEL_DEBUG = 1;
    //提现 绿色
    private static final int LOG_LEVEL_INFO = 2;
    //警告 橙色
    private static final int LOG_LEVEL_WARN = 3;
    //错误 红色
    private static final int LOG_LEVEL_ERROR = 4;
    //输出所有等级
    private static final int LOG_LEVEL_ALL = 5;

    /**
     * 允许输出的log日志等级
     * 当出正式版时,把mLogLevel的值改为 LOG_LEVEL_NONE,
     * 就不会输出任何的Log日志了.
     */
    private static int mLogLevel = LOG_LEVEL_ALL;

    /**
     * 获取Log等级
     */
    private static int getLogLevel() {
        return mLogLevel;
    }

    /**
     * 设置Log等级
     */
    public static void setLogLevel(int level) {
        LogUtil.mLogLevel = level;
    }

    /**
     * 以级别为 v 的形式输出LOG ，verbose啰嗦的意思
     */
    public static void v(String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_ALL) {
            Log.v(AppConfig.APP_TAG, msg);
        }
    }

    /**
     * 以级别为 v 的形式输出LOG ，verbose啰嗦的意思
     */
    public static void v(String tag, String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_ALL) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG,输出debug调试信息
     */
    public static void d(String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_DEBUG) {
            Log.d(AppConfig.APP_TAG, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG,输出debug调试信息
     */
    public static void d(String tag, String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG,一般提示性的消息information
     */
    public static void i(String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_INFO) {
            Log.i(AppConfig.APP_TAG, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG,一般提示性的消息information
     */
    public static void i(String tag, String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_INFO) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG,显示warning警告，一般是需要我们注意优化Android代码
     */
    public static void w(String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_WARN) {
            Log.w(AppConfig.APP_TAG, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG,显示warning警告，一般是需要我们注意优化Android代码
     */
    public static void w(String tag, String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_WARN) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG,error级别的Log信息,查看错误源的关键
     */
    public static void e(String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_ERROR) {
            Log.e(AppConfig.APP_TAG, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG,error级别的Log信息,查看错误源的关键
     */
    public static void e(String tag, String msg) {
        if (AppConfig.isDebug && getLogLevel() >= LOG_LEVEL_ERROR) {
            Log.e(tag, msg);
        }
    }

}


