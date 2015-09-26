package concurrent.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
	private final ConcurrentHashMap<Object,FutureTask<String>> taskCache = 
			new ConcurrentHashMap<Object, FutureTask<String>>();
	
	private String exectionTask(final String taskName) throws InterruptedException, ExecutionException{
		while(true){
			Future<String> future = taskCache.get(taskName);
			if (future == null){
				Callable<String> task = new Callable<String>() {
					
					@Override
					public String call() throws Exception {
						return taskName;
					}
				};
				
				//创建任务
				FutureTask<String> futureTask = new FutureTask<String>(task);
				future = taskCache.putIfAbsent(taskName, futureTask);
				
				if (future == null){
					future = futureTask;
					//执行任务
					futureTask.run();
				}
			}
			
			try{
				//等待任务执行完毕
				return future.get();
			}catch (CancellationException e){
				taskCache.remove(taskName, future);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println(new FutureTaskTest().exectionTask("Hello"));
	}
}
