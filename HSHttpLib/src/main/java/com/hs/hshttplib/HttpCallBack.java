package com.hs.hshttplib;

import java.io.File;
import java.net.HttpURLConnection;

/**
 * ********************************************************
 * @文件名称：HttpCallBack.java
 * @文件作者：Administrator
 * @创建时间：2015-5-29 上午10:02:16
 * @文件描述：Http请求回调类
 * @修改历史：2015-5-29创建初始版本
 *********************************************************
 */
public abstract class HttpCallBack
{

	public int respondCode = -1;

	/**
	 * Http请求开始前回调
	 */
	public void onPreStart()
	{
	};

	/**
	 * Http请求连接时调用
	 * 
	 * <b>waring</b> run in asynchrony thread
	 */
	public void onHttpConnection(HttpURLConnection conn)
	{
	}

	/**
	 * 进度回调，仅支持Download时使用
	 * 
	 * @param count
	 *            总数
	 * @param current
	 *            当前进度
	 */
	public void onLoading(long count, long current)
	{
	}

	/**
	 * Http请求成功时回调
	 * 
	 * @param t
	 */
	public void onSuccess(String t)
	{
	}

	/**
	 * Http请求成功时回调
	 * 
	 * @param t
	 */
	public void onSuccessFromCache(int code, String t)
	{
		onSuccess(code, t);
	}

	/**
	 * Http请求成功时回调
	 * 
	 * @param code
	 *            请求码
	 * @param t
	 *            Http请求返回信息
	 */
	public void onSuccess(int code, String t)
	{
		if (code != 200)
		{
			onFailure(new Exception(), code, "");
			return;
		}
		onSuccess(t);
	}

	/**
	 * Http下载成功时回调
	 */
	public void onSuccess(File f)
	{
	}

	/**
	 * Http请求失败时回调
	 * 
	 * @param t
	 * @param errorNo
	 *            错误码
	 * @param strMsg
	 *            错误原因
	 */
	public void onFailure(Throwable t, int errorNo, String strMsg)
	{
	}

	/**
	 * Http请求结束后回调
	 */
	public void onFinish()
	{
	}

}
