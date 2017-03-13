package jvm.ten;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2017/3/13.
 */
public class SynchronizedDeadLock {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock1){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("thread1 finished");
                }
            }
        },"threadA").start();

        new Thread(()->{
            synchronized (lock2){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println("thread2 finished");
                }
            }
        },"threadB").start();
    }
}
