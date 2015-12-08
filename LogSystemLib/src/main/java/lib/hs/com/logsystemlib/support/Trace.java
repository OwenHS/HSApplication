package lib.hs.com.logsystemlib.support;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lib.hs.com.logsystemlib.support.interfaces.TraceListener;

public final class Trace {

	public static final int ALL = -1;
	public static final int VERBOSE = 2;
	public static final int DEBUG = 3;
	public static final int INFO = 4;
	public static final int WARN = 5;
	public static final int ERROR = 6;
	public static final int CRITICAL = 10;
	
	private int priority = 4;

	// 用于执行打印任务
	private ExecutorService service;
	// 放置打印消息回调的接口，目前是终端和文件
	public ArrayList<TraceListener> listeners;
	private String name;

	public Trace(String name) {
		this.name = name;
		service = Executors.newSingleThreadExecutor();
		listeners = new ArrayList<TraceListener>();
	}

	/**
	 * 添加发生log时候的触发接口
	 * @param listener
	 * @return
	 */
	public Trace addListener(TraceListener listener) {
		listeners.add(listener);
		return this;
	}

	public Trace removeListener(TraceListener listener) {
		listeners.remove(listener);
		return this;
	}

	public void critical(final String tag, final Throwable exp) {
		critical(tag, getMessage(exp));
	}

	public void critical(final String tag, final Object message) {
		if (priority <= CRITICAL) {
			print(CRITICAL, tag, message);
		}
	}

	public void verbose(final String tag, final Throwable exp) {
		verbose(tag, getMessage(exp));
	}

	public void verbose(final String tag, final Object message) {
		if (priority <= VERBOSE) {
			print(VERBOSE, tag, message);
		}
	}

	public void error(final String tag, final Throwable exp) {
		error(tag, getMessage(exp));
	}

	public void error(final String tag, final Object message) {
		if (priority <= ERROR) {
			print(ERROR, tag, message);
		}
	}

	public void information(final String tag, final Throwable exp) {
		information(tag, getMessage(exp));
	}

	public void information(final String tag, final Object message) {
		if (priority <= INFO) {
			print(INFO, tag, message);
		}
	}

	public void warning(final String tag, final Throwable exp) {
		warning(tag, getMessage(exp));
	}

	public void warning(final String tag, final Object message) {
		if (priority <= WARN) {
			print(WARN, tag, message);
		}
	}

	public void debug(final String tag, final Throwable exp) {
		debug(tag, getMessage(exp));
	}

	public void debug(final String tag, final Object message) {
		if (priority <= DEBUG) {
			print(DEBUG, tag, message);
		}
	}

	public void print(final int priority, final String tag, final Object message) {
		service.execute(new Runnable() {

			@Override
			public void run() {
				for (TraceListener listener : listeners) {
					listener.traceEvent(priority, tag, message);
				}
			}
		});
	}

	public String getName() {
		return name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
	 * 获取Exception的简便异常信息
	 * 
	 * @param exp
	 * @return
	 */
	public static String getMessage(Throwable exp) {

		StringWriter sw = new StringWriter();
		exp.printStackTrace(new PrintWriter(sw, true));

		String str = sw.toString();

		return str;
	}
}
