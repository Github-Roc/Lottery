package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.presenter.BaseLotteryPresenter;

import javax.inject.Inject;

public class ShiYiXuanWuPresenter extends BaseLotteryPresenter {

    @Inject
    public ShiYiXuanWuPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
