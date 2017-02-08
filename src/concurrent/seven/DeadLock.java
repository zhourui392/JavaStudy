package concurrent.seven;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2017/2/8.
 */
public class DeadLock {
    private static Object A = new Object();
    private static Object B = new Object();
    private static Object C = new Object();
    private static Object D = new Object();

    public static void main(String[] args) {
        Thread aThread = new Thread(()->{
            synchronized (D){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A){
                    A = new Object();
                }
            }
        });

        Thread bThread = new Thread(()->{
            synchronized (A){
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    B = new Object();
                }
            }
        });

        Thread cThread = new Thread(()->{
            synchronized (B){
                try {
                    TimeUnit.MILLISECONDS.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (C){
                    C = new Object();
                }
            }
        });

        Thread dThread = new Thread(()->{
            synchronized (C){
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (D){
                    D = new Object();
                }
            }
        });

        aThread.start();
        bThread.start();
        cThread.start();
        dThread.start();
    }
}
