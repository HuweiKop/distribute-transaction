package com.huwei;

/**
 * Created by huwei on 2017/6/14.
 */
public class TestClass {
    public static void main(String[] args){
        double d = 0.123482;
//        int i = (int)(d*100);
        System.out.println((double)Math.round(d*10000)/100);
    }
}
