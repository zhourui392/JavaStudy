package spi;

import spi.service.HelloService;

import java.util.ServiceLoader;

/**
 * Created by zhourui on 2016/8/5.
 */
public class Demo {
    public static void main(String[] args) {
        ServiceLoader<HelloService> helloServiceLoader= ServiceLoader.load(HelloService.class);
        for(HelloService item:helloServiceLoader){
            System.out.print("invoked");
            item.hello();
        }
    }
}
