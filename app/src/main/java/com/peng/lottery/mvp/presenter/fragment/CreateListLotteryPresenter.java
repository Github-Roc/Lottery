package com.peng.lottery.mvp.presenter.fragment;

import android.annotation.SuppressLint;

import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.utils.LotteryUtil;
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

public class CreateListLotteryPresenter extends BasePresenter<CreateListLotteryContract.View> implements CreateListLotteryContract.Presenter {

    @Inject
    public CreateListLotteryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @SuppressLint("CheckResult")
    public void startCreateLottery(ActionConfig.LotteryType lotteryType, String createType, int createSize) {
        Observable.create((ObservableOnSubscribe<List<List<LotteryNumber>>>) emitter -> {
            try {
                List<List<LotteryNumber>> lotteryData = new ArrayList<>();
                for (int i = 0; i < createSize; i++) {
                    List<LotteryNumber> lotteryValue = new ArrayList<>();
                    lotteryData.add(lotteryValue);
                    LotteryUtil.getInstance().getRandomLottery(lotteryValue, lotteryType);
                }
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
}
