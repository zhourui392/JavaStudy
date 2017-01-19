package concurrent.threadPool;

/**
 * Created by zhourui on 2017/1/19.
 */
public class MyRunnable implements Runnable {
    private String name;
    private StackTraceElement[] stackTrace;
    public MyRunnable(String name, StackTraceElement[] stackTrace){
        this.name = name;
        this.stackTrace = stackTrace;
    }
    @Override
    public void run() {
        try {
            throw new Exception("test");
        }catch (Exception e){
//            e.printStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace){
                System.out.println(stackTraceElement.toString());
            }
        }
    }
}
