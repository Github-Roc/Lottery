package com.peng.lottery.mvp.contract.fragment;

import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.List;

public interface CreateListLotteryContract {

    interface View extends IView {
        void createLotteryFinish(List<List<LotteryNumber>> lotteryData);
    }

    interface Presenter {
        void startCreateLottery(ActionConfig.LotteryType lotteryType, String createType, int createSize);
    }
}
