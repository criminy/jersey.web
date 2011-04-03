package com.sun.jersey.contrib.web;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a chunk of templating code.
 * 
 * @author sheenobu
 *
 */
public abstract class Statement 
{
	public Statement(Statement parent, Map<String,AttributeStatement> attributes) {
		this.parent = parent;
		this.attributes = attributes;
	}
	
	private Map<String,AttributeStatement> attributes;
	private List<Statement> children = new ArrayList<Statement>();
	private Statement parent;
	
	public Statement getParent() {
		return parent;
	}
	
	public void setParent(Statement parent) {
		this.parent = parent;
	}
	
	public List<Statement> getChildren() {
		return children;
	}
	
	public abstract void run(Writer wr) throws IOException;

	
	public Map<String, AttributeStatement> getAttributes() {
		return attributes;
	}
	
}

