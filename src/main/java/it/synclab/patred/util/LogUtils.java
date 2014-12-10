package it.synclab.patred.util;


public class LogUtils {
	
	@SuppressWarnings("rawtypes")
	public static String formatClassName(Class clazz) {
		String name = clazz.getName();
		int i = name.indexOf("$$");
		if (i > 10)
			name = name.substring(0, i);
		return name;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getShortName(Class clazz) {
		String classname = formatClassName(clazz);
		
		StringBuilder shortName = new StringBuilder();
		
		for (int i = 0; i < classname.length(); i++) {
			if (Character.isUpperCase(classname.charAt(i))) {
				shortName.append(classname.charAt(i));
			}
		}
		shortName.append(".");
		return shortName.toString();
		
	}
	
}
