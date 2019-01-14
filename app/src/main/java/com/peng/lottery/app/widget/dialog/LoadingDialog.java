package com.peng.lottery.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;


/**
 * Created by tofirst on 2017/12/15.
 */
public class LoadingDialog {
    public static final int DIALOG_TYPE_LOADING = 666;
    public static final int DIALOG_TYPE_PROGRESS = 888;

    private int mDialogType;
    private Dialog mLoadingDialog;
    private LoadingView mLoadingView;
    private ProgressView mProgressView;
    private TextView mLoadingText;

    public LoadingDialog(Context context, int dialogType, String msg) {
        View view;
        mDialogType = dialogType;
        if (mDialogType == DIALOG_TYPE_PROGRESS) {
            view = View.inflate(context, R.layout.dialog_progressbar, null);
            mProgressView = view.findViewById(R.id.dialog_progress_view);
        } else {
            view = View.inflate(context, R.layout.dialog_loading, null);
            mLoadingView = view.findViewById(R.id.dialog_loading_view);
        }
        initView(context, view);
        changeText(msg);
    }

    private void initView(Context context, View view) {
        // 页面中显示文本
        mLoadingText = view.findViewById(R.id.dialog_loading_text);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public LoadingDialog changeText(String text) {
        mLoadingText.setText(text);
        return this;
    }

    public LoadingDialog setMax(int value) {
        mProgressView.setMax(value);
        return this;
    }

    public LoadingDialog setProgress(int value) {
        mProgressView.setProgress(value);
        return this;
    }

    public void show() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
            if (mDialogType == DIALOG_TYPE_LOADING) {
                mLoadingView.startAnim();
            }
        }
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            if (mDialogType == DIALOG_TYPE_LOADING) {
                mLoadingView.stopAnim();
            }
        }
    }
}

