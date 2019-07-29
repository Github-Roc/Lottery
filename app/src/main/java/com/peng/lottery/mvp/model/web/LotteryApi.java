package com.peng.lottery.mvp.model.web;

import com.peng.lottery.mvp.model.web.bean.BaseBean;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LotteryApi {

    @GET("lottery/ssq/latest")
    Observable<BaseBean<LotteryBean>> getLastLottery();
}
