package com.sun.jersey.contrib.web.internal.html.model;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;

/**
 * Represents a standard HTML tag and it's attributes.
 * 
 * @author sheenobu
 *
 */
public class HtmlStatement extends Statement
{
	private String name;
	
	public HtmlStatement(Statement parent,String name,
			Map<String,AttributeStatement> attributes)
	{
		super(parent,attributes);
		this.name = name;	
	}
	
	@Override
	public void run(Writer wr) throws IOException {
		
		
		if(this.getAttributes().size() == 0)
		{
			wr.write("<" + name + ">");
		}else{
		
			wr.write("<" + name + " ");
			
			
			int i = 0;
			int size = this.getAttributes().size()-1;
			for(String key : this.getAttributes().keySet())
			{
				this.getAttributes().get(key).run(wr);						
				if(i != size)
					wr.write(" ");
				i++;
			}
			wr.write(">");
		}
			
		if(this.getChildren().size() != 0)
		{

			for(Statement stmt : this.getChildren())
			{
				stmt.run(wr);
			}
			
			if(!name.equalsIgnoreCase("meta"))
			{
				wr.write("</" + name + ">");	
			}
		}else{
			
			if(!name.equalsIgnoreCase("meta"))
			{
				wr.write("/>");	
			}
		}
		
	}
}