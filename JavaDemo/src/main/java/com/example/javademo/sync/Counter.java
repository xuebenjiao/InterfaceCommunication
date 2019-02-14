package com.example.javademo.sync;

/**
 * Created by xue on 2019/1/24.
 * 当一个线程访问对象的一个synchronized(this)同步代码块时，
 * 另一个线程仍然可以访问该对象中的非synchronized(this)同步代码块。
 */

public class Counter implements Runnable{
    private static int mCount ;
    public Counter(){
        this.mCount = 0;
    }
    public void addCount (){
        synchronized (this){
            for(int i=0;i<5;i++){
                try{
                    mCount++;
                    System.out.println(Thread.currentThread().getName()+",addCount:"+mCount);
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    //非synchronized代码块，未对count进行读写操作，所以可以不用synchronized
    public void printCount() {
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + " print:" + mCount);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        if (threadName.equals("A")) {
            addCount();
        } else if (threadName.equals("B")) {
            printCount();
        }
    }
}
