package com.cvc.util;

public final class CoreUtil {
	
	public static String addDecimalDigits(String value) {
		
		if (!value.contains("//.")) {
			value  = value.concat(".00");
		}
		return value;
	}

}
