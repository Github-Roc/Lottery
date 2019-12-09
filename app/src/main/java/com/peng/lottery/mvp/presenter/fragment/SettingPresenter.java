package com.peng.lottery.mvp.presenter.fragment;

import android.text.TextUtils;

import com.peng.lottery.app.utils.DateFormatUtil;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.model.DataManager;

import javax.inject.Inject;

import static com.peng.lottery.mvp.presenter.activity.SplashPresenter.SPLASH_ENDING;
import static com.peng.lottery.mvp.presenter.activity.SplashPresenter.SPLASH_SLOGAN;

public class SettingPresenter extends BasePresenter {

    @Inject
    public SettingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public String getSlogan() {
        return (String) mSpHelper.get(SPLASH_SLOGAN, "");
    }

    public void saveSlogan(String slogan) {
        String date = TextUtils.isEmpty(slogan) ? "" : DateFormatUtil.getSysDate();
        mSpHelper.put(SPLASH_SLOGAN, slogan);
        mSpHelper.put(SPLASH_ENDING, date);
    }
}
