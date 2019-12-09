package com.peng.lottery.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.app.MyApplication;
import com.peng.lottery.app.utils.DialogUtil;
import com.peng.lottery.app.helper.AgentWebHelper;
import com.peng.lottery.app.widget.SlidingLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * 无MVP  Activity的基类
 */

public abstract class SimpleBaseActivity extends AppCompatActivity {

    /**
     * ButterKnife
     */
    private Unbinder mUnBinder;
    /**
     * AgentWeb工具类
     */
    private AgentWebHelper mAgentWebHelper;
    /**
     * 当前的Activity实例
     */
    protected SimpleBaseActivity mActivity;
    /**
     * 加载中Dialog
     */
    public ProgressDialog mLoadingDialog;

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

    public void showTipDialog(String message, DialogInterface.OnClickListener listener) {
        if (mActivity != null) {
            DialogUtil.showTipDialog(mActivity, message, listener);
        }
    }

    public void showLoadingDialog(String text) {
        if (mActivity != null && mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.showLoadingDialog(mActivity, text);
        }
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
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
