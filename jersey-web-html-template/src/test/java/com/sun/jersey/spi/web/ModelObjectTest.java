package com.sun.jersey.spi.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.internal.html.model.EchoStatement;
import com.sun.jersey.contrib.web.ns.jx.ForEachTag;
import com.sun.jersey.contrib.web.ns.jx.PathTag;




public class ModelObjectTest {


	
	
	@Test
	public void testModel() throws IOException
	{
		/*
		Writer wr = new OutputStreamWriter(System.out);
		
		List<Statement> templates = new ArrayList<Statement>();
		
		Statement top = new EchoStatement(null,
				"<!DOCTYPE html>\n" +
				"<html xmlns:jx=\"A\">\n" +
				"  <head>\n" + 
				"     <title>Insert title here</title>\n" +
				"  </head>\n" +
				"  <body>\n" +
				"     <ul>\n");
		
		Statement foreach = new ForEachTag(4,null);
		{
							
			Statement jxPath = new EchoStatement(foreach,
				"        <li><a>");
				
			Statement path = new PathTag(foreach, "id1");
		
			Statement nl = new EchoStatement(foreach,
			"</a></li>\n");
		
			Statement foreach2 = new ForEachTag(8,null);
			{
				Statement path2 = new PathTag(foreach2, "id2");
				foreach2.getChildren().add(path2);
			}
			foreach.getChildren().add(foreach2);
			foreach.getChildren().add(jxPath);
			foreach.getChildren().add(path);
			
			foreach.getChildren().add(nl);
			
		}
		
		Statement bottom = new EchoStatement(null,
				"    </ul>\n" +
				"  </body>\n"+
				"</html>");		
		
		templates.add(top);
		templates.add(foreach);
		templates.add(bottom);
		
		for(Statement stmt : templates)
		{
			stmt.run(wr);
		}
		
		wr.flush();
		*/
	}
	
}
