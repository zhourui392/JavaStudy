package jvm.six;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhourui on 2017/1/18.
 */
public class HelloMain {
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        while (true){
            HelloMainClassLoader classLoader = new HelloMainClassLoader();
            Class<?> workerClass = classLoader.findClass("jvm.six.Worker");

            Object worker;
            try {
                worker = workerClass.newInstance();
                Method doItMethod = workerClass.getMethod("doit");
                doItMethod.invoke(worker);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
