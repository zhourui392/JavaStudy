package jvm.seven;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by zhour on 2017/2/8.
 *  计算给定函数 y=1/x 在定义域 [1,100]上围城与X轴围成的面积，计算步长0.01
 */
public class HomeWork {
    private static final double START = 1;
    private static final double END = 100;

    private static final double STEP = 0.0000001;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("step:"+ Double.toString(STEP));
        System.out.println("singleThread  :"+simpleCal());
        //fork/join
        System.out.println("forkJoinResult:"+ForkJoinCal());

    }

    private static double ForkJoinCal() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(START+STEP, END, STEP);
        Future<Double> result = forkJoinPool.submit(countTask);

        return result.get();
    }

    private static double simpleCal() {
        double now = START + STEP;
        double result = 0;
        while (now <= END){
            double areaVal = STEP * (1 / (now));
            result = result + areaVal ;
            now = now + STEP;
        }
        return result;
    }
}

class CountTask extends RecursiveTask<Double> {
    private static final double THRESHOLD = 0.1;
    private double start;
    private double end;
    private double step;
    public CountTask(double start, double end, double step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    protected Double compute() {
        double sumArea = 0;
        boolean canCompute = (end - start) <= THRESHOLD;

        if (canCompute){
            for (double i = start; i <= end; i = i+step){
                sumArea = sumArea + step * (1 / i);
            }
        }else{
            //fork
            double middle = (start + end) / 2;
            CountTask leftCountTask = new CountTask(start, middle,step);
            CountTask rightCountTask = new CountTask(middle+step, end, step);
            leftCountTask.fork();
            rightCountTask.fork();

            //join
            double leftResult = leftCountTask.join();
            double rightResult = rightCountTask.join();

            sumArea = leftResult + rightResult;
        }
        return sumArea;
    }
}
