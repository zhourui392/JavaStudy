package concurrent.utilclass;

import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier的使用
 */
public class BankWaterService implements Runnable {
	private int count = 5;
	private CyclicBarrier c = new CyclicBarrier(count, this);
	
	private Executor executor = Executors.newFixedThreadPool(4);
	
	private ConcurrentHashMap<String, Integer> sheetBankWaterCount = 
			new ConcurrentHashMap<String, Integer>();

	private void count(){
		for (int i = 0; i < count; i++){
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
					
					try {
						c.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	@Override
	public void run() {
		int result = 0;
		
		for (Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()){
			result += sheet.getValue();
		}
		
		sheetBankWaterCount.put("result", result);
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		BankWaterService bankWaterCount = new BankWaterService();
		bankWaterCount.count();
	}
}
