package com.peng.lottery.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.peng.lottery.R;
import com.peng.lottery.app.MyApplication;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.SimpleBaseActivity;
import com.peng.lottery.mvp.ui.adapter.MainFragmentAdapter;
import com.peng.lottery.mvp.ui.fragment.AboutFragment;
import com.peng.lottery.mvp.ui.fragment.AddLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.MineLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.SettingFragment;

import butterknife.BindView;

import static com.peng.lottery.app.config.TipConfig.MAIN_EXIT_APP;

public class MainActivity extends SimpleBaseActivity {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_fab_add_lottery)
    FloatingActionButton mButtonAddLottery;
    @BindView(R.id.main_navigation_view)
    NavigationView mNavigationView;

    private Boolean isExit = false;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        // 添加菜单按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.main_menu_open, R.string.main_menu_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
        // 初始化ViewPager
        initViewPager();
    }

    @Override
    protected void initListener() {
        mNavigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    String fragmentName = null, titleText = null;
                    switch (menuItem.getItemId()) {
                        case R.id.main_menu_mine_lottery:
                            titleText = getString(R.string.title_mine_lottery);
                            fragmentName = MineLotteryFragment.class.getCanonicalName();
                            break;
                        case R.id.main_menu_setting:
                            titleText = getString(R.string.title_setting);
                            fragmentName = SettingFragment.class.getCanonicalName();
                            break;
                        case R.id.main_menu_about:
                            titleText = getString(R.string.title_about);
                            fragmentName = AboutFragment.class.getCanonicalName();
                            break;
                    }
                    ContainerActivity.start(mActivity, fragmentName, titleText);
                    mDrawerLayout.closeDrawers();
                    return true;
                });
        mButtonAddLottery.setOnClickListener(v ->
                ContainerActivity.start(mActivity, AddLotteryFragment.class.getCanonicalName(), getString(R.string.title_add_lottery)));
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            // 准备退出
            isExit = true;
            ToastUtil.showToast(mActivity, MAIN_EXIT_APP);

            new Handler().postDelayed(() -> {
                // 取消退出
                isExit = false;
            }, 2000);
        } else {
            MyApplication.exitApp();
        }
    }

    private void initViewPager() {
        MainFragmentAdapter mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}