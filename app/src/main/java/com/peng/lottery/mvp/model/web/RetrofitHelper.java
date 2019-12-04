package com.peng.lottery.mvp.model.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.mvp.model.web.bean.BaseBean;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;

public class RetrofitHelper {
    private LotteryApi mLotteryApi;

    public RetrofitHelper() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        mLotteryApi = new Retrofit.Builder()
                .baseUrl(AppConfig.LOTTERY_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build()
                .create(LotteryApi.class);
    }

    public Observable<BaseBean<LotteryBean>> getLastLottery(ActionConfig.LotteryType lotteryType) {
        String typeCode = lotteryType == LOTTERY_TYPE_DLT ? "cjdlt" : "ssq";
        return mLotteryApi.getLastLottery(typeCode);
    }

    public Observable<BaseBean<List<LotteryBean>>> getLotteryRecord(ActionConfig.LotteryType lotteryType) {
        String typeCode = lotteryType == LOTTERY_TYPE_DLT ? "cjdlt" : "ssq";
        return mLotteryApi.getLotteryRecord(typeCode, "50");
    }

}
