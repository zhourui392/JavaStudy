package jvm.ten;

/**
 * Created by zhour on 2017/3/13.
 */
public class SynchronizedPoint {
    //一个点的x，y坐标
    private   double   x,y;

    // an exclusively locked method
    void move(double deltaX,double deltaY) {
        synchronized (this){
            x +=deltaX;
            y +=deltaY;
        }
    }

    double distanceFromOrigin() {
        double  currentX,   currentY;
        synchronized (this){
            currentX =x;
            currentY =y;
        }
        return Math.sqrt(currentX *currentX + currentY *currentY);
    }
}
