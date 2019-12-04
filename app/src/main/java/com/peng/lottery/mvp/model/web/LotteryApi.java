package com.peng.lottery.mvp.model.web;

import com.peng.lottery.mvp.model.web.bean.BaseBean;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LotteryApi {

    @GET("lottery/common/latest")
    Observable<BaseBean<LotteryBean>> getLastLottery(@Query("code") String code);

    @GET("lottery/common/history")
    Observable<BaseBean<List<LotteryBean>>> getLotteryRecord(@Query("code") String code, @Query("size") String size);

}
