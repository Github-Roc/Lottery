package com.peng.lottery.mvp.contract.fragment;

import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.db.bean.LotteryData;

import java.util.List;
import java.util.Map;

public interface MineLotteryContract {

    interface View extends IView {

        void onLoadFinish(List<LotteryData> lotteryList);

        void showVerificationResult(String message);
    }

    interface Presenter {

        void getMineLotteryList(Map<String, String> param);

        void verificationLottery();
    }
}
