package com.peng.lottery.mvp.presenter.fragment;

import android.text.TextUtils;

import com.peng.lottery.app.config.SharedPreferencesConfig;
import com.peng.lottery.app.utils.DateFormatUtil;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.model.DataManager;

import javax.inject.Inject;

public class SettingPresenter extends BasePresenter {

    @Inject
    public SettingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public String getSlogan() {
        return (String) mSpHelper.get(SharedPreferencesConfig.SPLASH_SLOGAN, "");
    }

    public void saveSlogan(String slogan) {
        String date = TextUtils.isEmpty(slogan) ? "" : DateFormatUtil.getSysDate();
        mSpHelper.put(SharedPreferencesConfig.SPLASH_SLOGAN, slogan);
        mSpHelper.put(SharedPreferencesConfig.SPLASH_ENDING, date);
    }
}
