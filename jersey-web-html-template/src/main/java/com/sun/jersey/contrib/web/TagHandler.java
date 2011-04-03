package com.sun.jersey.contrib.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TagHandler {

	public String name();
	public String prefix();
}
