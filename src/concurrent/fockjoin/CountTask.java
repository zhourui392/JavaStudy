package concurrent.fockjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
	private static final int THRESHOLD = 2;
	private int start;
	private int end;
	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		boolean canCompute = (end - start) <= THRESHOLD;
		
		if (canCompute){
			for (int i = start; i <= end; i++){
				sum += 1;
			}
		}else{
			int middle = (start + end) / 2;
			CountTask leftCountTask = new CountTask(sum, middle);
			
			CountTask rightCountTask = new CountTask(middle+1, end);
			leftCountTask.fork();
			rightCountTask.fork();
			
			int leftResult = leftCountTask.join();
			int rightResult = rightCountTask.join();
			
			sum = leftResult + rightResult;
		}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask countTask = new CountTask(1, 100);
		Future<Integer> result = forkJoinPool.submit(countTask);
		
		try {
			System.out.println(result.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
