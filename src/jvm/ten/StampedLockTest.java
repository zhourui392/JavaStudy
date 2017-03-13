package jvm.ten;

import org.nutz.lang.Times;
/**
 * Created by zhour on 2017/3/13.
 */
public class StampedLockTest {
    private static int index = 0;
    private static StampedLockPoint point = new StampedLockPoint();
    private static final int END = 10000;
    public static void main(String[] args) {
        final long start =Times.now().getTime();
        for (int i = 0; i < 20; i++){
            new Thread(()->{
                while (true){
                    if (index > END){
                        break;
                    }
                    point.distanceFromOrigin();
                }
            }).start();
        }
        new Thread(()->{
            while (true){
                if (index <= END){
                    point.move(1,1);
                    index++;
                }else {
                    System.out.println("cost time:"+(Times.now().getTime()-start) +"m");
                    System.out.println(point.distanceFromOrigin());
                    break;
                }
            }
        }).start();
    }
}
