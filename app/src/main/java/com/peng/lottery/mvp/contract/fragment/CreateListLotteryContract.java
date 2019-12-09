package com.peng.lottery.mvp.contract.fragment;

import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.List;

public interface CreateListLotteryContract {

    interface View extends IView {
        void createLotteryFinish(List<List<LotteryNumber>> lotteryData);

        void saveLotterySuccess();
    }

    interface Presenter {
        void startCreateLottery(LotteryType lotteryType, List<LotteryNumber> selectNumbers, int createSize, int createTypePosition);

        void saveLotteryList(List<List<LotteryNumber>> data, LotteryType lotteryType);
    }
}
