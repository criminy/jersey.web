package com.sun.jersey.spi.web.test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class node_iterator implements Iterable<Node>,Iterator<Node>
{
	
	public node_iterator(NodeList nl) {
		this.nodeList = nl;
		idx = 0;
		current = nl.item(idx);
	}

	NodeList nodeList;
	Node current = null;
	int idx;
	
	@Override
	public boolean hasNext() {
		return nodeList.item(idx+1) != null;
	}

	@Override
	public Node next() {
		return current = nodeList.item(++idx);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Node> iterator() {
		return new node_iterator(nodeList);
	}
	
}

public class XHtmlParsingTest {


	
	private static Iterable<Node> iterator(NodeList nl)
	{
		return new node_iterator(nl);
	}
	
	public static void writeXmlFile(Document doc) {
	    try {
	        // Prepare the DOM document for writing
	        Source source = new DOMSource(doc);

	        // Prepare the output file	        
	        Result result = new StreamResult(System.out);

	        // Write the DOM document to the file
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(source, result);
	    } catch (TransformerConfigurationException e) {
	    } catch (TransformerException e) {
	    }
	}

	
	@Test
	public void test() throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(ClassLoader
				.getSystemResourceAsStream("test.html"));

		//doc.getDocumentElement().normalize();
		process(doc.getDocumentElement());
		writeXmlFile(doc);
	}

	public void process(Node n)
	{		
		if(n.getNodeName().matches(".+:.+"))
		{
			System.out.println("processing " + n.getNodeName());
			//n.getParentNode().removeChild(n);
		}
		for(Node e : iterator(n.getChildNodes()))
		{
			process(e);
		}
	}
	
}
