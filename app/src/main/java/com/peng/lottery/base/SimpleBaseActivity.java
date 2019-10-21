package com.peng.lottery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.blankj.rxbus.RxBus;
import com.peng.lottery.R;
import com.peng.lottery.app.MyApplication;
import com.peng.lottery.app.webview.AgentWebHelper;
import com.peng.lottery.app.widget.SlidingLayout;
import com.peng.lottery.app.widget.dialog.LoadingDialog;
import com.peng.lottery.app.widget.dialog.ShowInfoDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * 无MVP  Activity的基类
 */

public abstract class SimpleBaseActivity extends AppCompatActivity {

    /** ButterKnife */
    private Unbinder mUnBinder;
    /** AgentWeb工具类 */
    private AgentWebHelper mAgentWebHelper;
    /** 当前的Activity实例 */
    protected SimpleBaseActivity mActivity;
    /** 加载中Dialog */
    public LoadingDialog mLoadingDialog;

    protected Toolbar mToolbar;
    protected TextView mActivityTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(setLayoutResID());

        initView();
        initListener();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAgentWebHelper != null) {
            mAgentWebHelper.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAgentWebHelper != null) {
            mAgentWebHelper.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑ButterKnife
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        if (mAgentWebHelper != null) {
            mAgentWebHelper.onDestroy();
        }
        MyApplication.activityStack.remove(this);
        dismissLoadingDialog();
        mActivity = null;
    }

    public AgentWebHelper getWebHelper() {
        if (mAgentWebHelper == null) {
            mAgentWebHelper = new AgentWebHelper(mActivity);
        }
        return mAgentWebHelper;
    }

    public void showTipDialog(String message, View.OnClickListener onClickListener) {
        if (mActivity != null) {
            new ShowInfoDialog(mActivity, message)
                    .showCancelButton(true)
                    .setOnClickListener(onClickListener)
                    .show();
        }
    }

    public void showLoadingDialog(int type, String text) {
        if (mActivity != null) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(mActivity, type, text);
                mLoadingDialog.show();
            } else {
                mLoadingDialog.changeText(text);
            }
        }
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.close();
            mLoadingDialog = null;
        }
    }

    /**
     * 初始化界面ToolBar
     */
    private void initToolBar() {
        mToolbar = findViewById(R.id.app_toolbar);
        mActivityTitle = findViewById(R.id.tv_activity_title);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    /**
     * 初始化
     *
     * @des 子类可选择复写，进行一些初始化操作，会在setLayoutResID()方法前执行。
     */
    protected void init() {
        // 将activity添加到管理栈中
        mActivity = this;
        MyApplication.activityStack.add(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 初始化界面视图
     *
     * @des 子类可选择复写，初始化视图，会在initInject()方法后执行。
     */
    protected void initView() {
        //绑定ButterKnife
        mUnBinder = ButterKnife.bind(this);

        if (enableSlidingFinish()) {
            SlidingLayout slidingLayout = new SlidingLayout(this);
            slidingLayout.bindActivity(this);
        }
        if (enableInitToolBar()) {
            initToolBar();
        }
    }

    /**
     * 初始化监听器
     *
     * @des 子类可选择复写, 初始化界面内的监听，会在initView()方法后执行
     */
    protected void initListener() {
    }

    /**
     * 加载数据
     *
     * @des 子类可选择复写，用来加载界面数据，会在initListener()方法后执行
     */
    protected void loadData() {
    }

    /**
     * 是否打开左滑关闭页面
     *
     * @return true 是 false 否
     */
    protected boolean enableSlidingFinish() {
        return false;
    }

    /**
     * 是否初始化ToolBar
     *
     * @return true 是 false 否
     */
    protected boolean enableInitToolBar() {
        return true;
    }

    /**
     * 设置该页面的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResID();
}
