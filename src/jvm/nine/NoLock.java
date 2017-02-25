package jvm.nine;

import org.nutz.lang.Times;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhour on 2017/2/25.
 */
public class NoLock implements Runnable {
    private AtomicInteger index;
    private final int m;
    private long start;
    private int name;
    public NoLock(AtomicInteger index,int m,long start,int name){
        this.index = index;
        this.m = m;
        this.start = start;
        this.name = name;
    }
    @Override
    public void run() {
        while (index.get() < m){
            index.getAndIncrement();
        }
        if (HomeWork.noLockIsFinish.compareAndSet(false,true)){
            System.out.println("NoLock["+name+"] cost is " + (Times.now().getTime() - start)+"ms");
        }
    }
}
