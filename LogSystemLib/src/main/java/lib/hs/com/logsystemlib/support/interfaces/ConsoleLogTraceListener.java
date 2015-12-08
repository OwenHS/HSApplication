package lib.hs.com.logsystemlib.support.interfaces;

import android.util.Log;

/**
 * 在终端打印log的接口
 */
public class ConsoleLogTraceListener implements TraceListener {

	private boolean debug = true;

	public ConsoleLogTraceListener setDebug(boolean debug) {
		this.debug = debug;
		
		return this;
	}
	
	@Override
	public void traceEvent(int priority, String tag, Object message) {
		
		if(debug)
		Log.println(priority, tag, message.toString());

	}

}
