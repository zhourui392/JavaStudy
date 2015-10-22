package concurrent.threadgroup;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * threadgroup使用
 * 有 10 个随机时间休眠的线程 ，当其中一个完成，就中断其余的。
 */
public class SearchTask implements Runnable {
	private Result result;
	
	public SearchTask(Result result){
		this.result = result;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		try {
			doTask();
			result.setName(name);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return ;
		}
		System.out.printf("thread %s end.\n", Thread.currentThread().getName());
		
	}
	
	public void doTask() throws InterruptedException{
		Random random = new Random(new Date().getTime());
		int value = (int)(random.nextDouble()*100);
		System.out.printf("currentthread %s : %d\n", Thread.currentThread().getName(), value);
		TimeUnit.SECONDS.sleep(value);
	}

	public static void main(String[] args) {
		ThreadGroup group = new ThreadGroup("searchTaskGroup");
		Result result = new Result();
		SearchTask searchTask = new SearchTask(result);
		
		for (int i = 0; i < 10; i++){
			Thread thread = new Thread(group,searchTask);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.printf("number of active threads %s\n", group.activeCount());
		
		System.out.println("information about the threadgroup");
		group.list();
		
		Thread[] threads = new Thread[group.activeCount()];
		group.enumerate(threads);
		for (int i = 0; i < 10; i++){
			System.out.printf("thread "+threads[i].getName()+" : "+threads[i].getState()+"/n" );
		}
		waitFlush(group);
		group.interrupt();
	}
	
	public static void waitFlush(ThreadGroup threadGroup){
		//有一个线程完成，就中断其他的线程
		while(threadGroup.activeCount() > 9){
			System.out.println("group activeCount : " +threadGroup.activeCount());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
