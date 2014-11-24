import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class testReadXML {

	public static Map<String, String> readConfig(String fileName) {
		
		Map<String, String> configMap = new HashMap<String, String>();
		
		try {

			InputStream input = new FileInputStream(new File(fileName));

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(input);

			Element element = (Element) document.getDocumentElement();

			if (element.getNodeName().equals("configuration")) {
				NodeList propertyNodes = element
						.getElementsByTagName("property");

				for (int i = 0; i < propertyNodes.getLength(); i++) {

					// property parse
					Element propertyElement = (Element) propertyNodes.item(i);

					// System.out.println(propertyElement.getTextContent());
					System.out.flush();

					NodeList nameList = propertyElement
							.getElementsByTagName("name");
					Element nameElement = null;
					// System.out.println(nameList.getLength());
					if (nameList.getLength() != 1) {
						
						System.out.println("cofig file error");
						
						continue;
					} else {
						nameElement = (Element) nameList.item(0);
						/*
						System.out.println(nameElement.getNodeName()
								+ ".getNodeValue()" + "="
								+ nameElement.getNodeValue());
						System.out.println(nameElement.getNodeName()
								+ ".getTextContent()" + "="
								+ nameElement.getTextContent());
								*/
					}

					NodeList valueList = propertyElement
							.getElementsByTagName("value");
					Element valueElement = null;

					if (valueList.getLength() != 1) {
						System.out.println("cofig error");
						continue;
					} else {
						valueElement = (Element) valueList.item(0);
						/*
						System.out.println(valueElement.getNodeName()
								+ ".getNodeValue()" + "="
								+ valueElement.getNodeValue());
						System.out.println(valueElement.getNodeName()
								+ ".getTextContent()" + "="
								+ valueElement.getTextContent());
								*/
					}

					configMap.put(nameElement.getTextContent(),
							valueElement.getTextContent());

				}
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configMap;
	}

	public static void main(String[] argvs) {
		try {

			Map<String,String> configMap=readConfig("config.xml");
			
			String value=configMap.get("hello");
			System.out.println(value);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
