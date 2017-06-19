package com.huwei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei on 2017/6/14.
 */
public class TestClass {

    private static List<String> list = null;

    private static List<String> getList(int i){
        if(list==null){
            synchronized (TestClass.class){
                if(list==null){
                    list = get(i);
                }
            }
        }
        return list;
    }

    private static List<String> get(int i){
        List<String> list = new ArrayList<>();
        list.add("xxx");
        if(i==0) {
            throw new RuntimeException();
        }
        return list;
    }

    public static void main(String[] args){
//        double d = 0.123482;
//        System.out.println((double)Math.round(d*10000)/100);
        System.out.println(list);
        try {
            System.out.println(getList(0));
        }catch (RuntimeException ex){
            System.out.println("ex............");
        }
        System.out.println(getList(1));
    }
}
