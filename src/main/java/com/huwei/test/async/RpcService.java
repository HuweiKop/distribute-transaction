package com.huwei.test.async;

/**
 * Created by huwei on 2017/6/27.
 */
public class RpcService {

    public int getRpcResult1(){

        long start = System.currentTimeMillis();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("result 1 =================="+(end - start));
        return 1;
    }

    public int getRpcResult2(){

        long start = System.currentTimeMillis();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("result 2 =================="+(end - start));
        return 2;
    }
}
