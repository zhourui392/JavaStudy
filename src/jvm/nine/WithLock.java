package jvm.nine;

import org.nutz.lang.Times;

/**
 * Created by zhour on 2017/2/25.
 */
public class WithLock implements Runnable{
    private Integer index;
    private final int m;
    private final long start;
    private int name;
    public WithLock(Integer index, int m, long start,int name){
        this.index = index;
        this.m = m;
        this.start = start;
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println("WithLock["+name+"] start");
        while (index < m){
            synchronized (index){
                index++;
            }
        }
        if (HomeWork.lockIsFinish.compareAndSet(false,true)){
            System.out.println("WithLock["+name+"] cost is " + (Times.now().getTime() - start)+"ms");
        }
    }
}
