package com.hs.tools.common.utils;

public class EmptyUtils {
	
	public static boolean isEmpty(String dst){
		
		if(dst == null){
			return true;
		}
		
		if(dst.trim().equals("")){
			return true;
		}
		
		/*if(dst.equals("null")){
			return true;
		}*/
		
		return false;
	}
	
}
