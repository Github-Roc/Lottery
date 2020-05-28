package com.peng.lottery.mvp.model.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.mvp.model.web.bean.BaseBean;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_DLT;

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
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("app_id", "kmwjhjpxi0ftiwln")
                                .addHeader("app_secret", "bmNjR1hnTnJ0LzVLVGJyTjgzV0ZLdz09")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        mLotteryApi = new Retrofit.Builder()
                .baseUrl(AppConfig.LOTTERY_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build()
                .create(LotteryApi.class);
    }

    public Observable<BaseBean<LotteryBean>> getLastLottery(LotteryType lotteryType) {
        String typeCode = lotteryType == LOTTERY_TYPE_DLT ? "cjdlt" : "ssq";
        return mLotteryApi.getLastLottery(typeCode);
    }

    public Observable<BaseBean<List<LotteryBean>>> getLotteryRecord(LotteryType lotteryType) {
        String typeCode = lotteryType == LOTTERY_TYPE_DLT ? "cjdlt" : "ssq";
        return mLotteryApi.getLotteryRecord(typeCode, "50");
    }

}
