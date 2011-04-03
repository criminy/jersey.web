package com.sun.jersey.contrib.web.internal.html.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;

/**
 * Represents a statement which writes it's given
 * input to the writer.
 * 
 * @author sheenobu
 *
 */
public class EchoStatement extends Statement{

	private String data;
	
	public EchoStatement(Statement parent,String data) {
		super(parent,new HashMap<String,AttributeStatement>());
		this.data = data;
	}
	
	@Override
	public void run(Writer wr ) throws IOException {
		wr.write(data);
	}

}
