package com.peng.lottery.mvp.model.db;

import com.peng.lottery.app.MyApplication;
import com.peng.lottery.mvp.model.db.bean.DaoMaster;
import com.peng.lottery.mvp.model.db.bean.DaoSession;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;
import com.peng.lottery.mvp.model.db.bean.LotteryNumberDao;
import com.peng.lottery.mvp.model.db.bean.WebUrlDao;

import org.greenrobot.greendao.database.Database;

public class DataBaseHelper {

    private DaoSession mDaoSession;

    public DataBaseHelper() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.mContent, "lottery.db", null);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public LotteryDataDao getLotteryDataDao() {
        return mDaoSession.getLotteryDataDao();
    }

    public LotteryNumberDao getLotteryNumberDao() {
        return mDaoSession.getLotteryNumberDao();
    }

    public WebUrlDao getWebUrlDao() {
        return mDaoSession.getWebUrlDao();
    }
}

