package utilities;

import java.util.HashMap;
import java.util.Map;

public class Cache {

	/**
	 * 
	 */
	public static Map<String, Object> _cache = new HashMap<String, Object>();
	
	/**
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		_cache.put(key, value);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return _cache.get(key);
	}
}
