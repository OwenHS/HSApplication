package lib.hs.com.logsystemlib.support;

import android.content.Context;

import lib.hs.com.logsystemlib.support.interfaces.ConsoleLogTraceListener;
import lib.hs.com.logsystemlib.support.interfaces.FileLogTraceListener;

/**
 * 当发生了crash后触发
 */
public final class ErrorLogTrace {
	private Trace logTrace;
	
	public ErrorLogTrace(String name, Context context) {
		logTrace = new Trace(name);
		
		logTrace.setPriority(Trace.ALL);
		logTrace.addListener(new ConsoleLogTraceListener().setDebug(true));
		logTrace.addListener(new FileLogTraceListener(context.getExternalFilesDir(logTrace.getName()).getAbsolutePath()).setDebug(true));
	}
	
	public ErrorLogTrace setLevel(int level) {
		logTrace.setPriority(level);
		return this;
	}
	
    public void i(final String tag, final String message) {
    	logTrace.information(tag, message);
    }
     
    public void i(String tag, Exception exp) {
    	logTrace.information(tag, exp);
    }
     
    public void e(final String tag, final String message) {
    	logTrace.error(tag, message);
    }
     
    public void e(String tag, Exception exp) {
    	logTrace.error(tag, exp);
    }
     
    public void w(final String tag, final String message) {
    	logTrace.warning(tag, message);
    }
     
    public void w(String tag, Exception exp) {
    	logTrace.warning(tag, exp);
    }
     
    public void v(final String tag, final String message) {
    	logTrace.verbose(tag, message);
    }
     
    public void v(String tag, Exception exp) {
    	logTrace.verbose(tag, exp);
    }
     
    public void d(final String tag, final String message) {
    	logTrace.debug(tag, message);
    }
     
    public void d(String tag, Exception exp) {
    	logTrace.debug(tag, exp);
    }
    
    public void print(final String tag, final String message) {
    	logTrace.print(Trace.ALL, tag, message);
    }
    
    public void print(String tag, Throwable throwable) {
    	logTrace.print(Trace.ALL, tag, Trace.getMessage(throwable));
    }
}
