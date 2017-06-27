package com.huwei.process.strategy;

import com.huwei.model.MessageModel;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by huwei on 2017/6/27.
 */
public abstract class BaseProcessStrategy {

    public abstract void processing(MessageModel messageModel) throws InvocationTargetException, IllegalAccessException;
}
