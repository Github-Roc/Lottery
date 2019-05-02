package com.peng.lottery.mvp.presenter;

import android.text.TextUtils;

import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.config.ActionConfig.LotteryType;
import com.peng.lottery.app.utils.DateFormatUtil;
import com.peng.lottery.app.utils.MD5Util;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.model.db.bean.LotteryNumberDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_PK10;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_RED;

public class BaseLotteryPresenter<V extends IView> extends BasePresenter<V> {

    protected int mMaxSize;
    protected boolean isSort;

    private String mLuckyStr;

    private LotteryDataDao mLotteryDataDao;
    private LotteryNumberDao mLotteryNumberDao;

    public BaseLotteryPresenter(DataManager dataManager) {
        super(dataManager);

        mLotteryDataDao = mSQLiteHelper.getLotteryDataDao();
        mLotteryNumberDao = mSQLiteHelper.getLotteryNumberDao();
    }

    public String saveLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        return saveLottery(lotteryValue, lotteryType, "", "");
    }

    public String saveLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType, String luckyStr) {
        if (TextUtils.isEmpty(luckyStr)) {
            return saveLottery(lotteryValue, lotteryType);
        } else {
            return saveLottery(lotteryValue, lotteryType, "幸运号码", luckyStr);
        }
    }

    public String saveLottery(String lotteryLabel, List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        return saveLottery(lotteryValue, lotteryType, lotteryLabel, "");
    }

    /**
     * 保存彩票数据
     *
     * @param lotteryValue 彩票号码集合
     * @param lotteryType  彩票类型
     * @param lotteryLabel 彩票标签
     * @param luckyStr     输入的幸运字符串
     * @return 保存结果
     */
    private String saveLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType, String lotteryLabel, String luckyStr) {
        if (!checkLotterySize(lotteryValue, lotteryType)) {
            return "号码不完整，请先选择号码！";
        }
        if (TextUtils.isEmpty(lotteryValue.get(0).getNumberValue())) {
            return "该号码以保存！";
        }
        LotteryData lottery = new LotteryData();
        lottery.setLotteryType(lotteryType.type);
        lottery.setCreateData(DateFormatUtil.getSysDate());
        if (!TextUtils.isEmpty(luckyStr)) {
            lottery.setLuckyStr(luckyStr);
        }
        if (!TextUtils.isEmpty(lotteryLabel)) {
            lottery.setLotteryLabel(lotteryLabel);
        }
        mLotteryDataDao.insert(lottery);

        for (LotteryNumber lotteryNumber : lotteryValue) {
            LotteryNumber newNumber = new LotteryNumber();
            newNumber.setLotteryId(lottery.getId());
            newNumber.setNumberType(lotteryNumber.getNumberType());
            newNumber.setNumberValue(lotteryNumber.getNumberValue());
            mLotteryNumberDao.insert(newNumber);
        }
        lotteryValue.add(0, new LotteryNumber());
        return "保存成功！";
    }

    /**
     * 获取一注随机生成的彩票
     */
    public void getRandomLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        if (lotteryValue == null) {
            lotteryValue = new ArrayList<>();
        } else {
            lotteryValue.clear();
        }
        complementLottery(lotteryValue, lotteryType);
    }

    /**
     * 补齐彩票号码
     *
     * @param lotteryValue 现有号码集合
     * @param lotteryType  彩票类型
     */
    public void complementLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        List<LotteryNumber> numberBallList = ActionConfig.getLotteryNumberBallList(lotteryType);
        if (LOTTERY_TYPE_DLT.equals(lotteryType) || LOTTERY_TYPE_SSQ.equals(lotteryType)) {
            List<LotteryNumber> redBallList = new ArrayList<>();
            List<LotteryNumber> blueBallList = new ArrayList<>();
            for (LotteryNumber lotteryNumber : lotteryValue) {
                if (NUMBER_BALL_TYPE_RED.type.equals(lotteryNumber.getNumberType())) {
                    redBallList.add(lotteryNumber);
                } else {
                    blueBallList.add(lotteryNumber);
                }
            }

            int redBallSize = LOTTERY_TYPE_DLT.equals(lotteryType) ? 35 : 33;
            int blueBallSize = LOTTERY_TYPE_DLT.equals(lotteryType) ? 12 : 16;
            int redBallLength = LOTTERY_TYPE_DLT.equals(lotteryType) ? 5 : 6;
            int blueBallLength = LOTTERY_TYPE_DLT.equals(lotteryType) ? 2 : 1;

            boolean isLucky = !TextUtils.isEmpty(mLuckyStr);
            int luckyIndex = 0;
            byte[] luckyByte = null;
            if (isLucky) {
                mLuckyStr += DateFormatUtil.getDate();
                luckyByte = MD5Util.encode(mLuckyStr).getBytes();
            }

            while ((redBallList.size() + blueBallList.size()) < 7 && lotteryValue.size() < 7) {
                if (isLucky) {
                    LotteryNumber numberBall;
                    if (lotteryValue.size() < redBallLength) {
                        numberBall = numberBallList.get(luckyByte[luckyIndex] % redBallSize);
                    } else {
                        numberBall = numberBallList.get((luckyByte[luckyIndex] % blueBallSize) + 35);
                    }
                    luckyIndex++;
                    if (!lotteryValue.contains(numberBall)) {
                        lotteryValue.add(numberBall);
                    }
                } else {
                    LotteryNumber numberBall = numberBallList.get(new Random().nextInt(numberBallList.size()));
                    if (NUMBER_BALL_TYPE_RED.type.equals(numberBall.getNumberType())) {
                        if (!redBallList.contains(numberBall) && redBallList.size() < redBallLength) {
                            redBallList.add(numberBall);
                        }
                    } else {
                        if (!blueBallList.contains(numberBall) && blueBallList.size() < blueBallLength) {
                            blueBallList.add(numberBall);
                        }
                    }
                }
            }
            if (!isLucky) {
                lotteryValue.clear();
                lotteryValue.addAll(redBallList);
                lotteryValue.addAll(blueBallList);
            }
            Collections.sort(lotteryValue);
        } else if (LOTTERY_TYPE_11X5.equals(lotteryType)) {
            while (lotteryValue.size() < mMaxSize) {
                LotteryNumber numberBall = numberBallList.get(new Random().nextInt(numberBallList.size()));
                if (!lotteryValue.contains(numberBall)) {
                    lotteryValue.add(numberBall);
                }
            }
            if (isSort) {
                Collections.sort(lotteryValue);
            }
        } else if (LOTTERY_TYPE_PK10.equals(lotteryType)) {
            Collections.shuffle(numberBallList);
            lotteryValue.clear();
            lotteryValue.addAll(numberBallList);
        }
    }

    /**
     * 设置输入的幸运字符串
     *
     * @param luckyStr 输入的幸运字符串
     */
    public void setLuckyStr(String luckyStr) {
        mLuckyStr = luckyStr;
    }

    /**
     * 设置11选5类型，固定号码size
     *
     * @param type 11选5类型
     * @return 该类型对应的长度
     */
    public int set11x5Type(String type) {
        isSort = true;
        switch (type) {
            case "任选二":
                mMaxSize = 2;
                break;
            case "任选三":
                mMaxSize = 3;
                break;
            case "任选四":
                mMaxSize = 4;
                break;
            case "任选五":
                mMaxSize = 5;
                break;
            case "任选六":
                mMaxSize = 6;
                break;
            case "任选七":
                mMaxSize = 7;
                break;
            case "任选八":
                mMaxSize = 8;
                break;
            case "前二组选":
                mMaxSize = 2;
                isSort = false;
                break;
            case "前三组选":
                mMaxSize = 3;
                isSort = false;
                break;
            case "前二直选":
                mMaxSize = 2;
                isSort = false;
                break;
            case "前三直选":
                mMaxSize = 3;
                isSort = false;
                break;
        }
        return mMaxSize;
    }

    /**
     * 检测彩票号码长度是否符合规范
     */
    protected boolean checkLotterySize(List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        if (LOTTERY_TYPE_DLT.equals(lotteryType) || LOTTERY_TYPE_SSQ.equals(lotteryType)) {
            return lotteryValue != null && lotteryValue.size() >= 7;
        } else if (LOTTERY_TYPE_11X5.equals(lotteryType)) {
            return lotteryValue != null && lotteryValue.size() >= mMaxSize;
        }
        return LOTTERY_TYPE_PK10.equals(lotteryType);
    }
}
