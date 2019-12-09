package com.peng.lottery.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.LogUtil;
import com.peng.lottery.base.SimpleBaseActivity;

import butterknife.BindView;

public class ContainerActivity extends SimpleBaseActivity {

    @BindView(R.id.layout_container)
    LinearLayout layoutContainer;

    public String mTitleText;
    public String mFragmentName;

    private Fragment mFragment;

    @Override
    protected void init() {
        super.init();

        Intent intent = getIntent();
        mTitleText = intent.getStringExtra("titleText");
        mFragmentName = intent.getStringExtra("fragmentName");
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_container;
    }

    @Override
    protected void initView() {
        super.initView();

        mActivityTitle.setText(mTitleText);
        initFragment();
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void initFragment() {
        try {
            if (TextUtils.isEmpty(mFragmentName)) {
                LogUtil.e("can not find page fragmentName");
                return;
            }
            Class<?> fragmentClass = Class.forName(mFragmentName);
            mFragment = (Fragment) fragmentClass.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, mFragment);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }

    public static void start(Activity activity, String fragmentName, String title) {
        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra("titleText", title);
        intent.putExtra("fragmentName", fragmentName);
        activity.startActivity(intent);
    }
}
