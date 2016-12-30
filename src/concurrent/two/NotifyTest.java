package concurrent.two;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2016/12/21.
 */
public class NotifyTest {
    private static Object object;
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnbale2("test1"));
        Thread thread2 = new Thread(new MyRunnbale2("test2"));
        Thread thread3 = new Thread(new MyRunnbale2("test3"));
        thread1.start();
        thread2.start();
        thread3.start();
        TimeUnit.SECONDS.sleep(1);
        thread1.notifyAll();
    }
}

class MyRunnbale2 implements Runnable{
    private String name;
    public MyRunnbale2(String name){
        this.name = name;
    }
    @Override
    public void run() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name);
    }
}
