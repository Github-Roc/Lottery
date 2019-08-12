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
        setTitle(title);
        setContent(msg);
    }

    public ShowInfoDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvInfoTile.setText(title);
        }
        return this;
    }

    public ShowInfoDialog setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            if (content.contains("<p>") || content.contains("<br />")) {
                mTvInfoContent.setText(Html.fromHtml(content));
            } else {
                mTvInfoContent.setText(content);
            }
        }
        return this;
    }

    public ShowInfoDialog setButtonText(String... text) {
        if (text != null) {
            if (text.length >= 1) {
                mBtInfoOk.setText(text[0]);
            }
            if (text.length >= 2) {
                mBtInfoCancel.setVisibility(View.VISIBLE);
                mBtInfoCancel.setText(text[1]);
            }
        }
        return this;
    }

    public ShowInfoDialog setButtonColor(int... color) {
        if (color != null) {
            if (color.length >= 1) {
                mBtInfoOk.setTextColor(color[0]);
            }
            if (color.length >= 2) {
                mBtInfoCancel.setTextColor(color[1]);
            }
        }
        return this;
    }

    public ShowInfoDialog setOnClickListener(final View.OnClickListener... listener) {
        if (listener != null) {
            if (listener.length >= 1) {
                mBtInfoOk.setOnClickListener(v -> {
                    close();
                    listener[0].onClick(v);
                });
            }
            if (listener.length >= 2) {
                mBtInfoCancel.setOnClickListener(v -> {
                    close();
                    listener[1].onClick(v);
                });
            }
        }

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
