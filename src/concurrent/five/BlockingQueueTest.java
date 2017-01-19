package concurrent.five;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhour on 2017/1/14.
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        final long start = System.currentTimeMillis();
        for (int i = 0; i < 10;i ++){
            new Thread(()->{
                try {
                    for (int g = 0; g < 100000; g++){
                        blockingQueue.put(Long.toString(System.currentTimeMillis()));
                    }
                    System.out.println(Thread.currentThread().getName()
                            +"_blockingQueue finished put:"
                            +(System.currentTimeMillis() - start)
                            +"ms");
                    latch.countDown();
                } catch (InterruptedException e) {e.printStackTrace();}
            },"Thread"+i).start();
        }
        latch.await();
        //消费
        final long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10;i ++){
            new Thread(()->{
                try {
                    for (int g = 0; g < 100000; g++){
                        blockingQueue.take();
                    }
                    System.out.println(Thread.currentThread().getName()
                            +"_concurrentLinkedQueue finished take:"
                            +(System.currentTimeMillis() - start2)
                            +"ms");
                } catch (InterruptedException e) {e.printStackTrace();}
            },"Thread"+i).start();
        }
    }
}
