package com.peng.lottery.mvp.model.event;

import android.support.annotation.DrawableRes;

public class ActionBean {

    public int actionId;
    public String actionName;
    public String actionUrl;
    @DrawableRes
    public int actionIcon;

    public ActionBean(String actionName, String actionUrl) {
        this.actionName = actionName;
        this.actionUrl = actionUrl;
    }

    public ActionBean(int actionId, String actionName, int actionIcon) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.actionIcon =  actionIcon;
    }

    public ActionBean(int actionId, String actionName, int actionIcon, String actionUrl) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.actionIcon =  actionIcon;
        this.actionUrl = actionUrl;
    }
}
