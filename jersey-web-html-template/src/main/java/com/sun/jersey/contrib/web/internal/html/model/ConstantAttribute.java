package com.sun.jersey.contrib.web.internal.html.model;

import java.io.IOException;
import java.io.Writer;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;

public class ConstantAttribute extends AttributeStatement {

	
	private String value;
	
	public ConstantAttribute(Statement parent, String name,String value) {
		super(parent, name);
		this.value = value;
	}

	
	@Override
	public String getConstant() {
		return value;
	}
	
	@Override
	public void run(Writer wr) throws IOException {
		wr.write(String.format("%s='%s'",this.getName(),value));
	}

}
