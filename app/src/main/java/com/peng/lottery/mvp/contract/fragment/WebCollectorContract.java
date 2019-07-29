package com.peng.lottery.mvp.contract.fragment;

import com.peng.lottery.base.IView;
import com.peng.lottery.mvp.model.db.bean.WebUrl;

import java.util.List;

public interface WebCollectorContract {

    interface View extends IView {

        void onLoadFinish(List<WebUrl> urlList);
    }

    interface Presenter {

        void getWebUrlList();
    }
}
