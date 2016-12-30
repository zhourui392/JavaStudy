package concurrent.two;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhour on 2016/12/21.
 */
public class NotifyQueue {
    private List<String> queue = new LinkedList<>();
    public String get() throws InterruptedException {
        String value;
        synchronized (queue){
            while (queue.isEmpty()){
                System.out.println("queue get wait.queue length is " + queue.size());
                queue.wait();
                System.out.println("after get wait");
            }
            value = queue.remove(0);
            queue.notifyAll();
        }
        System.out.println("queue get value.queue length is " + queue.size());
        return value;
    }

    public void put(String value) throws InterruptedException {
        synchronized (queue){
            while (queue.size() == 10){
                System.out.println("queue put wait.queue length is " + queue.size());
                queue.wait();
                System.out.println("after put wait");
            }
            queue.add(value);
            queue.notifyAll();
        }
        System.out.println("queue add value.queue length is " + queue.size());
    }
}
