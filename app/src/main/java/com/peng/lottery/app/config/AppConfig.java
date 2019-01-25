package com.peng.lottery.app.config;

import android.os.Environment;

public class AppConfig {

    /** AppTag */
    public static final String APP_TAG = "Lottery";
    /** 当前是否是调试状态 */
    public static final boolean isDebug = false;
    /** 服务器根地址 */
    private static final int WEB_PORT = 8080;
    private static final int DEBUG_WEB_PORT = 8080;
    private static final String DEBUG_WEB_IP = "192.168.10.97";
    private static final String DEBUG_BASE_URL = "http://" + DEBUG_WEB_IP + ":" + DEBUG_WEB_PORT + "/jst/app/";
    private static final String WEB_IP = "211.88.20.137";
    private static final String BASE_URL = "http://" + WEB_IP + ":" + WEB_PORT + "/jst/app/";

    /** 本地文件根路径 */
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/lottery/";
    /** 软件更新本地apk路径 */
    public static final String APK_PATH = BASE_PATH + "Lottery.apk";

    /** Activity请求码 */
    public static int REQUEST_INSTALL = 10000;

    /** 结果状态码 */
    public static final int RESULT_SUCCESS = 200;
    public static final int RESULT_NO = 400;
    public static final int RESULT_NULL = 401;
    public static final int RESULT_ERROR = 402;
    public static final int RESULT_NET_ERROR = 403;
    public static final int RESULT_SERVICE_ERROR = 404;

    public static String getBaseUrl() {
        if (isDebug) {
            return DEBUG_BASE_URL;
        } else {
            return BASE_URL;
        }
    }
}
