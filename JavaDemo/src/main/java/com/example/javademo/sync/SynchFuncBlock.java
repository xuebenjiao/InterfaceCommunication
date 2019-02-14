package com.example.javademo.sync;

/**
 * Created by xue on 2019/1/24.
 */

public class SynchFuncBlock implements  Runnable{
    private  volatile  static int mCount ;
    public SynchFuncBlock(){
        this.mCount = 0;
    }
    @Override
    public void run() {
        synchronized (this){
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
    }
}
