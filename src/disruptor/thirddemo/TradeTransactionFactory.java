package disruptor.thirddemo;

import com.lmax.disruptor.EventFactory;

public class TradeTransactionFactory implements EventFactory<TradeTransaction> {

	@Override
	public TradeTransaction newInstance() {
		return new TradeTransaction();
	}

}
