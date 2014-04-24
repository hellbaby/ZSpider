package com.bjut.ailib.collector.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.bjut.ailib.collector.datamodel.SiteProperty;
import com.bjut.ailib.collector.extractor.Extractor;

public class SitePropertyLoader {
	
	public static List<SiteProperty> init() {
		SAXBuilder sb=new SAXBuilder(); 
	    Document doc = null;
		try {
			doc = sb.build("src/sites.xml");
		} catch (JDOMException e4) {
			e4.printStackTrace();
		} catch (IOException e4) {
			e4.printStackTrace();
		} 
	    Element root=doc.getRootElement();
	    List<Element> siteList=root.getChildren("site");
	    List<SiteProperty> siteProperties = new LinkedList<SiteProperty>();
	    for (Element e: siteList) {
	    	SiteProperty property = new SiteProperty();
	    	property.setName(getAttribute(e, "name"));//获取网站名称属性
	    	property.setSitemap( Boolean.parseBoolean(getAttribute(e, "sitemap")) );//获取网站地图属性
	    	property.setThreadNum(Integer.parseInt(getAttribute(e, "threadNum")));
	    	//获取解析器属性并用类加载器实例化
	    	String extractorName = getAttribute(e, "extractor");
	    	try {
				Class clazz = Class.forName("com.zxc.collector.extractor."+extractorName);
				Extractor extractor = (Extractor)clazz.newInstance();
				
				property.setExtractor(extractor);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e2) {
				e2.printStackTrace();
			} catch (IllegalAccessException e3) {
				e3.printStackTrace();
			}
	    	
//	    	List<Element> seedNodes = e.getChildren("seed");//获取seed属性
//	    	List<String> seeds = new LinkedList<String>();
//	    	for (Element seedNode: seedNodes) {	    		
//	    		seeds.add(seedNode.getAttribute("url").getValue());
//	    	}	    	
	    	property.setSeeds(getChildrenAttributes(e, "seed", "url"));
	    	List<String> regex = getChildrenAttributes(e, "regex", "pattern");
	    	if (regex.isEmpty()) {
	    		regex.add(getDefault("pattern"));
	    	}
	    	property.setRegex(regex);
	    	System.out.println(property);
	    	siteProperties.add(property);
	    }
	    return siteProperties;
	}
	
	public static String getAttribute(Element e, String name) {
		String value = null;
		Attribute attribute = e.getAttribute(name);
		if (attribute == null) {
			value = getDefault(name);
		} 
		else {
			value = attribute.getValue();
		}			
		return value;
	}
	
	public static List<String> getChildrenAttributes(Element e, String nodeName, String AttrName) {
		List<Element> children = e.getChildren(nodeName);//获取seed属性
    	List<String> attributes = new LinkedList<String>();
    	
    	for (Element node: children) {	    		
    		//attributes.add(node.getAttribute(AttrName).getValue());
    		attributes.add(getAttribute(node, AttrName));
    	}
		return attributes;
	}

	public static String getDefault(String name) {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(
					"src/defaultSiteConfig.properties"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return props.getProperty(name);
	}
	
	public static void main(String[] args) {
		//System.out.println(Integer.parseInt(getDefault("threadNum")));
		System.out.println(getDefault("pattern"));
		//init();
	}
}
