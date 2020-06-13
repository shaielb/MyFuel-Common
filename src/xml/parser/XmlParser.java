package xml.parser;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("unchecked")
public class XmlParser {

	public interface NodeAnalyzer {
		public void analyze(Node node);
	}

	/**
	 * @param <T>
	 * @param path
	 * @param c
	 * @return
	 */
	public static <T> T parseToObject(String path, Class<T> c) {
		File xmlFile = new File(path);

		JAXBContext jaxbContext;
		T item = null;
		try
		{
			jaxbContext = JAXBContext.newInstance(c);              
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			item = (T) jaxbUnmarshaller.unmarshal(xmlFile);
		}
		catch (JAXBException e) 
		{
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * @param node
	 * @param nodeAnalyzer
	 * @return
	 */
	private static Object constructMap(Node node, NodeAnalyzer nodeAnalyzer) {
		NodeList list = node.getChildNodes();
		if (list != null && list.getLength() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			boolean found = false;
			for (int i = 0; i < list.getLength(); ++i) {
				if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					if (nodeAnalyzer != null) {
						nodeAnalyzer.analyze(list.item(i));
					}
					found = true;
					map.put(list.item(i).getNodeName(), constructMap(list.item(i), nodeAnalyzer));
				}
			}
			if (found)
				return map;
		}
		return node.getTextContent();
	}
	
	/**
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(InputStream is) throws Exception {
		return parse(is, (NodeAnalyzer) null);
	}

	/**
	 * @param is
	 * @param nodeAnalyzer
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(InputStream is, NodeAnalyzer nodeAnalyzer) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(is);

		doc.getDocumentElement().normalize();

		Map<String, Object> map = new HashMap<String, Object>();
		Node node = doc.getDocumentElement();
		if (nodeAnalyzer != null) {
			nodeAnalyzer.analyze(node);
		}
		map.put(node.getNodeName(), constructMap(node, nodeAnalyzer));
		return map;
	}
	
	/**
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(String path) throws Exception {
		return parse(path, (NodeAnalyzer) null);
	}

	/**
	 * @param path
	 * @param nodeAnalyzer
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(String path, NodeAnalyzer nodeAnalyzer) throws Exception {
		File fXmlFile = new File(path);
		if (!fXmlFile.exists()) {
			return null;
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();

		Map<String, Object> map = new HashMap<String, Object>();
		Node node = doc.getDocumentElement();
		if (nodeAnalyzer != null) {
			nodeAnalyzer.analyze(node);
		}
		map.put(node.getNodeName(), constructMap(node, nodeAnalyzer));
		return map;
	}

	/**
	 * @param path
	 * @param nodeAnalyzer
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(String path, NodeAnalyzer nodeAnalyzer, String node) throws Exception {
		Map<String, Object> map = parse(path, nodeAnalyzer);
		map = findNode(map, node);
		return map;
	}

	/**
	 * @param is
	 * @param nodeAnalyzer
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(InputStream is, NodeAnalyzer nodeAnalyzer, String node) throws Exception {
		Map<String, Object> map = parse(is, nodeAnalyzer);
		map = findNode(map, node);
		return map;
	}

	/**
	 * @param path
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(String path, String node) throws Exception {
		Map<String, Object> map = parse(path, (NodeAnalyzer) null);
		map = findNode(map, node);
		return map;
	}

	/**
	 * @param is
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parse(InputStream is, String node) throws Exception {
		Map<String, Object> map = parse(is, (NodeAnalyzer) null);
		map = findNode(map, node);
		return map;
	}

	/**
	 * @param map
	 * @param node
	 * @return
	 */
	@SuppressWarnings("serial")
	private static Map<String, Object> findNode(Map<String, Object> map, String node) {
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().equals(node)) {
				return new HashMap<String, Object>() {{
					put(entry.getKey(), entry.getValue());
				}};
			}
			Object value = entry.getValue();
			if (value instanceof Map<?, ?>) {
				Map<String, Object> toReturn = findNode((Map<String, Object>) value, node);
				if (toReturn != null) {
					return toReturn;
				}
			}
		}
		return null;
	}
}
