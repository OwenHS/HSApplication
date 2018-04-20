package com.hs.hshttplib.core;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * ********************************************************
 * @文件名称：CaseInsensitiveMap.java
 * @文件作者：Administrator
 * @创建时间：2015-5-28 下午1:55:12
 * @文件描述： 一个无视大小写的Map
 * @修改历史：2015-5-28创建初始版本
 *********************************************************
 */
public class CaseInsensitiveMap<K, V> extends TreeMap<K, V>
{
	private static final long serialVersionUID = -503548416018184655L;

	public CaseInsensitiveMap()
	{
		super(new InsensitiveComparator<K>());
	}

	static class InsensitiveComparator<T> implements Comparator<T>
	{

		@Override
		public int compare(Object lhs, Object rhs)
		{
			String s1 = (String) lhs;
			String s2 = (String) rhs;
			return s1.toUpperCase().compareTo(s2.toUpperCase());
		}

		@Override
		public boolean equals(Object o)
		{
			return compare(this, o) == 0;
		}
	}
}
