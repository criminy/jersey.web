package com.sun.jersey.contrib.web.ns.ux;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.TagHandler;
import com.sun.jersey.contrib.web.internal.html.model.ConstantAttribute;


@TagHandler(name="attribute",prefix="ux")
public class AttributeTag extends Statement{
	
	private final int whitespaceFormatting;
	
	public AttributeTag(Statement parent, Map<String, AttributeStatement> attributes) {
		super(parent, attributes);

		if(attributes.containsKey("whitespace"))
		{
			String whitespaceVal = attributes.get("whitespace").getConstant();
			if(whitespaceVal.equals("expand"))
			{
				whitespaceFormatting = 0;
			}else
			if(whitespaceVal.equals("single-space"))
			{
				whitespaceFormatting = 1;
			}else
			if(whitespaceVal.equals("compact"))
			{
				whitespaceFormatting = 3;
			}else{
				whitespaceFormatting = 3;
			}						
		}else{
			whitespaceFormatting = 3;
		}
		

		if(this.getAttributes().containsKey("value"))
		{
			parent.getAttributes().put(
					this.getAttributes().get("name").getConstant(),
					new ConstantAttribute(null, 
						this.getAttributes().get("name").getConstant(),
						this.getAttributes().get("value").getConstant()));									
		}else{
			parent.getAttributes().put(
				this.getAttributes().get("name").getConstant(),
				new AttributeStatement(this,this.getAttributes().get("name").getConstant()) {
					
					@Override
					public void run(Writer wr) throws IOException {
						wr.write(this.getName() + "='");
						StringWriter strwr = new StringWriter();
						for(Statement stmt : this.getParent().getChildren())
							stmt.run(strwr);
						
						//TODO: use a standard attribute normalization method
						if(whitespaceFormatting == 3)
						{
							wr.write(strwr.toString().trim().replaceAll("\n","").replaceAll("\\s+","") + "'");
						}else if(whitespaceFormatting == 0)
						{
							wr.write(strwr.toString().trim().replaceAll("\n","") + "'");
						}else if(whitespaceFormatting==1)
						{
							wr.write(strwr.toString().trim().replaceAll("\n","").replaceAll("\\s+"," ") + "'");
						}
					}
					
					@Override
					public String getConstant() {
						return "s";
					}
				});
		}
	}

	@Override
	public void run(Writer wr) throws IOException {
		
	}
	
}
