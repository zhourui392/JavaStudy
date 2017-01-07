package jvm.four;

import java.util.*;

/**
 * Created by zhour on 2017/1/7.
 * 1. 写一个程序，尽量产生STW现象。给出代码和启动JVM参数。并附上GC的log日志，标出停顿的时间。
 *  1)设置新生代较小，新生代会频繁发生GC
 *  2)老年代被塞满，会频繁触发FullGC
   2. 是否有方法尽可能减少一次STW停顿时间？由此带来的弊端是什么？


 1、使用串行收集 -XX:+UseSerialGC
 2、设置大的老年代 -Xms100m -Xms100m -XX:NewRatio=1
 打印GC日志 -XX:+PrintGCDetails
 -Xms100m -Xms100m -XX:NewRatio=1 -XX:+PrintGCDetails

 3、塞满老年代，当JVM老年代无法分配内存时会频繁进行FullGC，直到内存溢出
 */
public class STWTest {
    public static List<String> dates = new ArrayList<>();

    public static void main(String[] args) {
        while (true){
            for (int i = 0; i < 1800000;i++){
                dates.add(UUID.randomUUID().toString());
            }
            dates.clear();
        }

    }
}
