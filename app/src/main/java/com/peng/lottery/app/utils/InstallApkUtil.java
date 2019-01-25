package com.peng.lottery.app.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.base.SimpleBaseActivity;

import java.io.File;


public class InstallApkUtil {

    private static File mInstallFile;

    public static void install(final SimpleBaseActivity activity, File file) {
        if (activity != null) {
            if (file != null) {
                mInstallFile = file;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean b = activity.getPackageManager().canRequestPackageInstalls();
                    if (b) {
                        startInstall(activity);
                    } else {
                        //请求安装未知应用来源的权限
                        activity.showTipDialog("请授权打开安装应用的权限", v -> startInstallPermissionSettingActivity(activity));
                    }
                } else {
                    startInstall(activity);
                }
            } else {
                startInstall(activity);
            }
        }
    }

    private static void startInstall(SimpleBaseActivity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(FileProvider.getUriForFile(activity, "com.peng.lottery.fileprovider", mInstallFile), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(mInstallFile), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(SimpleBaseActivity activity) {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, AppConfig.REQUEST_INSTALL);
    }

}
