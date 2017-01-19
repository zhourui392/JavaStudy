package jvm.six;

/**
 * Created by zhourui on 2017/1/18.
 */
public class HelloMainClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name,true);
    }
}
