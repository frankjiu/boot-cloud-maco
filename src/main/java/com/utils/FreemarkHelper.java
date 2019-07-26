package com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkHelper {

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static String process(String templateString, Map values) {
		Template t = null;
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		try {
			StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
			stringTemplateLoader.putTemplate("template", templateString);
			configuration.setTemplateLoader(stringTemplateLoader);
			t = configuration.getTemplate("template");
			StringWriter writer = new StringWriter();
			// t.process(values, writer);
			t.process(values, writer);
			return writer.toString();

		} catch (Exception e) {
			return templateString;
		}
	}

	/**
	 * by mao
	 * HTML
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void process(String templateString, Map values, String fileurl) {
		Template t = null;
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		try {
			StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
			stringTemplateLoader.putTemplate("template", templateString);
			Template template = configuration.getTemplate(templateString);
			System.out.println("===========template==>>" + template.getName());
			configuration.setTemplateLoader(stringTemplateLoader);
			t = configuration.getTemplate("template");
			System.out.println("=========>>");
			File file = new File(fileurl);
			Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			t.process(values, out);
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 生成覆盖
	@SuppressWarnings("deprecation")
	public static void generateHtml(String ftlName, String htmlPath, String templatePath, Map<String, Object> data) {
		try {
			Configuration configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new java.io.File(templatePath));
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate(ftlName);
			Writer writer = new OutputStreamWriter(new FileOutputStream(htmlPath), "UTF-8");
			template.process(data, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String aa="dfdsf${aaa}dsfdsf";
		// Map m=new HashMap();
		// m.put("aaa", "ccc");
		// FreemarkHelper a=null;
		// System.out.println("dd"+FreemarkHelper.process(aa, m));
		// getClass().getResource("").getPath();

		FreemarkHelper.process("shopHeaderNav.ftl", null, "d:/tmp.html");
	}

}
