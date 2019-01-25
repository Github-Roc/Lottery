package com.peng.lottery.mvp.contract.activity;

import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.db.bean.LotteryData;

import java.util.List;

public interface MineLotteryContract {

    interface View extends IView {

        void onLoadFinish(List<LotteryData> lotteryList);
    }

    interface Presenter {

        void getMineLotteryList();
    }
}
