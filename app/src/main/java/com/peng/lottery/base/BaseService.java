package com.peng.lottery.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * @author Peng
 * Created by tofirst on 2018/4/9.
 * Service的基类
 */
public class BaseService extends Service {
    protected Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mContext = null;
    }
}
