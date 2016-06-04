package imf.data;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataParser
{	
	private Element root;
	private List<DataObject> datas = new ArrayList<DataObject>();
	
	public DataParser(String name, int options)
	{
		try {
			File file = new File(name);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			
			root = doc.getDocumentElement();
			appendNode(doc.getElementsByTagName("me"));
			appendNode(doc.getElementsByTagName("static"));
			appendNode(doc.getElementsByTagName("trigger"));
			
		} catch (ParserConfigurationException e) {
			return;
		} catch (SAXException | IOException e) {
			return;
		}
	}
	
	private void appendNode(NodeList nodes)
	{
		for (int i = 0; i < nodes.getLength(); ++i)
			if (nodes.item(i).getParentNode() == root)
				datas.add(getNodeData(new DataObject(nodes.item(i).getNodeName()), nodes.item(i)));
	}
	
	private DataObject getNodeData(DataObject data, Node node)
	{
		NamedNodeMap attrs = node.getAttributes();

		for (int i = 0; i < attrs.getLength(); ++i)
			data.insert(attrs.item(i).getNodeName(), attrs.item(i).getNodeValue());
		
		if (data.ID.equals("trigger"))
		{
			for (Node child = node.getFirstChild(); child != node.getLastChild(); child = child.getNextSibling())
			{
				if(child.getTextContent().equals("\n\t\t"))
					continue;
				DataObject childData = getNodeData(new DataObject(child.getNodeName(), data), child);
				data.insertChild(childData);
			}
		}
		return data;
	}
	
	public DataObject get(String key)
	{
		for (DataObject o : datas)
			if (o.ID.equals(key))
				return o;
		return null;
	}
	
	public void loop(Consumer<DataObject> func)
	{
		for(DataObject oi : datas)
			func.accept(oi);
	}
}
