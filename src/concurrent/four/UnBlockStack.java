package concurrent.four;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhour on 2017/1/5.
 */
public class UnBlockStack {
    private AtomicInteger index = new AtomicInteger(0);
    private volatile int nowIndex;
    private List<String> list = new LinkedList<>();

    public String pop(){
        if (list.size() <=  0){
            return null;
        }
        for (;;){
            int nowIndex = index.get();
            if (index.compareAndSet(nowIndex,nowIndex-1)){
                String result = list.remove(nowIndex);
                index.decrementAndGet();
                return result;
            }
        }
    }

    public void push(String value){
        for (;;){
            int nowIndex = index.get();
            if (index.compareAndSet(nowIndex,nowIndex+1)){
                list.add(value);
                index.incrementAndGet();
                return;
            }
        }
    }

    public static void main(String[] args) {
        UnBlockStack unBlockStack = new UnBlockStack();

        for (int i = 0; i < 2; i++){
            new Thread(()->{
                while (true){
                    unBlockStack.push(UUID.randomUUID().toString());
                }
            }).start();
        }

        for (int i = 0; i < 2; i++){
            new Thread(()->{
                while (true){
                    String test = unBlockStack.pop();
                    System.out.println(test);
                }
            }).start();
        }


    }
}
