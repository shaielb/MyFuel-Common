package configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.parser.XmlParser;

@SuppressWarnings("unchecked")
public class Configuration {

	public static Map<String, Object> configuration() {
		String localPath = System.getProperty("user.dir");
		String uri = String.format("%s\\Configurations\\confuration.xml", localPath);
		try {
			if (new File(uri).exists()) {
				Map<String, Object> main = XmlParser.parse(uri);
				if (main != null) {
					return (Map<String, Object>) main.get("configuration");
				}
			}
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, String> arguemnts() {
		Object configuration = configuration();
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
