package com.hs.hshttplib.download;

/**********************************************************
 * @文件名称：I_FileLoader.java
 * @文件作者：Administrator
 * @创建时间：2015-5-30 下午2:31:43
 * @文件描述：文件下载接口协议
 * @修改历史：2015-5-30创建初始版本
 **********************************************************/
public interface I_FileLoader
{
	//下载 是否需要断点续传
    void doDownload(String url, boolean isResume);

    boolean isStop();

    void stop();
}
