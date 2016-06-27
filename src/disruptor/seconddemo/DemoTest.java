package disruptor.seconddemo;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

import disruptor.common.LongEvent;
import disruptor.common.LongEventFactory;
import disruptor.common.LongEventHander;

public class DemoTest {
	public static void main(String[] args) {
		int BUFFER_SIZE = 1024;
		int THREAD_NUMBERS = 4;
		Date bein = new Date();
		
		RingBuffer<LongEvent> ringBuffer = RingBuffer.createMultiProducer(new LongEventFactory(), BUFFER_SIZE);
		
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		WorkHandler<LongEvent> workHandler = new LongEventHander();
		
		WorkerPool<LongEvent> workPool = new WorkerPool <LongEvent>(ringBuffer, sequenceBarrier,new IgnoreExceptionHandler(),workHandler);
		
		workPool.start(executorService);
		
		for (int i = 0; i < 10000000; i++){
			long seq = ringBuffer.next();
			ringBuffer.get(seq).setValue(new Random().nextInt(2000));
			ringBuffer.publish(seq);
		}
		workPool.halt();
		executorService.shutdown();
		Date end = new Date();
		System.out.println("test cost time : " + (end.getTime()-bein.getTime())+" ms");
	}
}
