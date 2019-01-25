package com.peng.lottery.app.config;

import android.support.v4.app.Fragment;

import com.peng.lottery.mvp.ui.fragment.DaLeDouFragment;
import com.peng.lottery.mvp.ui.fragment.ShiYiXuanWuFragment;
import com.peng.lottery.mvp.ui.fragment.PkShiFragment;
import com.peng.lottery.mvp.ui.fragment.ShuangSeQiuFragment;

import java.util.ArrayList;
import java.util.List;

public class ActionConfig {

    public static List<String> getMainTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("大乐透");
        tabs.add("双色球");
        tabs.add("11选5");
        tabs.add("北京PK拾");
        return tabs;
    }

    public static List<Fragment> getMainFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DaLeDouFragment());
        fragments.add(new ShuangSeQiuFragment());
        fragments.add(new ShiYiXuanWuFragment());
        fragments.add(new PkShiFragment());
        return fragments;
    }
}
