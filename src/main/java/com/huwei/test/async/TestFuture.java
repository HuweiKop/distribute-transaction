package com.huwei.test.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by huwei on 2017/6/27.
 */
public class TestFuture {

    final static ExecutorService executor = Executors.newFixedThreadPool(2);

    public  static  void main(String[] args){
        RpcService rpc1 = new RpcService();
        RpcService rpc2 = new RpcService();

        Future<Integer> f1 = null;
        Future<Integer> f2 = null;

        try{
            long start = System.currentTimeMillis();
            f1 = executor.submit(()->rpc1.getRpcResult1());
            f2 = executor.submit(()->rpc2.getRpcResult2());

            int r1 = f1.get();
            int r2 = f2.get();

            System.out.println(r1+r2);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
