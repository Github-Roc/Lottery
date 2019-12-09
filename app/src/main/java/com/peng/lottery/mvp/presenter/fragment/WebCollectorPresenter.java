package com.peng.lottery.mvp.presenter.fragment;

import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.contract.fragment.WebCollectorContract;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.WebUrl;
import com.peng.lottery.mvp.model.db.bean.WebUrlDao;

import java.util.List;

import javax.inject.Inject;

import static com.peng.lottery.mvp.presenter.activity.WebPresenter.WEB_HOME_URL;

public class WebCollectorPresenter extends BasePresenter<WebCollectorContract.View> implements WebCollectorContract.Presenter {

    private WebUrlDao mWebUrlDao;

    @Inject
    public WebCollectorPresenter(DataManager dataManager) {
        super(dataManager);

        mWebUrlDao = dataManager.getDataBaseHelper().getWebUrlDao();
    }

    @Override
    public void getWebUrlList() {
        List<WebUrl> urlList = mWebUrlDao.loadAll();
        mView.onLoadFinish(urlList);
    }

    public void deleteWebUrl(WebUrl webUrl) {
        mWebUrlDao.delete(webUrl);
    }

    public void setWebHome(String url) {
        mSpHelper.put(WEB_HOME_URL, url);
    }
}
