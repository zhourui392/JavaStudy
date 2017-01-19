package concurrent.threadPool;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

/**
 * Created by zhourui on 2017/1/19.
 */
public class ExceptionInThreadPool {
    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        executorService.submit(new MyRunnable("123",Thread.currentThread().getStackTrace()));
    }
}
