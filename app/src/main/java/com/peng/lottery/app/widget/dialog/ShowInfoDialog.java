package com.peng.lottery.app.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.design.button.MaterialButton;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;


public class ShowInfoDialog {
    private Activity mActivity;

    private Dialog mShowInfoDialog;
    private TextView mTvInfoTile;
    private TextView mTvInfoContent;
    private MaterialButton mBtInfoOk;
    private MaterialButton mBtInfoCancel;

    public ShowInfoDialog(Activity activity) {
        this(activity, "", "");
    }

    public ShowInfoDialog(Activity activity, String msg) {
        this(activity, "", msg);
    }

    public ShowInfoDialog(Activity activity, String title, String msg) {
        this.mActivity = activity;

        initView();
        initListener();
        initContent(title, msg);
    }

    private void initView() {
        View view = View.inflate(mActivity, R.layout.dialog_showinfo, null);
        mShowInfoDialog = new Dialog(mActivity, R.style.DialogTheme);
        mShowInfoDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mTvInfoTile = view.findViewById(R.id.tv_info_title);
        mTvInfoContent = view.findViewById(R.id.tv_info_content);
        mBtInfoOk = view.findViewById(R.id.bt_info_ok);
        mBtInfoCancel = view.findViewById(R.id.bt_info_cancle);
        mTvInfoContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initListener() {
        mBtInfoOk.setOnClickListener(v -> close());
        mBtInfoCancel.setOnClickListener(v -> close());
    }

    private void initContent(String title, String msg) {
        if (!TextUtils.isEmpty(title)) {
            mTvInfoTile.setText(title);
        }
        if (msg == null) {
            msg = "";
        }
        if (!TextUtils.isEmpty(msg)) {
            if (msg.contains("<p>") || msg.contains("<br />")) {
                mTvInfoContent.setText(Html.fromHtml(msg));
            } else {
                mTvInfoContent.setText(msg);
            }
        } else {
            mTvInfoContent.setVisibility(View.GONE);
        }
    }

    public ShowInfoDialog setTitle(String title) {
        mTvInfoTile.setText(title);
        return this;
    }

    public ShowInfoDialog setContent(String content) {
        mTvInfoContent.setText(content);
        return this;
    }

    public ShowInfoDialog setButtonText(String text) {
        mBtInfoOk.setText(text);
        return this;
    }

    public ShowInfoDialog setButtonText(String text1, String text2) {
        mBtInfoOk.setText(text1);
        mBtInfoCancel.setText(text2);
        return this;
    }

    public ShowInfoDialog setOnNotCloseClickListener(final View.OnClickListener listener) {
        mBtInfoOk.setOnClickListener(listener);
        return this;
    }

    public ShowInfoDialog setOnClickListener(final View.OnClickListener listener) {
        mBtInfoOk.setOnClickListener(v -> {
            close();
            listener.onClick(v);
        });
        return this;
    }

    public ShowInfoDialog setOnClickListener(final View.OnClickListener listener1, final View.OnClickListener listener2) {
        mBtInfoOk.setOnClickListener(v -> {
            close();
            listener1.onClick(v);
        });
        mBtInfoCancel.setOnClickListener(v -> {
            close();
            listener2.onClick(v);
        });
        return this;
    }

    public ShowInfoDialog showCancelButton(boolean isShow) {
        if (isShow) {
            mBtInfoCancel.setVisibility(View.VISIBLE);
        } else {
            mBtInfoCancel.setVisibility(View.GONE);
        }
        return this;
    }

    public ShowInfoDialog setCancelable(boolean flag) {
        mShowInfoDialog.setCancelable(flag);
        return this;
    }

    public ShowInfoDialog setCanceledOnTouchOutside(boolean flag) {
        mShowInfoDialog.setCanceledOnTouchOutside(flag);
        return this;
    }

    public void show() {
        if (mShowInfoDialog != null) {
            mShowInfoDialog.show();
        }
    }

    public void close() {
        if (mShowInfoDialog != null) {
            mShowInfoDialog.dismiss();
        }
    }
}
