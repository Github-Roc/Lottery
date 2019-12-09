package com.peng.lottery.mvp.ui.fragment;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.peng.lottery.R;
import com.peng.lottery.base.SimpleBaseFragment;
import com.peng.lottery.mvp.ui.adapter.pager.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddLotteryFragment extends SimpleBaseFragment {

    @BindView(R.id.add_lottery_tab)
    TabLayout mTabLayout;
    @BindView(R.id.add_lottery_pager)
    ViewPager mViewPager;

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_add_lottery;
    }

    @Override
    protected void initView() {
        super.initView();

        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(mActivity.getSupportFragmentManager());
        fragmentAdapter.setData(getTitleList(), getFragmentList());
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CreateSingleLotteryFragment());
        fragments.add(new CreateListLotteryFragment());
        return fragments;
    }

    private List<String> getTitleList() {
        List<String> tabs = new ArrayList<>();
        tabs.add("单注生成");
        tabs.add("批量生成");
        return tabs;
    }
}
