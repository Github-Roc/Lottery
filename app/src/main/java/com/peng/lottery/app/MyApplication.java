package com.peng.lottery.app;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.base.SimpleBaseActivity;
import com.tencent.bugly.Bugly;

import java.util.Stack;


public class MyApplication extends MultiDexApplication {
    /** 管理运行的所有的activity */
    public final static Stack<SimpleBaseActivity> activityStack = new Stack<>();
    /** 全局的上下文 */
    public static MyApplication mContent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContent = this;

        initBugly();
    }

    private void initBugly() {
        Bugly.init(getApplicationContext(), "b610428b73", AppConfig.isDebug);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        for (Activity act : MyApplication.activityStack) {
            if (act != null) {
                act.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
