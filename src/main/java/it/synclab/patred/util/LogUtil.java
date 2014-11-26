package it.synclab.patred.util;

public class LogUtil {
	public static String getArguments(Object[] arguments) {
		if (arguments == null || arguments.length == 0)
			return "";
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arguments.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			
			sb.append(arguments[i]);
		}
		
		return sb.toString();
	}
}
