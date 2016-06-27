package disruptor.common;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class LongEventHander implements EventHandler<LongEvent>,WorkHandler<LongEvent>{

	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("event :"  + event.getValue());
	}

	@Override
	public void onEvent(LongEvent event) throws Exception {
		System.out.println("event :"  + event.getValue());
	}

}
