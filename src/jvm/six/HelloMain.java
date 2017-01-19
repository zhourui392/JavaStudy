package jvm.six;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhourui on 2017/1/18.
 */
public class HelloMain {
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        Worker worker = new Worker();

        while (true){
            worker.doit();
            TimeUnit.SECONDS.sleep(1);

            HelloMain.class.getClassLoader();
//            HelloMain.class.getClassLoader().loadClass("jvm.six.Worker");
        }
    }
}
