package com.hs.hshttplib;

import com.hs.hshttplib.core.CaseInsensitiveMap;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.SSLSocketFactory;

/**********************************************************
 * @文件名称：HttpConfig.java
 * @文件作者：huangshuo
 * @创建时间：2015-5-18 上午10:36:46
 * @文件描述：Http配置文件
 * @修改历史：2015-5-18创建初始版本
 **********************************************************/
public class HttpConfig
{

	/** 缓存文件夹 */
	public static String CACHEPATH = "HSLibrary/cache";
	/** 定义http上传文件的数据分隔线 */
	public static final String BOUNDARY = "---------7d4a6d158c9";

	/** 缓存时间5分钟 */
	public long cacheTime = 0;
	/** 超时设置，包括读超时、写超时、socket链接超时 */
	public int timeOut = 30000;
    /** 为了更真实的模拟网络请求。如果启用，在读取完成以后，并不立即返回而是延迟500毫秒再返回 */
    public boolean useDelayCache = false;
    /** 如果启用了useDelayCache，本属性才有效。单位:ms */
    public long delayTime = 500;
    
    /** http请求最大并发连接数 */
    public int maxConnections = 10;
    /** 错误尝试次数，错误异常表请在RetryHandler添加 */
    public int maxRetries = 5;
    /** 默认8kb */
    public int socketBuffer = 8192;

	/** 消息头 */
	public TreeMap<String, String> httpHeader = null;
	public SSLSocketFactory sslSocketFactory = null;
	public Map<String, List<String>> respondHeader = null;

	public static String sCookie = "";
	
	public File savePath;

	public HttpConfig()
	{
		respondHeader = new HashMap<String, List<String>>();
		httpHeader = new CaseInsensitiveMap<String, String>();
		// 如果你是用Windows开发，请不要修改本选项；除非你的服务端也是windows
		httpHeader.put("Charset", "UTF-8");
	}
	
    public void setCookieString(String cookie) {
        sCookie = cookie;

    }

    public String getCookieString() {
        return sCookie;
    }
}
