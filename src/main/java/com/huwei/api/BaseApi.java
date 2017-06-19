package com.huwei.api;

/**
 * Created by huwei on 2017/6/16.
 */
public class BaseApi {
    protected boolean repeat = false;

    public void repeat(){
        this.repeat = true;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
