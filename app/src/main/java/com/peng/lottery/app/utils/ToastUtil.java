package com.peng.lottery.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * @author Peng
 * @des Toast工具类
 */
public class ToastUtil {
    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(@NonNull Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(@NonNull Context context, @StringRes int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.show();
    }

}
