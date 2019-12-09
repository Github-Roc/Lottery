package com.peng.lottery.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class DialogUtil {

    public static void showTipDialog(Context context, String content, DialogInterface.OnClickListener listener) {
        showDialogOneButton(context, "提示", content, "确定", listener);
    }

    public static void showDefaultDialog(Context context, String title, String content, DialogInterface.OnClickListener positiveButtonListener, DialogInterface.OnClickListener neutralButtonListener) {
        showDialogTwoButton(context, title, content, "确定", positiveButtonListener, "取消", neutralButtonListener);
    }

    public static void showDialogOneButton(Context context, String title, String content, String buttonText, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(buttonText, onClickListener);
        builder.create().show();
    }

    public static void showDialogTwoButton(Context context, String title, String content,
                                           String positiveButtonText, DialogInterface.OnClickListener positiveButtonListener,
                                           String neutralButtonText, DialogInterface.OnClickListener neutralButtonListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonText, positiveButtonListener)
                .setNeutralButton(neutralButtonText, neutralButtonListener);
        builder.create().show();
    }

    public static void showDialogThreeButton(Context context, String title, String content,
                                             String positiveButtonText, DialogInterface.OnClickListener positiveButtonListener,
                                             String neutralButtonText, DialogInterface.OnClickListener neutralButtonListener,
                                             String negativeButtonText, DialogInterface.OnClickListener negativeButtonListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonText, positiveButtonListener)
                .setNeutralButton(neutralButtonText, neutralButtonListener)
                .setNegativeButton(negativeButtonText, negativeButtonListener);
        builder.create().show();
    }

    public static void showInputDialog(Context context, String title, String buttonText, InputListener listener) {
        EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(editText)
                .setPositiveButton(buttonText, (dialogInterface, i) -> {
                    if (listener != null) {
                        listener.onClick(editText.getText().toString());
                    }
                });
        builder.create().show();
    }

    public static void showListDialog(Context context, String title, List<String> data, DialogInterface.OnClickListener clickListener) {
        String[] items = new String[data.size()];
        data.toArray(items);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(items, clickListener);
        builder.create().show();
    }

    public static void showSingSelectDialog(Context context, String title, List<String> data, SingSelectListener listener) {
        String[] items = new String[data.size()];
        data.toArray(items);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(items, (dialogInterface, i) -> {
                    if (listener != null) {
                        String text = items[i];
                        listener.onClick(text);
                    }
                });
        builder.create().show();
    }

    public static void showViewDialog(Context context, String title, @LayoutRes int layoutRes, String buttonText, DialogInterface.OnClickListener onClickListener) {
        View view = LayoutInflater.from(context).inflate(layoutRes, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(buttonText, onClickListener);
        builder.create().show();
    }


    public static ProgressDialog showLoadingDialog(Context context, String loadingText) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(loadingText);
        // 是否形成一个加载动画  true表示不明确加载进度形成转圈动画  false 表示明确加载进度
        progressDialog.setIndeterminate(true);
        // 点击返回键或者dialog四周是否关闭dialog  true表示可以关闭 false表示不可关闭
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static ProgressDialog showProgressDialog(Context context, String title) {
        final int MAX_VALUE = 100;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_VALUE);
        progressDialog.show();
        return progressDialog;
    }

    public interface InputListener {
        void onClick(String inputText);
    }

    public interface SingSelectListener {
        void onClick(String selectText);
    }
}