package disruptor.firstdemo;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

import disruptor.common.LongEvent;
import disruptor.common.LongEventFactory;
import disruptor.common.LongEventHander;

public class DemoTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int BUFFER_SIZE = 1024;
		int THREAD_NUMBERS = 4;
		Date bein = new Date();
		final RingBuffer<LongEvent> ringbuffer = RingBuffer.create(ProducerType.MULTI, new LongEventFactory(), BUFFER_SIZE, new YieldingWaitStrategy());
		
		ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);
		
		SequenceBarrier sequenceBarrier = ringbuffer.newBarrier();
		
		BatchEventProcessor<LongEvent> longEventProcessor = new BatchEventProcessor<>(ringbuffer, sequenceBarrier, new LongEventHander());
		
		ringbuffer.addGatingSequences(longEventProcessor.getSequence());
		
		executors.submit(longEventProcessor);
		
		
		Future<?> future = executors.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				long seq;
				for (int i = 0; i < 1000000; i++){
					seq = ringbuffer.next();
					ringbuffer.get(seq).setValue(new Random().nextInt(2000));
					ringbuffer.publish(seq);
				}
				return null;
			}
		});
		
		future.get();
		longEventProcessor.halt();
		executors.shutdown();
		Date end = new Date();
		System.out.println("test cost time : " + (end.getTime()-bein.getTime())+" ms");
	}
}
