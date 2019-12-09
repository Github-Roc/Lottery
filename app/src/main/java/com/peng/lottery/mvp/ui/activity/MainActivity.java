package com.peng.lottery.mvp.ui.activity;

import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.peng.lottery.R;
import com.peng.lottery.app.MyApplication;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.SimpleBaseActivity;
import com.peng.lottery.mvp.ui.adapter.pager.FragmentPagerAdapter;
import com.peng.lottery.mvp.ui.fragment.AboutFragment;
import com.peng.lottery.mvp.ui.fragment.AddLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.MainLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.MineLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_PK10;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_SSQ;
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
        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        fragmentAdapter.setData(getMainTabs(), getMainFragments());
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<String> getMainTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add(LOTTERY_TYPE_DLT.type);
        tabs.add(LOTTERY_TYPE_SSQ.type);
        tabs.add(LOTTERY_TYPE_11X5.type);
        tabs.add(LOTTERY_TYPE_PK10.type);
        return tabs;
    }

    private List<Fragment> getMainFragments() {
        List<Fragment> fragments = new ArrayList<>();
        MainLotteryFragment fragment = new MainLotteryFragment();
        fragment.setLotteryType(LOTTERY_TYPE_DLT);
        fragments.add(fragment);
        fragment = new MainLotteryFragment();
        fragment.setLotteryType(LOTTERY_TYPE_SSQ);
        fragments.add(fragment);
        fragment = new MainLotteryFragment();
        fragment.setLotteryType(LOTTERY_TYPE_11X5);
        fragments.add(fragment);
        fragment = new MainLotteryFragment();
        fragment.setLotteryType(LOTTERY_TYPE_PK10);
        fragments.add(fragment);
        return fragments;
    }
}