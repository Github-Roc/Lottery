package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.base.BaseLotteryPresenter;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_OTHER;

public class PkShiPresenter extends BaseLotteryPresenter {

    @Inject
    public PkShiPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public List<LotteryNumber> getRandomLottery() {
        List<LotteryNumber> lotteryNumbers = new ArrayList<>();
        int intValue = 1;
        while (intValue <= 10) {
            String value = intValue < 10 ? "0" + intValue : "" + intValue;
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumberValue(value);
            lotteryNumber.setNumberType(NUMBER_BALL_TYPE_OTHER.type);
            lotteryNumbers.add(lotteryNumber);
            intValue++;
        }
        Collections.shuffle(lotteryNumbers);
        return lotteryNumbers;
    }

}
