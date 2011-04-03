package com.sun.jersey.spi.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

abstract class Segment {
	List<Segment> children = new ArrayList<Segment>();

	public void addChild(Segment child) {
		children.add(child);
	}

	public List<Segment> getChildren() {
		return children;
	}

	public abstract void runSegment();
}

class EchoSegment extends Segment {
	private String segmentValue;

	public EchoSegment(String str) {
		this.segmentValue = str;
	}

	@Override
	public void runSegment() {
		System.out.println(segmentValue);
	}
}

class WritingHandler extends DefaultHandler {
	public WritingHandler(Writer wr) {
		this.writer = wr;
	}

	private Writer writer;

	public Writer getWriter() {
		return writer;
	}

	protected void write(char c) throws SAXException {
		try {
			this.writer.write(c);
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	protected void write(String str) throws SAXException {
		try {
			this.writer.write(str);
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}
}

class Jxhandler extends WritingHandler {
	CoreTemplateParser parser;

	public Jxhandler(CoreTemplateParser parser, Writer wr) {
		super(wr);
		this.parser = parser;
	}
}

class EchoHandler extends WritingHandler {
	CoreTemplateParser parser;

	public EchoHandler(CoreTemplateParser parser, Writer wr) {
		super(wr);
		this.parser = parser;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (!parser.hasWrittenEndChar()) {
			write(">");
			parser.eventWrittenEndChar();
		}

		for (int i = 0; i != length; i++)
			write(ch[i + start]);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (!parser.hasWrittenEndChar()) {
			write("/>");
			parser.eventWrittenEndChar();
		} else {
			write("</" + qName + ">");
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		{// render beginning of element

			StringBuffer buffer = new StringBuffer();
			buffer.append("<");
			buffer.append(qName);
			for (int i = 0; i != atts.getLength(); i++) {
				buffer.append(" ");
				buffer.append(atts.getQName(i));
				buffer.append("=\"");
				buffer.append(atts.getValue(i));
				buffer.append("\" ");
			}
			write(buffer.toString().trim());
		}
	}

}

class CoreTemplateParser extends WritingHandler {

	private Stack<Boolean> writtenElem = new Stack<Boolean>();

	public boolean hasWrittenEndChar() {
		return writtenElem.peek();
	}

	public void eventWrittenEndChar() {
		writtenElem.pop();
		writtenElem.push(true);
	}
	
	@Override
	public void startDocument() throws SAXException {
		writtenElem.push(true);
	}

	public Stack<Boolean> getWrittenElem() {
		return writtenElem;
	}

	public CoreTemplateParser(Writer wr) {
		super(wr);
	}

	private Stack<DefaultHandler> handlers = new Stack<DefaultHandler>();

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		handlers.peek().characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		handlers.peek().endElement(uri, localName, qName);
		handlers.pop();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		{ // modify rendering stack
			if (!hasWrittenEndChar()) {
				write(">");
				eventWrittenEndChar();
			}

			writtenElem.push(false);
		}

		handlers.push(new EchoHandler(this, getWriter()));
		handlers.peek().startElement(uri, localName, qName, atts);
	}

}

public class CopyOfHtmlParsingTest {

	@Test
	public void test1() throws ParserConfigurationException, SAXException,
			IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		Writer wr = new StringWriter();
		InputStream is = ClassLoader.getSystemResourceAsStream("test.html");
		
		saxParser.parse(is, new CoreTemplateParser(wr));

		System.out.println(wr.toString());
	}

}
