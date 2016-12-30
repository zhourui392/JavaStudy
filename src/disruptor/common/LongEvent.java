package disruptor.common;

public class LongEvent {
	private long value;
	public void setValue(long value) {
		this.value = value;
	}
	public long getValue() {
		return value;
	}
}
