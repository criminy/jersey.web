package com.sun.jersey.contrib.web.ns.jx;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.TagHandler;

/**
 * Represents a POJO lookup path, most usually jxpath,
 * that returns a value.
 * @author sheenobu
 *
 */
@TagHandler(name="path",prefix="jx")
public class PathTag extends Statement
{
	private String variable;
	
	public PathTag(Statement parent, Map<String,AttributeStatement> attributes) {
		super(parent,attributes);
		this.variable = attributes.get("path").getConstant();
	}

	@Override
	public void run(Writer wr) throws IOException {
		wr.write(variable);
	}
}
