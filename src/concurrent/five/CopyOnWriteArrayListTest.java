package concurrent.five;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zhourui on 2017/1/14.
 * 2.       分析CopyOnWriteArrayList的核心代码，说明CopyOnWriteArrayList如何提高并行度。
 */
public class CopyOnWriteArrayListTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("test");
        copyOnWriteArrayList.get(1);
    }
}
