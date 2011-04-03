package com.sun.jersey.contrib.web.internal.html.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;

/**
 * Represents the fully parsed document.
 * 
 * @author sheenobu
 *
 */
public class DocumentStatement extends Statement {

	public DocumentStatement() {
		super(null,new HashMap<String,AttributeStatement>());
	}

	private String modeline = "";
	private RenderingModes renderingMode = 
		RenderingModes.HTML4;

	public RenderingModes getRenderingMode() {
		return renderingMode;
	}
	
	public void setMode(String publicId,String systemId)
	{
		if(publicId.equals("-//W3C//DTD XHTML 1.0 Transitional//EN"))
		{
			renderingMode = RenderingModes.XHTML1_0;
		}
		if(publicId.equals("-//W3C//DTD HTML 4.01//EN"))
		{
			renderingMode = RenderingModes.HTML4;
		}
		modeline = String.format("<!DOCTYPE html PUBLIC \"%s\" \"%s\">\n",
			publicId,systemId);
	}
	
	@Override
	public void run(Writer wr) throws IOException {
		wr.write(modeline);
		for(Statement stmt : this.getChildren())
		{
			stmt.run(wr);
		}		
	}
	
}
