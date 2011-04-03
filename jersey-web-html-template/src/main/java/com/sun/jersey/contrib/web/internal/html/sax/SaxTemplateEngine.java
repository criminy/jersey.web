package com.sun.jersey.contrib.web.internal.html.sax;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.sun.jersey.contrib.web.TemplateEngine;
import com.sun.jersey.contrib.web.internal.html.model.DocumentStatement;
import com.sun.jersey.contrib.web.internal.ns.NamespaceDatabase;

public class SaxTemplateEngine implements TemplateEngine {

	private SAXParser saxParser;
	private NamespaceDatabase namespaceDb;
	private Map<String,DocumentStatement> simpleCache;
	
	public SaxTemplateEngine()
	{
		namespaceDb = new NamespaceDatabase();
		simpleCache = new ConcurrentHashMap<String, DocumentStatement>();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}
	
	@Override
	public void renderTemplate(String filename,Writer wr) throws IOException
	{
		DocumentStatement ds = getDocument(filename);
		ds.run(wr);
	}
	
	protected DocumentStatement getDocument(String filename) throws IOException
	{
		if(simpleCache.containsKey(filename))
		{
			return simpleCache.get(filename);
		}
		HtmlStatementSaxParser parser = new HtmlStatementSaxParser(namespaceDb);
		InputStream is = ClassLoader.getSystemResourceAsStream(filename);
		
		try {
			saxParser.parse(is, parser);
		} catch (SAXException e) {
			throw new IOException(e);
		}
		return parser.getParsedDocument();

	}
	
}
