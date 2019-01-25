package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.base.BaseLotteryPresenter;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import static com.peng.lottery.base.BaseLotteryPresenter.NumberType.NUMBER_TYPE_OTHER;

public class ShiYiXuanWuPresenter extends BaseLotteryPresenter {

    private String[] mValueBox = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};

    @Inject
    public ShiYiXuanWuPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public List<LotteryNumber> getRandomLottery() {
        List<LotteryNumber> lotteryNumbers = new ArrayList<>();
        List<String> numberBox = new ArrayList<>();
        while (numberBox.size() < 5) {
            String number = mValueBox[new Random().nextInt(mValueBox.length)];
            if (!numberBox.contains(number)) {
                numberBox.add(number);
            }
        }
        Collections.sort(numberBox);
        for (String value : numberBox) {
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumberValue(value);
            lotteryNumber.setNumberType(NUMBER_TYPE_OTHER.type);
            lotteryNumbers.add(lotteryNumber);
        }
        return lotteryNumbers;
    }

}
