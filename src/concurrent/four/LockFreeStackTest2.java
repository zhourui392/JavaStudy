package concurrent.four;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2017/1/8.
 */
public class LockFreeStackTest2 {
    public static void main(String[] args) throws InterruptedException {
        final LockFreeStack<String> mystack = new LockFreeStack();
        for (int i = 0; i < 5; i++){
            new Thread(()->{
                for (int j=0; j < 10; j++){
                    mystack.push(UUID.randomUUID().toString());
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(5);
        //the result is 50
        System.out.println("after push, size is "+mystack.size());

        for (int i = 0; i < 5; i++){
            new Thread(()->{
                for (int j=0; j < 10; j++){
                    mystack.pop();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(5);
        //the result is 50
        System.out.println("after pop, size is "+mystack.size());
    }
}
