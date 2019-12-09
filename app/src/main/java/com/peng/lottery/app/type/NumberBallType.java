package com.peng.lottery.app.type;

/**
 * 彩票号码球类型
 */
public enum NumberBallType {
    NUMBER_BALL_TYPE_RED("红球"),
    NUMBER_BALL_TYPE_BLUE("蓝球"),
    NUMBER_BALL_TYPE_NULL("空球"),
    NUMBER_BALL_TYPE_OTHER("其他");

    public String type;

    NumberBallType(String type) {
        this.type = type;
    }
}