package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtil {

	/**
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String swithToUpperCase(String str, String separator) {
		String[] parts = str.split(separator);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; ++i) {
			sb.append(parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1, parts[i].length()));
		}
		return sb.toString();
	}
	
	/**
	 * @param str
	 * @param separators
	 * @return
	 */
	public static String swithToUpperCase(String str, String[] separators) {
		for (String separator: separators) {
			str = swithToUpperCase(str, separator);
		}
		return str;
	}

	/**
	 * @param source
	 * @param replacements
	 * @return
	 */
	public static String replace(String source, Map<String, String> replacements) {
		String[] lines = source.split("\n");
		for (int i = 0; i < lines.length; ++i) {
			for (Entry<String, String> replacement : replacements.entrySet()) {
				lines[i] = lines[i].replace(replacement.getKey(), replacement.getValue());
			}
		}
		return String.join("\n", lines);
	}

	/**
	 * @param source
	 * @param target
	 * @param replacement
	 * @return
	 */
	public static String replace(String source, String target, String replacement) {
		String[] lines = source.split("\n");
		for (int i = 0; i < lines.length; ++i) {
			lines[i] = lines[i].replace(target, replacement);
		}
		return String.join("\n", lines);
	}

	/**
	 * @param line
	 * @return
	 */
	public static String[] separateByUpperCase(String line) {
		List<String> list = new ArrayList<String>();
		char[] chars = line.toCharArray();
		String word = "";
		boolean in = false;
		for (int i = 0; i < chars.length; ++i) {
			if (Character.isUpperCase(chars[i])) {
				if (in) {
					list.add(word);
					word = "";
				}
				in = true;
			}
			if (in) {
				word += chars[i];
			}
		}
		if (in) {
			list.add(word);
		}
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static String firstToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * @param colName
	 * @return
	 */
	public static String getTitle(String colName) {
		colName = swithToUpperCase(colName, "_");
		return String.join(" ", separateByUpperCase(colName));
	}
}
