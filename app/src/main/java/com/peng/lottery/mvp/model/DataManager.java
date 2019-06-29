package com.peng.lottery.mvp.model;


import com.peng.lottery.mvp.model.db.DataBaseHelper;
import com.peng.lottery.mvp.model.web.RetrofitHelper;
import com.peng.lottery.mvp.model.sp.SharedPreferencesHelper;

public class DataManager {
    private static DataManager mInstance;

    private RetrofitHelper mRetrofitHelper;
    private DataBaseHelper mDataBaseHelper;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    public RetrofitHelper getRetrofitHelper() {
        if (mRetrofitHelper == null) {
            mRetrofitHelper = new RetrofitHelper();
        }
        return mRetrofitHelper;
    }

    public DataBaseHelper getDataBaseHelper() {
        if (mDataBaseHelper == null) {
            mDataBaseHelper = new DataBaseHelper();
        }
        return mDataBaseHelper;
    }

    public SharedPreferencesHelper getSharedPreferencesHelper() {
        if (mSharedPreferencesHelper == null) {
            mSharedPreferencesHelper = new SharedPreferencesHelper();
        }
        return mSharedPreferencesHelper;
    }

}
