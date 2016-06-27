package disruptor.thirddemo;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction>, WorkHandler<TradeTransaction> {

	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(TradeTransaction event) throws Exception {
		event.setId(UUID.randomUUID().toString());
		System.out.println(event.getId());  
	}

}
