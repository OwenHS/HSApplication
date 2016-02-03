/**
 * 
 */
package com.hs.hshttplib.core;


/**********************************************************
 * @文件名称：SimpleSafeAsyncTask.java
 * @文件作者：Administrator
 * @创建时间：2015-6-5 上午11:51:58
 * @文件描述：简化版的HSSafeTask， 为bitmap下载准备的中间class
 * @修改历史：2015-6-5创建初始版本
 **********************************************************/
public abstract class SimpleSafeAsyncTask<T> extends HSSafeTask<Void, Void, T>
{

	@Override
	protected T doInBackgroundSafely(Void... params) throws Exception
	{
		return doInBackground();
	}

	public abstract T doInBackground();
}
