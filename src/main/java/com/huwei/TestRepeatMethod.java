package com.huwei;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:32 2017/9/5
 * @Modified By
 */
public class TestRepeatMethod {

    public static void main(String[] args){
        test(null);
    }

    static void test(int arg){
        System.out.println("test1");
    }

    static void test(String arg){
        System.out.println("test2");
    }
}
