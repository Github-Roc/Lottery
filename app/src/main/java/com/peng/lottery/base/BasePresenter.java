package com.peng.lottery.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.peng.lottery.app.MyApplication;
import com.peng.lottery.app.utils.NetworkUtil;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.DataBaseHelper;
import com.peng.lottery.mvp.model.sp.SharedPreferencesHelper;
import com.peng.lottery.mvp.model.web.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * Presenter的基类
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected V mView;
    protected RetrofitHelper mRetrofitHelper;
    protected DataBaseHelper mSQLiteHelper;
    protected SharedPreferencesHelper mSpHelper;
    private CompositeDisposable mDisposables;
    private Gson gson;

    public BasePresenter(DataManager dataManager) {
        this.mRetrofitHelper = dataManager.getRetrofitHelper();
        this.mSQLiteHelper = dataManager.getDataBaseHelper();
        this.mSpHelper = dataManager.getSharedPreferencesHelper();
    }

    @Override
    public void bindView(V view) {
        this.mView = view;
    }

    @Override
    public void unbindView() {
        clearDisposables();
        mDisposables = null;
        mView = null;
    }

    protected <D> Observable<D> beforeExecute(Observable<D> observable) {
        return observable.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))
                .doOnSubscribe(this::addDisposable)
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected String toJson(Object obj) {
        if (gson == null) {
            gson = new GsonBuilder().setLenient().create();
        }
        return gson.toJson(obj);
    }

    private void addDisposable(Disposable disposable) {
        if (!NetworkUtil.isNetworkAvailable(MyApplication.mContent)) {
            mView.showToast("网络不可用，请检查网络连接。");
            disposable.dispose();
            return;
        }
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    private void clearDisposables() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }
}
