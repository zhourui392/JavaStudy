package concurrent.two;

/**
 * Created by zhour on 2016/12/21.
 */
public class JoinTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyRunnbale("test1"));
        Thread thread2 = new Thread(new MyRunnbale("test2"));
        Thread thread3 = new Thread(new MyRunnbale("test3"));
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyRunnbale implements Runnable{
    private String name;
    public MyRunnbale(String name){
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name);
    }
}
