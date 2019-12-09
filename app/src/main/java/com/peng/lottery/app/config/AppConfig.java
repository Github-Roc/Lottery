package com.peng.lottery.app.config;

import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.List;

import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_BLUE;
import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_NULL;
import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_OTHER;
import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_RED;

public class AppConfig {

    /** 当前是否是调试状态 */
    public static final boolean isDebug = false;
    /** AppTag */
    public static final String APP_TAG = "Lottery";
    /** 接口请求URL */
    public static final String LOTTERY_URL = "https://www.mxnzp.com/api/";


    private static List<LotteryNumber> dltNumberBalls;
    private static List<LotteryNumber> ssqNumberBalls;
    private static List<LotteryNumber> syxwNumberBalls;
    private static List<LotteryNumber> pksNumberBalls;

    /**
     * 获取对应彩票类型的可选号码球
     */
    public static List<LotteryNumber> getLotteryNumberBallList(LotteryType lotteryType) {
        switch (lotteryType) {
            case LOTTERY_TYPE_DLT:
                if (dltNumberBalls == null) {
                    dltNumberBalls = new ArrayList<>();
                    for (int number = 1; number <= 35; number++) {
                        LotteryNumber lotteryNumber = new LotteryNumber();
                        lotteryNumber.setNumberValue(number < 10 ? "0" + number : "" + number);
                        lotteryNumber.setNumberType(NUMBER_BALL_TYPE_RED.type);
                        dltNumberBalls.add(lotteryNumber);
                    }
                    for (int number = 1; number <= 12; number++) {
                        LotteryNumber lotteryNumber = new LotteryNumber();
                        lotteryNumber.setNumberValue(number < 10 ? "0" + number : "" + number);
                        lotteryNumber.setNumberType(NUMBER_BALL_TYPE_BLUE.type);
                        dltNumberBalls.add(lotteryNumber);
                    }
                }
                return dltNumberBalls;
            case LOTTERY_TYPE_SSQ:
                if (ssqNumberBalls == null) {
                    ssqNumberBalls = new ArrayList<>();
                    for (int number = 1; number <= 35; number++) {
                        LotteryNumber lotteryNumber = new LotteryNumber();
                        lotteryNumber.setNumberValue(number < 10 ? "0" + number : "" + number);
                        lotteryNumber.setNumberType(number <= 33 ? NUMBER_BALL_TYPE_RED.type : NUMBER_BALL_TYPE_NULL.type);
                        ssqNumberBalls.add(lotteryNumber);
                    }
                    for (int number = 1; number <= 16; number++) {
                        LotteryNumber lotteryNumber = new LotteryNumber();
                        lotteryNumber.setNumberValue(number < 10 ? "0" + number : "" + number);
                        lotteryNumber.setNumberType(NUMBER_BALL_TYPE_BLUE.type);
                        ssqNumberBalls.add(lotteryNumber);
                    }
                }
                return ssqNumberBalls;
            case LOTTERY_TYPE_11X5:
                if (syxwNumberBalls == null) {
                    syxwNumberBalls = new ArrayList<>();
                    for (int number = 1; number <= 11; number++) {
                        LotteryNumber lotteryNumber = new LotteryNumber();
                        lotteryNumber.setNumberValue(number < 10 ? "0" + number : "" + number);
                        lotteryNumber.setNumberType(NUMBER_BALL_TYPE_OTHER.type);
                        syxwNumberBalls.add(lotteryNumber);
                    }
                }
                return syxwNumberBalls;
            case LOTTERY_TYPE_PK10:
                if (pksNumberBalls == null) {
                    pksNumberBalls = new ArrayList<>();
                    for (int number = 1; number <= 10; number++) {
                        LotteryNumber lotteryNumber = new LotteryNumber();
                        lotteryNumber.setNumberValue(number < 10 ? "0" + number : "" + number);
                        lotteryNumber.setNumberType(NUMBER_BALL_TYPE_OTHER.type);
                        pksNumberBalls.add(lotteryNumber);
                    }
                }
                return pksNumberBalls;
        }
        return new ArrayList<>();
    }
}
