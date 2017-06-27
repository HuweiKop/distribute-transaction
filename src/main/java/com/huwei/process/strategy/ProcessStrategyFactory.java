package com.huwei.process.strategy;

import com.huwei.constant.ProcesStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huwei on 2017/6/27.
 */
public class ProcessStrategyFactory {

    private volatile static Map<Integer, BaseProcessStrategy> container = null;

    public static BaseProcessStrategy getProcessStrategy(Integer key) {
        if (container == null) {
            init();
        }
        return container.get(key);
    }

    private static synchronized void init() {
        if (container == null) {
            container = new HashMap<>();
            container.put(ProcesStrategy.ReTry, new RetryProcessStrategy());
            container.put(ProcesStrategy.Rollback, new RollbackProcessStrategy());
        }
    }

    public static void register(int key, BaseProcessStrategy strategy) {
        if (container == null) {
            init();
        }
        container.put(key, strategy);
    }
}
