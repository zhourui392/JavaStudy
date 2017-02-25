package jvm.nine;

import org.nutz.lang.Times;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhour on 2017/2/25.
 */
public class HomeWork {
    public static final int M = 1000000;

    public static int threadCount = 1000 ;

    public static AtomicBoolean lockIsFinish = new AtomicBoolean(false);
    public static AtomicBoolean noLockIsFinish = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        List<Thread> threads1 = new ArrayList<>(threadCount);
        long start = Times.now().getTime();
        //1、nolock
        for (int i = 0; i < threadCount; i++){
            threads1.add(new Thread(new NoLock(atomicInteger,M,start,i)));
        }
        for (int i = 0; i < threadCount; i++){
            threads1.get(i).start();
        }
        TimeUnit.SECONDS.sleep(10);

        List<Thread> threads2 = new ArrayList<>(threadCount);
        long start2 = Times.now().getTime();
        Integer NoLockIndex = new Integer(0);
        //2、lock
        for (int i = 0; i < threadCount; i++){
            threads2.add(new Thread(new WithLock(NoLockIndex,M,start2,i)));
        }
        for (int i = 0; i < threadCount; i++){
            threads2.get(i).start();
        }
        TimeUnit.SECONDS.sleep(10);
    }
}
