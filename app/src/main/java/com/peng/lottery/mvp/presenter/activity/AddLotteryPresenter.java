package com.peng.lottery.mvp.presenter.activity;

import com.peng.lottery.base.BaseLotteryPresenter;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.model.db.bean.LotteryNumberDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_BLUE;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_OTHER;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_RED;

public class AddLotteryPresenter extends BaseLotteryPresenter {

    private LotteryDataDao mLotteryDataDao;
    private LotteryNumberDao mLotteryNumberDao;

    @Inject
    public AddLotteryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public boolean checkIsAdd(List<LotteryNumber> lotteryNumbers, String lotteryType, String numberType) {
        int count = 0;
        if (NUMBER_BALL_TYPE_RED.type.equals(numberType)) {
            for (LotteryNumber lotteryNumber : lotteryNumbers) {
                if (NUMBER_BALL_TYPE_RED.type.equals(lotteryNumber.getNumberType())) {
                    count++;
                }
            }
            return LOTTERY_TYPE_DLT.type.equals(lotteryType) ? count <= 5 : count <= 6;
        } else if (NUMBER_BALL_TYPE_BLUE.type.equals(numberType)) {
            for (LotteryNumber lotteryNumber : lotteryNumbers) {
                if (NUMBER_BALL_TYPE_BLUE.type.equals(lotteryNumber.getNumberType())) {
                    count++;
                }
            }
            return LOTTERY_TYPE_DLT.type.equals(lotteryType) ? count <= 2 : count <= 1;
        } else if (NUMBER_BALL_TYPE_OTHER.type.equals(numberType)) {
            return lotteryNumbers.size() >= 8;
        } else {
            return false;
        }
    }
}
