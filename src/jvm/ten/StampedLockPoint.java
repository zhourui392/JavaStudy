package jvm.ten;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by zhour on 2017/3/13.
 */
public class StampedLockPoint {
    //一个点的x，y坐标
    private double x,y;
    private final StampedLock stampedLock   =  new StampedLock();

    // an exclusively locked method
    void move(double deltaX,double deltaY) {
        long stamp =stampedLock.writeLock(); //写锁
        try {
            x +=deltaX;
            y +=deltaY;
        } finally {
            stampedLock.unlockWrite(stamp);//释放写锁
        }
    }

    double distanceFromOrigin() {
        double  currentX =x,   currentY =y;
        long stamp =stampedLock.readLock(); //读锁
        try {
            currentX =x;
            currentY =y;
        }finally{
            stampedLock.unlockRead(stamp);//释放读锁
        }
        //读锁验证成功后才执行计算，即读的时候没有发生写
        return Math.sqrt(currentX *currentX + currentY *currentY);
    }
}
