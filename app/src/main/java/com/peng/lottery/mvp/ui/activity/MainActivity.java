package com.peng.lottery.mvp.ui.activity;

import android.content.Intent;
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

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class MainActivity extends SimpleBaseActivity {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
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
                    Intent intent = new Intent();
                    switch (menuItem.getItemId()) {
                        case R.id.main_menu_mine_lottery:
                            intent.setClass(mActivity, MineLotteryActivity.class);
                            break;
                        case R.id.main_menu_about:
                            intent.setClass(mActivity, AboutActivity.class);
                            break;
                        case R.id.main_menu_setting:
                            intent.setClass(mActivity, SettingActivity.class);
                            break;
                    }
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    return true;
                });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        if (!isExit) {
            // 准备退出
            isExit = true;
            ToastUtil.showToast(mActivity, "再按一次退出程序");

            Timer tExit = new Timer();
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 取消退出
                    isExit = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            MyApplication.exitApp();
        }
    }

    private void initViewPager() {
        MainFragmentAdapter mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}