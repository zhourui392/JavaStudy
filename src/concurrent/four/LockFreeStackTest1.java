package concurrent.four;
/**
 * Created by zhour on 2017/1/5.
 * 实现一个无锁的Stack，并写一段测试代码（多线程访问），证明这个Stack是线程安全的。给出程序以及运行的截图。
 */
public class LockFreeStackTest1 {

    public static void main(String[] args) {
        final LockFreeStack<Long> mystack = new LockFreeStack();
        mystack.push(1L);
        mystack.push(2L);
        mystack.push(3L);
        mystack.push(4L);
        mystack.push(5L);
        System.out.println("peek:"+mystack.peek());
        System.out.println("pop:"+mystack.pop());
        System.out.println("pop:"+mystack.pop());
        System.out.println("pop:"+mystack.pop());
        System.out.println("pop:"+mystack.pop());
        System.out.println("pop:"+mystack.pop());
        System.out.println("pop:"+mystack.pop());
    }
}
