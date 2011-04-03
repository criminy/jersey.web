package com.sun.jersey.internal.web.html.parser;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sun.jersey.contrib.web.TemplateEngine;
import com.sun.jersey.contrib.web.internal.html.sax.SaxTemplateEngine;

public class HtmlStatementSaxParserTest {
	
	private static TemplateEngine templateContext;
	
	@BeforeClass
	public static void startParsers() throws ParserConfigurationException, SAXException
	{
		templateContext = new SaxTemplateEngine();
	}
	
	@Test
	public void testValidParsing() throws IOException, ParserConfigurationException, SAXException
	{		
		Writer wr = new StringWriter();
		templateContext.renderTemplate("test.xhtml1.html", wr);
		wr.flush();
		System.out.println(wr.toString());
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidParsing() throws SAXException, IOException
	{
		Writer wr = new StringWriter();
		templateContext.renderTemplate("test.invalid.html", wr);
		wr.flush();
		System.out.println(wr.toString());
	}
}
