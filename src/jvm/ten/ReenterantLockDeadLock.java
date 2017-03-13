package jvm.ten;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhour on 2017/3/13.
 */
public class ReenterantLockDeadLock {
    private static ReentrantLock lock = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(()->{
            lock.lock();
            try {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lock();
                try {
                    System.out.println("thread1 finished");
                }finally {
                    lock2.unlock();
                }
            }finally {
                lock.unlock();
            }
        },"threadA").start();

        new Thread(()->{
            lock2.lock();
            try {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    System.out.println("thread2 finished");
                }finally {
                    lock.unlock();
                }
            }finally {
                lock2.unlock();
            }
        },"threadB").start();
    }
}
