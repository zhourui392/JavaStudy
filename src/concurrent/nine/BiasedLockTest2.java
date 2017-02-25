package concurrent.nine;

import org.nutz.lang.Times;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 竞争激烈时
 */
public class BiasedLockTest2 {
    public static Long count = new Long(0);
    public static AtomicInteger index = new AtomicInteger(0);
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        long begin = Times.now().getTime();

        for (int i = 0; i < 20; i++){
            executorService.submit(() -> {
                for(int j = 0; j < 10000000; j++){
                    synchronized (count){
                        count++;
                    }
                }
                System.out.println("loop:"+index.incrementAndGet()+",time is "+(Times.now().getTime() - begin)+"ms");
            });
        }
    }
}
