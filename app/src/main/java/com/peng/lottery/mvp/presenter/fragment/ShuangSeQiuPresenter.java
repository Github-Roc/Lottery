package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.presenter.BaseLotteryPresenter;

import javax.inject.Inject;

public class ShuangSeQiuPresenter extends BaseLotteryPresenter {

    @Inject
    public ShuangSeQiuPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
