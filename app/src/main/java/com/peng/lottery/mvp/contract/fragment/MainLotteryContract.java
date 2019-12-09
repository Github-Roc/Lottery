package com.peng.lottery.mvp.contract.fragment;

import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.List;

public interface MainLotteryContract {

    interface View extends IView {
        void createLotteryFinish();
    }

    interface Presenter {
        void getLotteryRecord(List<LotteryNumber> lotteryValue, LotteryType lotteryType);
    }
}
