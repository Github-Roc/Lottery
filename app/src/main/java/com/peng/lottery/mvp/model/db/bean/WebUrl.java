package com.peng.lottery.mvp.model.db.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WebUrl {

    @Id(autoincrement = true)
    private Long Id;

    // 网址Url
    private String collectionUrl;
    // 网址Icon
    private String collectionIcon;
    // 网址Title
    private String collectionTitle;
    // 收藏时间
    private String collectionDate;
    @Generated(hash = 1770021366)
    public WebUrl(Long Id, String collectionUrl, String collectionIcon,
            String collectionTitle, String collectionDate) {
        this.Id = Id;
        this.collectionUrl = collectionUrl;
        this.collectionIcon = collectionIcon;
        this.collectionTitle = collectionTitle;
        this.collectionDate = collectionDate;
    }
    @Generated(hash = 1285492167)
    public WebUrl() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getCollectionUrl() {
        return this.collectionUrl;
    }
    public void setCollectionUrl(String collectionUrl) {
        this.collectionUrl = collectionUrl;
    }
    public String getCollectionIcon() {
        return this.collectionIcon;
    }
    public void setCollectionIcon(String collectionIcon) {
        this.collectionIcon = collectionIcon;
    }
    public String getCollectionTitle() {
        return this.collectionTitle;
    }
    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }
    public String getCollectionDate() {
        return this.collectionDate;
    }
    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }
  
}
