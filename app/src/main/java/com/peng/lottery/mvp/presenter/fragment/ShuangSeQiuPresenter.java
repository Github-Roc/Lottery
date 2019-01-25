package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.app.utils.MD5Util;
import com.peng.lottery.base.BaseLotteryPresenter;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import static com.peng.lottery.base.BaseLotteryPresenter.NumberType.NUMBER_TYPE_BLUE;
import static com.peng.lottery.base.BaseLotteryPresenter.NumberType.NUMBER_TYPE_RED;

public class ShuangSeQiuPresenter extends BaseLotteryPresenter {

    private String[] mRedValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};
    private String[] mBlueValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"};

    @Inject
    public ShuangSeQiuPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public List<LotteryNumber> getRandomLottery() {
        return getLotteryNumber(false, "");
    }

    public List<LotteryNumber> getLotteryNumber(boolean isLucky, String luckyStr){
        List<LotteryNumber> lotteryNumbers = new ArrayList<>();
        for (String value : getLotteryValue(NUMBER_TYPE_RED, isLucky, luckyStr)) {
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumberValue(value);
            lotteryNumber.setNumberType(NUMBER_TYPE_RED.type);
            lotteryNumbers.add(lotteryNumber);
        }
        for (String value : getLotteryValue(NUMBER_TYPE_BLUE, isLucky, luckyStr)) {
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumberValue(value);
            lotteryNumber.setNumberType(NUMBER_TYPE_BLUE.type);
            lotteryNumbers.add(lotteryNumber);
        }
        return lotteryNumbers;
    }

    private List<String> getLotteryValue(NumberType numberType, boolean isLucky, String luckyStr) {
        List<String> numberBox = new ArrayList<>();
        int luckyIndex = 0;
        byte[] luckyByte = MD5Util.encode(luckyStr).getBytes();
        int length = numberType == NUMBER_TYPE_RED ? 6 : 1;
        String[] valueBox = numberType == NUMBER_TYPE_RED ? mRedValues :mBlueValues;
        while (numberBox.size() < length) {
            String number;
            if (isLucky) {
                number = valueBox[luckyByte[luckyIndex] % valueBox.length];
                luckyIndex += 2;
            } else {
                number = valueBox[new Random().nextInt(valueBox.length)];
            }
            if (!numberBox.contains(number)) {
                numberBox.add(number);
            }
        }
        Collections.sort(numberBox);
        return numberBox;
    }
}
