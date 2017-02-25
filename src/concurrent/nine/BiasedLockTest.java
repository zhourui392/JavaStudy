package concurrent.nine;

import org.nutz.lang.Times;

/**
 * Created by zhour on 2017/2/25.
 * 写一段简单的代码，测试使用偏向锁和禁用偏向锁的性能差异。
 * 给出你的jdk版本，程序运行时jvm参数，以及输出结果和结论
 * 禁用偏向锁：-XX:-UseBiasedLocking
 *
 *
 * 竞争不激烈时。
 */
public class BiasedLockTest {
    public static Integer count = new Integer(0);
    public static void main(String[] args) {
        long begin = Times.now().getTime();
        for(int i = 0; i < 1000000000; i++){
            synchronized (count){
                count++;
            }
        }

        System.out.println("time is "+(Times.now().getTime() - begin)+"ms");
    }
}
