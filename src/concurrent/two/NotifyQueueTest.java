package concurrent.two;

/**
 * Created by zhour on 2016/12/21.
 */
public class NotifyQueueTest {
    public static void main(String[] args) {
        NotifyQueue notifyQueue = new NotifyQueue();
        for (int i = 0; i<5; i++){
            Thread thread = new Thread(new Consumer(notifyQueue,"Consumer"+i));
            thread.start();
        }
        for (int i = 0; i<5; i++){
            Thread thread = new Thread(new Producer(notifyQueue,"Producer"+i));
            thread.start();
        }
    }
}

class Consumer implements Runnable{
    private NotifyQueue notifyQueue;
    private String name;
    public Consumer(NotifyQueue notifyQueue,String name){
        this.notifyQueue = notifyQueue;
        this.name = name;
    }
    @Override
    public void run() {
        while (true){
            try {
                notifyQueue.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer implements Runnable{
    private NotifyQueue notifyQueue;
    private String name;
    public Producer(NotifyQueue notifyQueue,String name){
        this.notifyQueue = notifyQueue;
        this.name = name;
    }
    @Override
    public void run() {
        while (true){
            try {
                notifyQueue.put(name + "'s produce");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
