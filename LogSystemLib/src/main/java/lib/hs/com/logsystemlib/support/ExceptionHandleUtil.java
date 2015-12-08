package lib.hs.com.logsystemlib.support;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandleUtil implements java.lang.Thread.UncaughtExceptionHandler {
	
	private static final String Tag = ExceptionHandleUtil.class.getSimpleName();
	private Thread.UncaughtExceptionHandler defaultHandler;
	private static ExceptionHandleUtil instance;
	
	private static ErrorLogTrace trace;
	
	private ExceptionHandleUtil(Context context){
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		trace = new ErrorLogTrace("crash", context);
	}
	
	public static void register(Context context){
		
		if(instance == null){
			instance = new ExceptionHandleUtil(context);
		}
		
		Thread.setDefaultUncaughtExceptionHandler(instance);
	}
	
	/**
	 * 异常发生时触发的函数
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		if(instance == null && defaultHandler == null){
			return;
		}
		
		if(instance!=null){
			
			if(null != trace)
				trace.print("CrashHandler", ex);
			
			android.os.Process.killProcess(android.os.Process.myPid());
		}else{
			defaultHandler.uncaughtException(thread, ex);
		}
	}
	
	/**
	 * 异常处理处
	 * @param exception
	 */
	private void handle(Throwable exception){
		
	}
	
	/**
	 * 获取分行号的消息
	 * @param ex
	 * @return
	 */
	private String getMessage(Throwable ex){
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		return stringWriter.toString();
	}
}
