package jvm.two;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhour on 2016/12/21.
 */
public class PemOutOfMemory {
    public static void main(String[] args) {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 1000000;i++){
            testList.add("test"+i);
        }
    }
}
