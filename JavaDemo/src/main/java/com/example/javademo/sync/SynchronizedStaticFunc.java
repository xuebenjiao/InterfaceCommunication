package com.example.javademo.sync;

/**
 * Created by xue on 2019/1/24.
 * synchronized 修改静态方法
 */

public class SynchronizedStaticFunc implements Runnable{
    private  volatile  static int mCount ;
    public SynchronizedStaticFunc(){
        this.mCount = 0;
    }
    public static synchronized void method(){
        for(int i=0;i<5;i++){
            try {
                mCount++;
                System.out.println("当前线程是："+Thread.currentThread().getName()+" ,mCount:"+mCount);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public  void run() {
        method();

    }
}
