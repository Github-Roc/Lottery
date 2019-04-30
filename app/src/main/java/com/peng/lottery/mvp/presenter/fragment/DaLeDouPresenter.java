package com.peng.lottery.mvp.presenter.fragment;

import android.text.TextUtils;

import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.utils.DateFormatUtil;
import com.peng.lottery.app.utils.MD5Util;
import com.peng.lottery.base.BaseLotteryPresenter;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_BLUE;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_RED;


public class DaLeDouPresenter extends BaseLotteryPresenter {

    private String[] mRedValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
    private String[] mBlueValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    @Inject
    public DaLeDouPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public List<LotteryNumber> getRandomLottery() {
        return getLotteryNumber("");
    }

    public List<LotteryNumber> getLotteryNumber(String luckyStr) {
        List<LotteryNumber> lotteryNumbers = new ArrayList<>();
        for (String value : getLotteryValue(NUMBER_BALL_TYPE_RED, luckyStr)) {
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumberValue(value);
            lotteryNumber.setNumberType(NUMBER_BALL_TYPE_RED.type);
            lotteryNumbers.add(lotteryNumber);
        }
        for (String value : getLotteryValue(NUMBER_BALL_TYPE_BLUE, luckyStr)) {
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumberValue(value);
            lotteryNumber.setNumberType(NUMBER_BALL_TYPE_BLUE.type);
            lotteryNumbers.add(lotteryNumber);
        }
        return lotteryNumbers;
    }

    private List<String> getLotteryValue(ActionConfig.NumberBallType numberType, String luckyStr) {
        List<String> numberBox = new ArrayList<>();
        int length = numberType == NUMBER_BALL_TYPE_RED ? 5 : 2;
        String[] valueBox = numberType == NUMBER_BALL_TYPE_RED ? mRedValues : mBlueValues;

        boolean isLucky = !TextUtils.isEmpty(luckyStr);
        luckyStr += DateFormatUtil.getDate();
        int luckyIndex = 0;
        byte[] luckyByte = MD5Util.encode(luckyStr).getBytes();

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
