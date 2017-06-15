package com.huwei.service;

/**
 * Created by huwei on 2017/6/13.
 */
public class BaseService {
    protected boolean repeat = false;

    public void repeat(){
        this.repeat = true;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
