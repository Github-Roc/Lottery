package com.peng.lottery.mvp.presenter.fragment;

import android.text.TextUtils;

import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.contract.fragment.MineLotteryContract;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.model.web.LotteryObserver;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;

public class MineLotteryPresenter extends BasePresenter<MineLotteryContract.View> implements MineLotteryContract.Presenter {
    private LotteryDataDao mLotteryDataDao;

    @Inject
    public MineLotteryPresenter(DataManager dataManager) {
        super(dataManager);

        mLotteryDataDao = mSQLiteHelper.getLotteryDataDao();
    }

    @Override
    public void getMineLotteryList(Map<String, String> param) {
        List<LotteryData> lotteryList = null;
        if (param != null && param.size() > 0) {
            String lotteryDate = param.get("lotteryDate");
            String lotteryLabel = param.get("lotteryLabel");
            String lotteryType = param.get("lotteryType");
            if (!TextUtils.isEmpty(lotteryDate)) {
                lotteryList = mLotteryDataDao.queryBuilder()
                        .where(LotteryDataDao.Properties.CreateDate.eq(lotteryDate))
                        .list();
            } else if (!TextUtils.isEmpty(lotteryLabel)) {
                lotteryList = mLotteryDataDao.queryBuilder()
                        .where(LotteryDataDao.Properties.LotteryLabel.eq(lotteryLabel))
                        .list();
            } else if (!TextUtils.isEmpty(lotteryType)) {
                lotteryList = mLotteryDataDao.queryBuilder()
                        .where(LotteryDataDao.Properties.LotteryType.eq(lotteryType))
                        .list();
            }
        } else {
            lotteryList = mLotteryDataDao.loadAll();
        }
        mView.onLoadFinish(lotteryList);
    }

    @Override
    public void verificationLottery() {
        beforeExecute(mRetrofitHelper.getLastLottery())
                .subscribe(new LotteryObserver<LotteryBean>() {
                    @Override
                    public void onError(String errorMsg) {
                        mView.showToast(errorMsg);
                    }

                    @Override
                    public void onSuccess(LotteryBean data) {
                        String[] str = data.openCode.split("\\+");
                        String[] redBall = str[0].split(",");
                        String blueBall = str[1];
                        StringBuilder stringBuilder = new StringBuilder();
                        for (LotteryData lottery : getLotteryListByType(LOTTERY_TYPE_SSQ)) {
                            int redSize = 0, blueSize = 0;
                            for (LotteryNumber number : lottery.getLotteryValue()) {
                                if (number.getNumberType().equals(ActionConfig.NumberBallType.NUMBER_BALL_TYPE_BLUE.type)) {
                                    if (blueBall.equals(number.getNumberValue())) {
                                        blueSize++;
                                    }
                                } else {
                                    for (String redNumber : redBall) {
                                        if (redNumber.equals(number.getNumberValue())) {
                                            redSize++;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (blueSize >= 1 || redSize >= 4) {
                                if (stringBuilder.length() == 0) {
                                    stringBuilder.append("恭喜中奖！").append("<br />");
                                }
                                stringBuilder.append("号码:").append(lottery.getLotteryValue())
                                        .append("中了:").append(redSize).append("+").append(blueSize)
                                        .append("<br />");
                            }
                        }
                        if (stringBuilder.length() == 0) {
                            mView.showToast("很遗憾，没有号码中奖！");
                        } else {
                            mView.showVerificationResult(stringBuilder.toString());
                        }
                    }
                });
    }

    public boolean isHasList(ActionConfig.LotteryType lotteryType) {
        List<LotteryData> lotteryList = getLotteryListByType(lotteryType);
        return lotteryList != null && lotteryList.size() > 0;
    }

    public void deleteAll() {
        mLotteryDataDao.deleteAll();
    }

    public void deleteLottery(LotteryData lottery) {
        mLotteryDataDao.delete(lottery);
    }

    private List<LotteryData> getLotteryListByType(ActionConfig.LotteryType lotteryType) {
        if (lotteryType != null) {
            return mLotteryDataDao.queryBuilder()
                    .where(LotteryDataDao.Properties.LotteryType.eq(lotteryType.type))
                    .list();
        }
        return mLotteryDataDao.loadAll();
    }
}
