package com.example.javademo.sync;

/**
 * Created by xue on 2019/1/24.
 * 总结：
 A. 无论synchronized关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；如果synchronized作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁。
 B. 每个对象只有一个锁（lock）与之相关联，谁拿到这个锁谁就可以运行它所控制的那段代码。
 C. 实现同步是要很大的系统开销作为代价的，甚至可能造成死锁，所以尽量避免无谓的同步控制。
 */

public class SynchronizedDemo {
    public static void main(String[] args) {

        /*当两个并发线程(thread1和thread2)访问同一个对象(syncThread)中的synchronized代码块时，
        在同一时刻只能有一个线程得到执行，另一个线程受阻塞，必须等待当前线程执行完这个代码块以
        后才能执行该代码块。Thread1和thread2是互斥的，因为在执行synchronized代码块时会锁定当前
        的对象，只有执行完该代码块才能释放该对象锁，下一个线程才能执行并锁定该对象。*/
        SynchFuncBlock synchFuncBlock = new SynchFuncBlock();
        Thread thread = new Thread(synchFuncBlock, "Thread1");
        Thread thread1 = new Thread(synchFuncBlock, "thread2");
     /*   thread.start();
        thread1.start();*/
/*这时创建了两个SyncThread的对象syncThread1和syncThread2，线程thread1执行的是syncThread1对象
中的synchronized代码(run)，而线程thread2执行的是syncThread2对象中的synchronized代码(run)；我
们知道synchronized锁定的是对象，这时会有两把锁分别锁定syncThread1对象和syncThread2对象，而这
两把锁是互不干扰的，不形成互斥，所以两个线程可以同时执行。*/
        Thread thread3 = new Thread(new SynchFuncBlock(), "Thread3");
        Thread thread4 = new Thread(new SynchFuncBlock(), "thread4");
    /*    thread3.start();
        thread4.start();*/

        /*当一个线程访问对象的一个synchronized(this)同步代码块时，
        * 另一个线程仍然可以访问该对象中的非synchronized(this)同步代码块。*/
        Counter counter = new Counter();
        Thread thread5 = new Thread(counter, "A");
        Thread thread6 = new Thread(counter, "B");
       /* thread5.start();
        thread6.start();*/


        /*在AccountOperator 类中的run方法里，我们用synchronized 给account对象加了锁。这时，
        当一个线程访问account对象时，其他试图访问account对象的线程将会阻塞，直到该线程访问
        account对象结束。也就是说谁拿到那个锁谁就可以运行它所控制的那段代码。*/

        Account account = new Account("zhang san", 10000.0f);
        AccountOperator accountOperator = new AccountOperator(account);

        final int THREAD_NUM = 5;
        Thread threads[] = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
//            threads[i].start();
        }


        /*我们知道静态方法是属于类的而不属于对象的。同样的，synchronized修饰的静态方法锁定的是这个类的所有对象*/
        SynchronizedStaticFunc staticFunc = new SynchronizedStaticFunc();
        SynchronizedStaticFunc staticFunc1 = new SynchronizedStaticFunc();
        Thread thread7 = new Thread(staticFunc, "thread7");
        Thread thread8 = new Thread(staticFunc1, "thread8");
      /*  thread7.start();
        thread8.start();*/

        /*synchronized作用于一个类T时，是给这个类T加锁，T的所有对象用的是同一把锁。*/
        SynchClass staticClass= new SynchClass();
        SynchClass staticClass1 = new SynchClass();
        Thread thread9 = new Thread(staticClass, "thread9");
        Thread thread10 = new Thread(staticClass1, "thread10");
        thread9.start();
        thread10.start();
    }
}
