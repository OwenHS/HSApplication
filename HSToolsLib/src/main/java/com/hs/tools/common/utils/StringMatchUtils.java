package com.hs.tools.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatchUtils
{
	public static boolean isEmail(String email)
	{
		String str = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
