package utils;

public class Utils {
	public static String normaliseString(String input) {
		String in = input.replaceAll("[\t\"\':\\(\\)]", " ").replaceAll(
				"\\s{2,}", " ");
		
		return in.trim();
	}
}
