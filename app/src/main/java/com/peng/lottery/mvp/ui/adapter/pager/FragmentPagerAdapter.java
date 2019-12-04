package com.peng.lottery.mvp.ui.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.peng.lottery.app.config.ActionConfig;

import java.util.List;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        mFragments = ActionConfig.getMainFragments();
        mTitles = ActionConfig.getMainTabs();
    }

    public void setData(List<Fragment> fragments, List<String> titles) {
        this.mFragments = fragments;
        this.mTitles = titles;
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
