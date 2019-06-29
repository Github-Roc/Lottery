package com.peng.lottery.mvp.presenter.activity;

import com.peng.lottery.app.config.SharedPreferencesConfig;
import com.peng.lottery.app.utils.DateFormatUtil;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.bean.WebUrl;
import com.peng.lottery.mvp.model.db.bean.WebUrlDao;

import java.util.List;

import javax.inject.Inject;

public class WebPresenter extends BasePresenter {
    private WebUrlDao mWebUrlDao;

    @Inject
    public WebPresenter(DataManager dataManager) {
        super(dataManager);

        mWebUrlDao = dataManager.getDataBaseHelper().getWebUrlDao();
    }

    public String getHomeUrl() {
        return (String) mSpHelper.get(SharedPreferencesConfig.WEB_HOME_URL, "https://www.baidu.com");
    }

    public String collectionUrl(String icon, String title, String url) {
        if (checkUrlIsCollection(url)) {
            return "该地址以收藏！";
        }
        WebUrl webUrl = new WebUrl();
        webUrl.setCollectionIcon(icon);
        webUrl.setCollectionTitle(title);
        webUrl.setCollectionUrl(url);
        webUrl.setCollectionDate(DateFormatUtil.getSysDate());
        mWebUrlDao.insert(webUrl);
        return "收藏成功！";

    }

    private boolean checkUrlIsCollection(String url) {
        List<WebUrl> urlList = mWebUrlDao.queryBuilder().where(WebUrlDao.Properties.CollectionUrl.eq(url)).build().list();
        return urlList != null && urlList.size() > 0;
    }
}
