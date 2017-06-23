package com.huwei.util;

import com.huwei.annotation.RollbackService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huwei on 2017/6/23.
 */
public class BeanScanner {

    private List<String> classNames = new ArrayList<>();

    private Map<String, Method> rollbackMethodMap = new HashMap<>();

    private BeanScanner() {
        scanPackage("com.huwei.api");
        try {
            filterAndInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BeanScanner getInstance() {
        return InnerClass.instance;
    }

    static class InnerClass {
        private static BeanScanner instance = new BeanScanner();
    }

    private void scanPackage(String basePackage) {
        String name = "/" + basePackage.replaceAll("\\.", "/");
        URL url = BeanScanner.class.getResource(name);
        String fileStr = url.getFile();
        File file = new File(fileStr);
        String[] files = file.list();
        for (String path : files) {
            File filePath = new File(fileStr + "\\" + path);
            if (filePath.isDirectory()) {
                scanPackage(basePackage + "." + path);
            } else {
                classNames.add(basePackage + "." + filePath.getName());
                System.out.println("className add:" + basePackage + "." + filePath.getName());
            }
        }
    }

    private void filterAndInstance() throws Exception {
        if (classNames.size() <= 0) {
            return;
        }
        for (String className : classNames) {
            Class cNameClass = Class.forName(className.replace(".class", ""));
            if (cNameClass.isAnnotationPresent(Component.class)) {
                Method[] methods = cNameClass.getMethods();
                for (Method m : methods) {
                    if (m.isAnnotationPresent(RollbackService.class)) {
                        RollbackService rollbackService = m.getAnnotation(RollbackService.class);
                        rollbackMethodMap.put(cNameClass.getName() + "::" + rollbackService.value(), m);
                    }
                }
            }
        }
    }

    public Method getMethod(String key) {
        return rollbackMethodMap.get(key);
    }
}
