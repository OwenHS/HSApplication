package com.hs.lib.log;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandleUtil implements Thread.UncaughtExceptionHandler {
	
	private Thread.UncaughtExceptionHandler defaultHandler;
	private static ExceptionHandleUtil instance;
	

	private ExceptionHandleUtil(Context context){
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public static void register(Context context){
		
		if(instance == null){
			instance = new ExceptionHandleUtil(context.getApplicationContext());
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
		
		if(instance!=null && HLog.getDebugMode()){
			HLog.crash("crash",getMessage(ex));
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
