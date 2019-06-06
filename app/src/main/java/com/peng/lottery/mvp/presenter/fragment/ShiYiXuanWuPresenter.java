package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.presenter.BaseLotteryPresenter;

import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.inject.Inject;

public class ShiYiXuanWuPresenter extends BaseLotteryPresenter {

    @Inject
    public ShiYiXuanWuPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public String getLotteryProbability(String type) {
        double probability = 0;
        switch (type) {
            case "任选二":
                probability = 1 / 5.5d;
                break;
            case "任选三":
                probability = 1 / 16.5d;
                break;
            case "任选四":
                probability = 1 / 66d;
                break;
            case "任选五":
                probability = 1 / 462d;
                break;
            case "任选六":
                probability = 1 / 77d;
                break;
            case "任选七":
                probability = 1 / 22d;
                break;
            case "任选八":
                probability = 1 / 8.25d;
                break;
            case "前二直选":
                probability = 1 / 55d;
                break;
            case "前三直选":
                probability = 1 / 165d;
                break;
            case "前二组选":
                probability = 1 / 110d;
                break;
            case "前三组选":
                probability = 1 / 990d;
                break;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(5);
        numberFormat.setRoundingMode(RoundingMode.UP);
        return numberFormat.format(probability * 100);
    }

    public int getLotteryPrice(String type) {
        int price = 0;
        switch (type) {
            case "任选二":
                price = 6;
                break;
            case "任选三":
                price = 19;
                break;
            case "任选四":
                price = 78;
                break;
            case "任选五":
                price = 540;
                break;
            case "任选六":
                price = 90;
                break;
            case "任选七":
                price = 26;
                break;
            case "任选八":
                price = 9;
                break;
            case "前二直选":
                price = 65;
                break;
            case "前三直选":
                price = 195;
                break;
            case "前二组选":
                price = 130;
                break;
            case "前三组选":
                price = 1170;
                break;
        }
        return price;
    }
}
