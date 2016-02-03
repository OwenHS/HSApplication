package com.hs.hshttplib;

import com.hs.hshttplib.util.StringUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class HttpParams
{
	public Map<String, String> urlParams;
	
	private SortBy order = SortBy.NonOrder;
	
	public enum SortBy
	{
		NonOrder,OrderByAlbet
	}
	
	public ConcurrentHashMap<String, FileWrapper> fileWraps;

	public HttpParams()
	{
		init();
	}
	
	public HttpParams(SortBy order)
	{
		this.order = order;
	}
	
	public HttpParams(int i)
	{
		init(i);
	}

	public void init()
	{
		init(4);
	}

	public void init(int i)
	{
		if(order == SortBy.OrderByAlbet)
		{
			urlParams = new TreeMap<String, String>();
		}
		else
		{
			urlParams = new ConcurrentHashMap<String, String>(8);
		}
		// multipart分隔符集合
		fileWraps = new ConcurrentHashMap<String, FileWrapper>(i);
	}

	public boolean haveFile()
	{
		return (fileWraps.size() != 0);
	}

	public void put(String key, int value)
	{
		put(key, value + "");
	}

	public void put(String key, String value)
	{
		if (key != null && value != null)
		{
			urlParams.put(key, value);
		}
		else
		{
			throw new RuntimeException("key or value is NULL");
		}
	}

	public void put(String key, byte[] file)
	{
		put(key, new ByteArrayInputStream(file));
	}

	public void put(String key, File file) throws FileNotFoundException
	{
		put(key, new FileInputStream(file), file.getName());
	}

	public void put(String key, InputStream value)
	{
		put(key, value, "fileName");
	}

	public void put(String key, InputStream value, String fileName)
	{
		if (key != null && value != null)
		{
			fileWraps.put(key, new FileWrapper(value, fileName, null));
		}
		else
		{
			throw new RuntimeException("key or value is NULL");
		}

	}

	public String getStringParams()
	{
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : urlParams.entrySet())
		{
			if (result.length() > 0)
			{
				result.append("&");
			}
			if (fileWraps.size() != 0)
			{
				try
				{
					result.append(URLEncoder.encode(entry.getKey(), "utf-8"));
					result.append("=");
					result.append(URLEncoder.encode(entry.getValue(), "utf-8"));
				}
				catch (UnsupportedEncodingException e)
				{
					result.append(entry.getKey());
					result.append("=");
					result.append(entry.getValue());
				}
			}
			else
			{
			}
		}
		return result.toString();
	}

	public void remove(String key)
	{
		urlParams.remove(key);
		fileWraps.remove(key);
	}

	// 在get中将参数拼接成 xx=yy&xx=yy&xx=yy
	@Override
	public String toString()
	{

		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : urlParams.entrySet())
		{
			if (result.length() > 0)
			{
				result.append("&");
			}

			try
			{
				result.append(URLEncoder.encode(entry.getKey(), "utf-8"));
				result.append("=");
				result.append(URLEncoder.encode(entry.getValue(), "utf-8"));
			}
			catch (UnsupportedEncodingException e)
			{
				result.append(entry.getKey());
				result.append("=");
				result.append(entry.getValue());
			}
		}

		return result.toString();
	}

	/**
	* 获取参数集
	*/
	public HttpEntity getEntity()
	{
		HttpEntity entity = null;
		if (!fileWraps.isEmpty())
		{
			MultipartEntity multipartEntity = new MultipartEntity();
			for (Map.Entry<String, String> entry : urlParams.entrySet())
			{
				multipartEntity.addPart(entry.getKey(), entry.getValue());
			}
			int currentIndex = 0;
			int lastIndex = fileWraps.entrySet().size() - 1;
			for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileWraps.entrySet())
			{
				FileWrapper file = entry.getValue();
				if (file.inputStream != null)
				{
					boolean isLast = currentIndex == lastIndex;
					if (file.contentType != null)
					{
						multipartEntity.addPart(entry.getKey(), file.fileName, file.inputStream, file.contentType,
								isLast);
					}
					else
					{
						multipartEntity.addPart(entry.getKey(), file.fileName, file.inputStream, isLast);
					}
				}
				currentIndex++;
			}
			entity = multipartEntity;
		}
		else
		{
			try
			{
				entity = new UrlEncodedFormEntity(getParamsList(), "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return entity;
	}

	protected List<BasicNameValuePair> getParamsList()
	{
		List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : urlParams.entrySet())
		{
			lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return lparams;
	}

	/**
	 * 封装一个文件参数
	 * 
	 */
	public static class FileWrapper
	{
		public InputStream inputStream;
		public String fileName;
		public String contentType;

		public FileWrapper(InputStream inputStream, String fileName, String contentType)
		{
			this.inputStream = inputStream;
			this.contentType = contentType;
			if (StringUtils.isEmpty(fileName))
			{
				this.fileName = "nofilename";
			}
			else
			{
				this.fileName = fileName;
			}
		}
	}
}
