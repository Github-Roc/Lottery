package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.presenter.BaseLotteryPresenter;

import javax.inject.Inject;

public class DaLeDouPresenter extends BaseLotteryPresenter {

    @Inject
    public DaLeDouPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
