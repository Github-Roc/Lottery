package com.peng.lottery.mvp.presenter.activity;

import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.contract.activity.MineLotteryContract;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;

import java.util.List;

import javax.inject.Inject;

public class MineLotteryPresenter extends BasePresenter<MineLotteryContract.View> implements MineLotteryContract.Presenter {
    private LotteryDataDao mLotteryDataDao;

    @Inject
    public MineLotteryPresenter(DataManager dataManager) {
        super(dataManager);

        mLotteryDataDao = mSQLiteHelper.getLotteryDataDao();
    }

    @Override
    public void getMineLotteryList() {
        List<LotteryData> lotteryList = mLotteryDataDao.loadAll();
        mView.onLoadFinish(lotteryList);
    }
}
