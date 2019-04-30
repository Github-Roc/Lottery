package com.peng.lottery.base;

import android.text.TextUtils;

import com.peng.lottery.app.config.ActionConfig.LotteryType;
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

    public String saveLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        return saveLottery("", lotteryValue, lotteryType, "");
    }

    public String saveLottery(List<LotteryNumber> lotteryValue, LotteryType lotteryType, String luckyStr) {
        if (TextUtils.isEmpty(luckyStr)) {
            return saveLottery("", lotteryValue, lotteryType, "");
        } else {
            return saveLottery("幸运号码", lotteryValue, lotteryType, luckyStr);
        }
    }

    public String saveLottery(String lotteryLabel, List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        return saveLottery(lotteryLabel, lotteryValue, lotteryType, "");
    }

    private String saveLottery(String lotteryLabel, List<LotteryNumber> lotteryValue, LotteryType lotteryType, String luckyStr) {
        if (lotteryValue.get(0).getId() == null) {
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
                lotteryNumber.setLotteryId(lottery.getId());
                mLotteryNumberDao.insert(lotteryNumber);
            }
            return "保存成功！";
        } else {
            return "该号码以保存！";
        }
    }
}
