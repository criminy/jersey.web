package com.sun.jersey.contrib.web;

import java.io.IOException;
import java.io.Writer;

public abstract class AttributeStatement {

	public abstract String getConstant();
	
	public String getName() {
		return name;
	}
	
	public AttributeStatement(Statement parent,String name) {
		this.parent = parent;
		this.name = name;
	}
	
	private String name;	
	private Statement parent;
	
	public Statement getParent() {
		return parent;
	}
	
	public void setParent(Statement parent) {
		this.parent = parent;
	}
	

	
	public abstract void run(Writer wr) throws IOException;

}
