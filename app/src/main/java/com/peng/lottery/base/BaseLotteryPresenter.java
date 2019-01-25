package com.peng.lottery.base;

import com.peng.lottery.app.utils.DateFormatUtil;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.model.db.bean.LotteryNumberDao;

import java.util.List;

public abstract class BaseLotteryPresenter<V extends IView> extends BasePresenter<V> {

    private LotteryDataDao mLotteryDataDao;
    private LotteryNumberDao mLotteryNumberDao;

    public BaseLotteryPresenter(DataManager dataManager) {
        super(dataManager);

        mLotteryDataDao = mSQLiteHelper.getLotteryDataDao();
        mLotteryNumberDao = mSQLiteHelper.getLotteryNumberDao();
    }

    public void saveLottery(List<LotteryNumber> lotteryNumbers, LotteryType lotteryType) {
        saveLottery(lotteryNumbers, lotteryType, "");
    }

    public void saveLottery(List<LotteryNumber> lotteryNumbers, LotteryType lotteryType, String luckyType) {
        LotteryData lottery = new LotteryData();
        lottery.setLotteryType(lotteryType.type);
        lottery.setLuckyType(luckyType);
        lottery.setCreateData(DateFormatUtil.getSysDate());
        mLotteryDataDao.insert(lottery);

        for (LotteryNumber lotteryNumber : lotteryNumbers) {
            lotteryNumber.setLotteryId(lottery.getId());
            mLotteryNumberDao.insert(lotteryNumber);
        }
    }

    public abstract List<LotteryNumber> getRandomLottery();

    public enum LotteryType {
        LOTTERY_TYPE_DALETOU("大乐透"),
        LOTTERY_TYPE_SHUANGSEQIU("双色球"),
        LOTTERY_TYPE_SHIYIXUANWU("11选5"),
        LOTTERY_TYPE_PKSHI("PK拾");

        public String type;

        LotteryType(String type) {
            this.type = type;
        }
    }

    public enum NumberType {
        NUMBER_TYPE_RED("红球"),
        NUMBER_TYPE_BLUE("蓝球"),
        NUMBER_TYPE_OTHER("其他");

        public String type;

        NumberType(String type) {
            this.type = type;
        }
    }
}
