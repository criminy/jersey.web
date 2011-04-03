package com.sun.jersey.contrib.web.internal.html.sax;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.internal.html.model.ConstantAttribute;
import com.sun.jersey.contrib.web.internal.html.model.DocumentStatement;
import com.sun.jersey.contrib.web.internal.html.model.EchoStatement;
import com.sun.jersey.contrib.web.internal.html.model.HtmlStatement;
import com.sun.jersey.contrib.web.internal.html.model.RenderingModes;
import com.sun.jersey.contrib.web.internal.html.model.XhtmlStatement;
import com.sun.jersey.contrib.web.internal.ns.NamespaceDatabase;

/**
 * Sax parser which parses out HTML and (X)HTML
 * into a executable statement tree.
 * 
 * @author sheenobu
 *
 */
public class HtmlStatementSaxParser extends DefaultHandler {

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException {
		parent.setMode(publicId, systemId);

		return new InputSource(
				new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
	}
	
	private Logger log = Logger.getLogger(HtmlStatementSaxParser.class);
	private Statement statement;
	private DocumentStatement parent;
	
	@Override
	public void startDocument() throws SAXException {
		log.debug("starting document");
		
		statement = parent = new DocumentStatement();
	}
	
	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		log.warn(name);
	}
	
	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		log.warn(name);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		StringBuffer buffer = new StringBuffer(length);
		
		for(int i = 0; i!=length;i++)
			buffer.append(ch[i+start]);
		
		Statement echoStatement = new EchoStatement(statement,
				buffer.toString());
		
		statement.getChildren().add(echoStatement);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		log.debug("Startelement " + qName);
		
		Statement newStmt = null;
		
		if(SaxParserUtil.isHtmlElement(uri, localName, qName, attributes))
		{
			log.debug(" Adding HTML element ");
			if(this.getParsedDocument().getRenderingMode().equals(
				RenderingModes.HTML4))
					newStmt = new HtmlStatement(statement, qName,convertAttributes(attributes));
			else
					newStmt = new XhtmlStatement(statement, qName,convertAttributes(attributes));
	
		}else{
			
			newStmt = db.getTag(qName, statement, 
				convertAttributes(attributes));
			if(newStmt == null)
				throw new RuntimeException("Can't find tag " + qName);
		}
		
		if(newStmt != null) {
			statement.getChildren().add(newStmt);
			statement = newStmt;
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(statement != null && statement.getParent() != null )
			statement = statement.getParent();
	}
	
	@Override
	public void endDocument() throws SAXException {
		log.debug("Document finished");
	}
	
	public DocumentStatement getParsedDocument()
	{
		return this.parent;
	}
	
	protected Map<String,AttributeStatement> convertAttributes(Attributes attr)
	{
		Map<String, AttributeStatement> ret = createAttributeMap();
		
		for(int i = 0; i != attr.getLength();i++)
		{
			ret.put(attr.getQName(i),
					new ConstantAttribute(null, attr.getQName(i), attr.getValue(i)));		
		}
		
		return ret;
	}

	private Map<String, AttributeStatement> createAttributeMap() {
		return new HashMap<String, AttributeStatement>();
	}
	
	public HtmlStatementSaxParser(NamespaceDatabase db) {
		this.db = db;
	}

	private NamespaceDatabase db;

}



class SaxParserUtil
{
	public static boolean isHtmlElement(String uri,String localName,String qName,
			Attributes attributes)
	{
		return qName.matches("[a-zA-Z]+");
	}
}
