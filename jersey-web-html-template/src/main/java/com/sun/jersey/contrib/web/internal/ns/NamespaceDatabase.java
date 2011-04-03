package com.sun.jersey.contrib.web.internal.ns;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scannotation.AnnotationDB;
import org.scannotation.AnnotationDB.CrossReferenceException;
import org.scannotation.ClasspathUrlFinder;

import com.sun.jersey.contrib.web.AttributeStatement;
import com.sun.jersey.contrib.web.Statement;
import com.sun.jersey.contrib.web.TagHandler;
import com.sun.jersey.contrib.web.ns.packageInfo;

/**
 * The internal database which stores namespace information and lookups.
 * 
 * @author sheenobu
 * 
 */
public class NamespaceDatabase {

	List<String> prefixes;
	Map<String, Class<? extends Statement>> tags;

	protected Map<String, Class<? extends Statement>> _createTags() {
		return new HashMap<String, Class<? extends Statement>>();
	}

	protected List<String> _createPrefixes() {
		return new ArrayList<String>();
	}

	@SuppressWarnings("unchecked")
	public NamespaceDatabase() {
		this.prefixes = _createPrefixes();
		this.tags = _createTags();

		// scan classpath
		AnnotationDB db = new AnnotationDB();
		URL url = ClasspathUrlFinder.findClassBase(packageInfo.class);
		db.setScanClassAnnotations(true);
		db.setScanPackages(new String[] { "com.sun.jersey.contrib.web.ns" });
		try {
			db.scanArchives(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			db.crossReferenceMetaAnnotations();
		} catch (CrossReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Object name : db.getAnnotationIndex().get(
				TagHandler.class.getName())) {
			Class<? extends Statement> tagclazz = null;
			try {
				tagclazz = (Class<? extends Statement>) Class.forName(name.toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TagHandler t = (TagHandler) tagclazz.getAnnotation(TagHandler.class);
			
			tags.put(t.prefix() + ":" + t.name(),
				tagclazz);
		}
	}

	public List<String> getPrefixes() {
		return prefixes;
	}

	public Map<String, Class<? extends Statement>> getTags() {
		return tags;
	}

	public Statement getTag(String fullTagName, Statement parent,
			Map<String, AttributeStatement> attributes) {
		Class<?> tagclass = tags.get(fullTagName);
		if (tagclass == null)
			return null;

		Constructor<?> c;
		try {
			c = tagclass.getConstructor(Statement.class, Map.class);
			return (Statement) c.newInstance(parent, attributes);
		} catch (Exception exn) {
			exn.printStackTrace();
			return null;
		}
	}

}
