package com.peng.lottery.base;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peng.lottery.app.helper.AgentWebHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * Fragment的基类
 */

public abstract class SimpleBaseFragment extends Fragment {

    /** 当前Fragment是否有缓存视图，需要初始化。 */
    protected boolean isInit;
    /** 当前Fragment所依赖的Activity */
    protected SimpleBaseActivity mActivity;

    /** ButterKnife */
    private Unbinder mUnBinder;
    /** 当前Fragment的缓存 view */
    private View rootView;
    /** AgentWeb工具类 */
    private AgentWebHelper mAgentWebHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            isInit = true;
            rootView = inflater.inflate(setLayoutResID(), null);
        }
        //绑定ButterKnife
        mUnBinder = ButterKnife.bind(this, rootView);

        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isInit) {
            initView();
            initListener();
            loadData();
            isInit = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAgentWebHelper != null) {
            mAgentWebHelper.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAgentWebHelper != null) {
            mAgentWebHelper.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑ButterKnife
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        if (mAgentWebHelper != null) {
            mAgentWebHelper.onDestroy();
        }
    }

    protected AgentWebHelper getWebUtil(){
        if (mAgentWebHelper == null) {
            mAgentWebHelper = new AgentWebHelper(mActivity);
        }
        return mAgentWebHelper;
    }

    public void showTipDialog(String message, DialogInterface.OnClickListener listener) {
        mActivity.showTipDialog(message, listener);
    }

    public void showLoadingDialog(String text) {
        mActivity.showLoadingDialog(text);
    }

    public void dismissLoadingDialog() {
        mActivity.dismissLoadingDialog();
    }

    /**
     * 初始化
     * @des 子类可选择复写，进行一些初始化操作，会在setLayoutView()方法前执行。
     */
    protected void init() {
        mActivity = (SimpleBaseActivity) getActivity();
    }

    /**
     * 初始化界面视图
     * @des 子类可选择复，写初始化视图，会在initInject()方法后执行。
     */
    protected void initView() {
    }

    /**
     * 初始化监听器
     * @des 子类可选择复写, 初始化界面内的监听，会在initView()方法后执行
     */
    protected void initListener() {
    }

    /**
     * 加载数据
     * @des 子类可选择复写，用来加载界面数据，会在initListener()方法后执行
     */
    protected void loadData() {
    }

    /**
     * 设置该页面的布局文件资源ID
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResID();

}
