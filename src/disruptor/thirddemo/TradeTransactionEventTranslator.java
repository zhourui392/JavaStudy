package disruptor.thirddemo;

import java.util.Random;

import com.lmax.disruptor.EventTranslator;

public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {

	@Override
	public void translateTo(TradeTransaction event, long sequence) {
		this.generateTradeTransaction(event);
	}

	private TradeTransaction generateTradeTransaction(TradeTransaction trade){  
        trade.setPrice(new Random().nextDouble());  
        return trade;  
    }
}
