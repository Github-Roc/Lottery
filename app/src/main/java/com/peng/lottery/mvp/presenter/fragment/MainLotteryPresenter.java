package com.peng.lottery.mvp.presenter.fragment;

import android.annotation.SuppressLint;

import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.app.helper.LotteryHelper;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.contract.fragment.MainLotteryContract;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.model.web.LotteryObserver;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.peng.lottery.app.config.TipConfig.MAIN_CREATE_LOTTERY;


public class MainLotteryPresenter extends BasePresenter<MainLotteryContract.View> implements MainLotteryContract.Presenter {

    @Inject
    public MainLotteryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getLotteryRecord(List<LotteryNumber> lotteryValue, LotteryType lotteryType) {
        beforeExecute(mRetrofitHelper.getLotteryRecord(lotteryType))
                .subscribe(new LotteryObserver<List<LotteryBean>>() {
                    @Override
                    public void onError(String errorMsg) {
                        mView.dismissLoading();
                        mView.showToast(errorMsg);
                    }

                    @Override
                    public void onSuccess(List<LotteryBean> data) {
                        createLotteryValue(lotteryValue, lotteryType, data);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void createLotteryValue(List<LotteryNumber> lotteryValue, LotteryType lotteryType, List<LotteryBean> data) {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            try {
                LotteryHelper.getInstance().getAILottery(lotteryValue, lotteryType, data);
                emitter.onNext(200);
            } catch (Exception e) {
                emitter.onNext(404);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if (code == 200) {
                        mView.createLotteryFinish();
                    } else {
                        mView.dismissLoading();
                        mView.showToast(MAIN_CREATE_LOTTERY);
                    }
                });
    }
}
