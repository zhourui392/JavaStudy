package concurrent.utilclass;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 同步屏障
 * @author zhourui <zhourui0125@gmail.com>
 */
public class CyclicBarrierTest {
	static CyclicBarrier c = new CyclicBarrier(2, new A());
	
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					c.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				
				System.out.println(1);
			}
		}).start();
		
		
		c.await();
		System.out.println(2);
	}
}

class A implements Runnable{

	@Override
	public void run() {
		System.out.println("3");
	}
	
}
