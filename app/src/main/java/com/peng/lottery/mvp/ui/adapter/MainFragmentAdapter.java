package com.peng.lottery.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.peng.lottery.app.config.ActionConfig;

import java.util.List;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);

        mFragments = ActionConfig.getMainFragments();
        mTitles = ActionConfig.getMainTabs();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
