package com.peng.lottery.app.type;

/**
 * 彩票类型
 */
public enum LotteryType {
    LOTTERY_TYPE_DLT("大乐透"),
    LOTTERY_TYPE_SSQ("双色球"),
    LOTTERY_TYPE_11X5("11选5"),
    LOTTERY_TYPE_PK10("PK拾");

    public String type;

    LotteryType(String type) {
        this.type = type;
    }
}