package com.peng.lottery.mvp.presenter.activity;

import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.contract.activity.MainContract;
import com.peng.lottery.mvp.model.DataManager;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
