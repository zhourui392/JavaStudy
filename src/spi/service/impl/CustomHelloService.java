package spi.service.impl;

import spi.service.HelloService;

/**
 * Created by zhourui on 2016/8/5.
 */
public class CustomHelloService implements HelloService {
    @Override
    public void hello() {
        System.out.print("Hello Custom.");
    }
}
