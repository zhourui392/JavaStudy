package concurrent.five;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhourui on 2017/1/14.
 * 1.比较 ConcurrentLinkedQueue 和 BlockingQueue的性能，并说明为什么。给出你的测试代码，和运行结果的截图。
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        //生产
        final long start = System.currentTimeMillis();
        for (int i = 0; i < 10;i ++){
            new Thread(()->{
                    for (int g = 0; g < 100000; g++){
                        concurrentLinkedQueue.add(Long.toString(System.currentTimeMillis()));
                    }
                System.out.println(Thread.currentThread().getName()
                        +"_concurrentLinkedQueue finished put:"
                        +(System.currentTimeMillis() - start)
                        +"ms");
                latch.countDown();
            },"Thread"+i).start();
        }
        latch.await();
        //消费
        final long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10;i ++){
            new Thread(()->{
                for (int g = 0; g < 100000; g++){
                    concurrentLinkedQueue.poll();
                }
                System.out.println(Thread.currentThread().getName()
                        +"_concurrentLinkedQueue finished poll:"
                        +(System.currentTimeMillis() - start2)
                        +"ms");
            },"Thread"+i).start();
        }
    }
}
