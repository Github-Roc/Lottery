package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.presenter.BaseLotteryPresenter;

import javax.inject.Inject;

public class PkShiPresenter extends BaseLotteryPresenter {

    @Inject
    public PkShiPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
