package com.hs.hshttplib.download;

import com.hs.hshttplib.HttpCallBack;

import java.io.File;

/**********************************************************
 * @文件名称：FileProxyHttpCallBack.java
 * @文件作者：Administrator
 * @创建时间：2015-9-7 下午2:31:34
 * @文件描述：
 * @修改历史：2015-9-7创建初始版本
 **********************************************************/
public class FileProxyHttpCallBack extends HttpCallBack
{
	private HttpCallBack callBack;
	private FileFinishCallBack finishCallBack;
	private String url;

	public FileProxyHttpCallBack(String url, HttpCallBack callBack, FileFinishCallBack finishCallBack)
	{
		this.url = url;
		this.callBack = callBack;
		this.finishCallBack = finishCallBack;
	}

	@Override
	public void onPreStart()
	{
		callBack.onPreStart();
	}

	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg)
	{
		callBack.onFailure(t, errorNo, strMsg);
	}

	@Override
	public void onLoading(long count, long current)
	{
		callBack.onLoading(count,current);
	}

	@Override
	public void onSuccess(File f)
	{
		callBack.onSuccess(f);
	}

	@Override
	public void onFinish()
	{
		if (callBack != null)
			callBack.onFinish();

		if (finishCallBack != null)
			finishCallBack.removeLoader(url);
	}

	public interface FileFinishCallBack
	{
		public void removeLoader(String url);
	}
}
