package com.peng.lottery.mvp.presenter.fragment;

import android.annotation.SuppressLint;

import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.app.config.TipConfig;
import com.peng.lottery.app.helper.LotteryHelper;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.contract.fragment.CreateListLotteryContract;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.peng.lottery.app.config.TipConfig.MAIN_CREATE_LOTTERY;

@SuppressLint("CheckResult")
public class CreateListLotteryPresenter extends BasePresenter<CreateListLotteryContract.View> implements CreateListLotteryContract.Presenter {

    @Inject
    public CreateListLotteryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void startCreateLottery(LotteryType lotteryType, List<LotteryNumber> selectNumbers, int createSize, int createTypePosition) {
        Observable.create((ObservableOnSubscribe<List<List<LotteryNumber>>>) emitter -> {
            try {
                List<List<LotteryNumber>> lotteryData = new ArrayList<>();
                if (createTypePosition == 2 || createTypePosition == 3) {
                    boolean isUseSelectNumber = createTypePosition == 3;
                    LotteryHelper.getInstance().setSelectNumbers(selectNumbers, isUseSelectNumber);
                }
                for (int i = 0; i < createSize; i++) {
                    List<LotteryNumber> lotteryValue = new ArrayList<>();
                    lotteryData.add(lotteryValue);
                    LotteryHelper.getInstance().getRandomLottery(lotteryValue, lotteryType);
                }
                LotteryHelper.getInstance().setSelectNumbers(null, false);
                emitter.onNext(lotteryData);
            } catch (Exception e) {
                emitter.onNext(null);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lotteryData -> {
                    mView.dismissLoading();
                    if (lotteryData != null) {
                        mView.createLotteryFinish(lotteryData);
                    } else {
                        mView.showToast(MAIN_CREATE_LOTTERY);
                    }
                });
    }

    public void saveLotteryList(List<List<LotteryNumber>> data, LotteryType lotteryType) {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            try {
                for (List<LotteryNumber> lotteryValue : data) {
                    LotteryHelper.getInstance().saveLottery(lotteryValue, lotteryType);
                }
                emitter.onNext(TipConfig.APP_SAVE_SUCCESS);
            } catch (Exception e) {
                emitter.onNext("保存失败：" + e.getMessage());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    mView.dismissLoading();
                    mView.showToast(message);
                    if (TipConfig.APP_SAVE_SUCCESS.equals(message)) {
                        mView.saveLotterySuccess();
                    }
                });
    }
}
