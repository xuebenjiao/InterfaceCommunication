package com.example.library;

import java.lang.reflect.Method;

/**
 * Created by xue on 2019/1/23.
 */

public class MethodManager {
    /*订阅者的回调方法（注解方法）的线程模式*/
     private ThreadMode threadMode;
     /*订阅者方法*/
     private Method method;
     /*订阅者方法回调（注解）参数类型*/
     private Class<?> type;

    public MethodManager(Class<?> type,ThreadMode threadMode, Method method) {
        this.threadMode = threadMode;
        this.method = method;
        this.type = type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
