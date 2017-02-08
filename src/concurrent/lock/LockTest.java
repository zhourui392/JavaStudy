package concurrent.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2017/1/20.
 */
public class LockTest {
    static Object lock = new Object();
    static Object bbb = lock;
    public static void main(String[] args) throws InterruptedException {

        System.out.println(lock.toString());
        System.out.println(bbb.toString());
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock = "222";
            System.out.println(lock);
        }).start();
        synchronized (lock){
            try {
                System.out.println(lock);
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("synchronized end in main.");

        TimeUnit.SECONDS.sleep(2);
    }
}
