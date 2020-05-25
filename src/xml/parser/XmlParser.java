package xml.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("unchecked")
public class XmlParser {

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

	private static Object constructMap(Node node) {
		NodeList list = node.getChildNodes();
		if (list != null && list.getLength() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			boolean found = false;
			for (int i = 0; i < list.getLength(); ++i) {
				if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					found = true;
					map.put(list.item(i).getNodeName(), constructMap(list.item(i)));
				}
			}
			if (found)
				return map;
		}
		return node.getTextContent();
	}

	public static Map<String, Object> parse(String path) throws SAXException, IOException, ParserConfigurationException {
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
		map.put(node.getNodeName(), constructMap(node));
		return map;
	}
	
	public static Map<String, Object> parse(String path, String node) throws SAXException, IOException, ParserConfigurationException {
		Map<String, Object> map = parse(path);
		map = findNode(map, node);
		return map;
	}
	
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
