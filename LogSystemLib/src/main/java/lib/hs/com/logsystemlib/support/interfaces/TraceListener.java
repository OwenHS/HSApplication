package lib.hs.com.logsystemlib.support.interfaces;

/**
 * log发生时候的操作接口
 */
public interface TraceListener {
	/**
	 * 当log需要产生的时候，触发此接口
	 * @param priority 	触发等级
	 * @param tag		触发Tag
	 * @param message	触发信息
	 */
	public void traceEvent(int priority, String tag, Object message);
}
