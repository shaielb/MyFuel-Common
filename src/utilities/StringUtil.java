package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtil {

	public static String swithToUpperCase(String str, String separator) {
		String[] parts = str.split(separator);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; ++i) {
			sb.append(parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1, parts[i].length()).toLowerCase());
		}
		return sb.toString();
	}

	public static String replace(String source, Map<String, String> replacements) {
		String[] lines = source.split("\n");
		for (int i = 0; i < lines.length; ++i) {
			for (Entry<String, String> replacement : replacements.entrySet()) {
				lines[i] = lines[i].replace(replacement.getKey(), replacement.getValue());
			}
		}
		return String.join("\n", lines);
	}

	public static String replace(String source, String target, String replacement) {
		String[] lines = source.split("\n");
		for (int i = 0; i < lines.length; ++i) {
			lines[i] = lines[i].replace(target, replacement);
		}
		return String.join("\n", lines);
	}

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
}
