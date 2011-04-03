package com.sun.jersey.contrib.web.ns.jx;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.TagHandler;

/**
 * Represents a for-each blog, which repeats it's child
 * data over an iterator.
 * 
 * @author sheenobu
 *
 */
@TagHandler(name="foreach",prefix="jx")
public class ForEachTag extends Statement
{
	
	private int count;

	public ForEachTag(Statement parent, Map<String,AttributeStatement> attributes) {
		super(parent,attributes);
		this.count = Integer.parseInt(attributes.get("count").getConstant());
	}

	@Override
	public void run(Writer wr) throws IOException {
		for(int i = 0; i!=count; i++)
			for(Statement stmt : this.getChildren())
			{
				stmt.run(wr);
			}
	}

}