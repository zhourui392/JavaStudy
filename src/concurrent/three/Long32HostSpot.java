package concurrent.three;

/**
 * Created by zhour on 2016/12/30.
 */
public class Long32HostSpot {
    protected static long l = -1l;
    public static void main(String[] args) {
//        System.out.println("long -1:"+toBinary(-1l));
//        System.out.println("long 1: "+toBinary(1l));
        Worker w1 = new Worker();
        Worker2 w2 = new Worker2();

        w1.setDaemon(true);
        w2.setDaemon(true);
        w1.start();
        w2.start();
        for (int i = 0; i < 10000000; i++) {
            if (l != -1l && l != 1l) {
//                System.out.println("long t: "+toBinary(l));
            }
        }
    }
    private static String toBinary(long l) {
        StringBuilder sb = new StringBuilder(Long.toBinaryString(l));
        while (sb.length() < 64) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }
}

class Worker extends Thread {
    public Worker() {}
    @Override
    public void run() {
        while (true) {
            Long32HostSpot.l = -1l;
        }
    }
}
class Worker2 extends Thread {
    public Worker2() {}
    @Override
    public void run() {
        while (true) {
            Long32HostSpot.l = 1l;
        }
    }
}
