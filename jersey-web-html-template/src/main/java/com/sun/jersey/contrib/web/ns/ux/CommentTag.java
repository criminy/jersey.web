package com.sun.jersey.contrib.web.ns.ux;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.TagHandler;

/**
 * A statement which does nothing, useful for comments.
 * @author sheenobu
 *
 */
@TagHandler(name="comment",prefix="ux")
public class CommentTag extends Statement
{
	public CommentTag(Statement parent,Map<String, AttributeStatement> attributes) {
		super(parent,attributes);
	}

	@Override
	public void run(Writer wr) throws IOException {
		
	}
}