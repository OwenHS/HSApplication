/**
 * 
 */
package com.hs.hshttplib;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**********************************************************
 * @文件名称：MultipartEntity.java
 * @文件作者：Administrator
 * @创建时间：2015-5-30 下午4:06:03
 * @文件描述：  用于向网络发送http参数，这个类参考自博客
 * http://blog.rafaelsanches.com/2011/01/29/upload-using-multipart-post-
 * using-httpclient-in-android/)<br>
 * @修改历史：2015-5-30创建初始版本
 **********************************************************/

final class MultipartEntity implements HttpEntity
{

	private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	private String boundary = null; // 分隔符
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	boolean isSetLast = false;
	boolean isSetFirst = false;

	public MultipartEntity()
	{
		// 使用线程安全的StringBuffer而不是StringBuilder
		final StringBuffer buf = new StringBuffer();
		final Random rand = new Random();
		for (int i = 0; i < 30; i++)
		{
			buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
		}
		this.boundary = buf.toString();

	}

	/**
	 * 做参数之间的分割
	 */
	public void writeFirstBoundaryIfNeeds()
	{
		if (!isSetFirst)
		{
			try
			{
				out.write(("--" + boundary + "\r\n").getBytes());
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
		isSetFirst = true;
	}

	/**
	 * 做参数末尾的分割
	 */
	public void writeLastBoundaryIfNeeds()
	{
		if (isSetLast)
		{
			return;
		}
		try
		{
			out.write(("\r\n--" + boundary + "--\r\n").getBytes());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		isSetLast = true;
	}

	/**
	 * 添加一个字符参数
	 * 
	 * @param key
	 * @param value
	 */
	public void addPart(final String key, final String value)
	{
		writeFirstBoundaryIfNeeds();
		try
		{
			out.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
			out.write(value.getBytes());
			out.write(("\r\n--" + boundary + "\r\n").getBytes());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 添加一个文件参数
	 * 
	 * @param key
	 *            k
	 * @param fileName
	 *            文件名
	 * @param fin
	 *            文件输入流
	 * @param isLast
	 *            是否为最后一个参数
	 */
	public void addPart(final String key, final String fileName, final InputStream fin, final boolean isLast)
	{
		addPart(key, fileName, fin, "application/octet-stream", isLast);
	}

	public void addPart(final String key, final String fileName, final InputStream fin, String type,
			final boolean isLast)
	{
		writeFirstBoundaryIfNeeds();
		try
		{
			type = "Content-Type: " + type + "\r\n";
			out.write(("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n")
					.getBytes());
			out.write(type.getBytes());
			out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());

			final byte[] tmp = new byte[4096];
			int l = 0;
			while ((l = fin.read(tmp)) != -1)
			{
				out.write(tmp, 0, l);
			}
			if (!isLast)
				out.write(("\r\n--" + boundary + "\r\n").getBytes());
			out.flush();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fin.close();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加一个文件参数，使用默认文件名
	 * 
	 * @param key
	 *            k
	 * @param value
	 *            v
	 * @param isLast
	 *            是否为最后一个参数
	 */
	public void addPart(final String key, final File value, final boolean isLast)
	{
		try
		{
			addPart(key, value.getName(), new FileInputStream(value), isLast);
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public long getContentLength()
	{
		writeLastBoundaryIfNeeds();
		return out.toByteArray().length;
	}

	@Override
	public Header getContentType()
	{
		return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
	}

	@Override
	public boolean isChunked()
	{
		return false;
	}

	@Override
	public boolean isRepeatable()
	{
		return false;
	}

	@Override
	public boolean isStreaming()
	{
		return false;
	}

	@Override
	public void writeTo(final OutputStream outstream) throws IOException
	{
		outstream.write(out.toByteArray());
	}

	@Override
	public Header getContentEncoding()
	{
		return null;
	}

	@Override
	public void consumeContent() throws IOException, UnsupportedOperationException
	{
		if (isStreaming())
		{
			throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
		}
	}

	@Override
	public InputStream getContent() throws IOException, UnsupportedOperationException
	{
		return new ByteArrayInputStream(out.toByteArray());
	}
}
