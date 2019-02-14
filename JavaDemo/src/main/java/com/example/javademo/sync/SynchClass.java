package com.example.javademo.sync;

/**
 * Created by xue on 2019/1/24.
 */

public class SynchClass implements Runnable {
    private   static int mCount ;
    public SynchClass(){
        this.mCount = 0;
    }
    public static  void method(){
        synchronized (SynchClass.class) {
            for (int i = 0; i < 5; i++) {
                try {
                    mCount++;
                    System.out.println("当前线程是：" + Thread.currentThread().getName() + " ,mCount:" + mCount);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public  void run() {
        method();

    }
}
