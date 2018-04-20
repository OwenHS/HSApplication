/**
 * 
 */
package com.hs.hshttplib.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.http.HttpEntity;

import android.util.Log;

import com.hs.hshttplib.download.SimpleDownloader.DownloadProgress;
import com.hs.hshttplib.util.FileUtils;

/**********************************************************
 * @文件名称：FileEntityHandler.java
 * @文件作者：Administrator
 * @创建时间：2015-5-29 下午2:28:37
 * @文件描述：
 * @修改历史：2015-5-29创建初始版本
 **********************************************************/
public class FileEntityHandler
{

	private boolean mStop = false;

	public boolean isStop()
	{
		return mStop;
	}

	public void setStop(boolean stop)
	{
		this.mStop = stop;
	}

	public File handleEntity(HttpEntity entity, DownloadProgress callback, File save, boolean isResume)
			throws IOException
	{
		long current = 0;
		RandomAccessFile file = new RandomAccessFile(save, "rw");
		if (isResume)
		{
			current = file.length();
		}
		InputStream input = entity.getContent();
		long count = entity.getContentLength() + current;
		if (mStop)
		{
			FileUtils.closeIO(file);
			return save;
		}
		// 在这里其实这样写是不对的，之所以如此是为了用户体验，谁都不想自己下载时进度条都走了一大半了，就因为一个暂停一下子少了一大串
		/**
		 * 这里实际的写法应该是： <br>
		 * current = input.skip(current); <br>
		 * file.seek(current); <br>
		 * 根据JDK文档中的解释：Inputstream.skip(long i)方法跳过i个字节，并返回实际跳过的字节数。<br>
		 * 导致这种情况的原因很多，跳过 n 个字节之前已到达文件末尾只是其中一种可能。这里我猜测可能是碎片文件的损害造成的。
		 */
		
		file.seek(/*input.skip(current)*/current);

		int readLen = 0;
		byte[] buffer = new byte[1024];

		while ((readLen = input.read(buffer, 0, 1024)) != -1)
		{
			if (mStop)
			{
				break;
			}
			else
			{
				file.write(buffer, 0, readLen);
				current += readLen;
				//下载了部分实体后,更新进度
				callback.onProgress(count, current);
			}
		}
		callback.onProgress(count, current);
		Log.d("owen","end "+"FielEntityHandler publishProgress count = "+count+" current = "+current);
		if (mStop && current < count)
		{
			// 用户主动停止
			Log.d("http_file","FielEntityHandler 用户主动暂停");
			FileUtils.closeIO(file);
			throw new IOException("user stop download thread");
		}
		
		FileUtils.closeIO(file);
		return save;
	}

}
