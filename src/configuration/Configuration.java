package configuration;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import xml.parser.XmlParser;

/**
 * @author shaielb
 *
 */
@SuppressWarnings("unchecked")
public class Configuration {

	/**
	 * @param path
	 * @return
	 */
	public static Map<String, Object> configuration(String path) {
		try {
			if (new File(path).exists()) {
				Map<String, Object> main = XmlParser.parse(path);
				if (main != null) {
					return (Map<String, Object>) main.get("configuration");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param is
	 * @return
	 */
	public static Map<String, Object> configuration(InputStream is) {
		try {
			Map<String, Object> main = XmlParser.parse(is);
			if (main != null) {
				return (Map<String, Object>) main.get("configuration");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return
	 */
	public static Map<String, String> arguemnts() {
		String localPath = System.getProperty("user.dir");
		String uri = String.format("%s\\Configurations\\confuration.xml", localPath);
		Object configuration = configuration(uri);
		if (configuration != null) {
			Object arguments = ((Map<String, Object>) configuration).get("arguments");
			if (arguments != null) {
				Map<String, String> args = new HashMap<String, String>();
				Map<String, Object> argumentsMap = (Map<String, Object>) arguments;
				for (String key : argumentsMap.keySet()) {
					args.put(key, (String) argumentsMap.get(key));
				}
				return args;
			}
		}
		return null;
	}
}
