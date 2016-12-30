package concurrent.threadPool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2016/12/27.
 */
public class ThreadPoolTest {
    public static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static void main(String[] args) {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(new Date());
            }
        },0,10, TimeUnit.SECONDS);
    }
}
