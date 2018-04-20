package com.hs.hshttplib.util;

public class HSConfig {

	public static final double VERSION = 2.14;

	/** 错误处理广播 */
	public static final String RECEIVER_ERROR = HSConfig.class.getName()
			+ "org.kymjs.android.frame.error";
	/** 无网络警告广播 */
	public static final String RECEIVER_NOT_NET_WARN = HSConfig.class.getName()
			+ "org.kymjs.android.frame.notnet";
	/** preference键值对 */
	public static final String SETTING_FILE = "HSframe_preference";
	public static final String ONLY_WIFI = "only_wifi";

}
