package com.peng.lottery.mvp.model.event;


public class EventBean<D> {

    public int eventCode;
    public D data;

    public EventBean(int eventCode){
        this.eventCode = eventCode;
    }

    public EventBean(int eventCode, D data){
        this.eventCode = eventCode;
        this.data = data;
    }
}
