package com.sun.jersey.contrib.web;

import java.io.IOException;
import java.io.Writer;

public interface TemplateEngine {

	public void renderTemplate(String filename, Writer wr) throws IOException;

}