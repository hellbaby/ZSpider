package com.bjut.ailib.collector.extractor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bjut.ailib.collector.datamodel.Item;

public abstract class CustomizedExtractor {
	
	protected CustomizedExtractor parrentExtractor;
	protected String regex;
	protected List<Item> seedList = Collections.synchronizedList(new LinkedList<Item>());
	protected List<Item> resultList = Collections.synchronizedList(new LinkedList<Item>());
	protected Item thisItem;
	protected Item parentItem;
	protected boolean isUpdate = false;
	
	/** 
	 * 用于匹配链接标签<a href="xxx"> XXX <的正则表达式
	 */
	protected static final String PATTERN_A_HREF = 
			"<a\\s.*?href=\"([^\"]+)\"[^>]*>([^<]*)<";
	
	public CustomizedExtractor getParrentExtractor() {
		return parrentExtractor;
	}

	public void setParrentExtractor(CustomizedExtractor parrentExtractor) {
		this.parrentExtractor = parrentExtractor;
	}
	
	public Item getThisItem() {
		return thisItem;
	}

	public void setThisItem(Item thisItem) {
		this.thisItem = thisItem;
	}

	public CustomizedExtractor() {
		
	}
	
	public CustomizedExtractor(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public CustomizedExtractor(CustomizedExtractor extractor) {
		this.parrentExtractor = extractor;
		seedList = parrentExtractor.getResultList();
		isUpdate = parrentExtractor.isUpdate();
	}
	
	public boolean isUpdate() {
		return isUpdate;
	}
	
//	public CustomizedExtractor(List<String> seedList) {
//		this.seedList = seedList;
//	}	
	
	public abstract void extract();
	
	//public abstract void extract(CustomizedExtractor parrentExtractor);	
	
	public static boolean customMatcher(String url, List<String> regexList) {
		boolean matches = false;
		for (String regex : regexList) {
			matches = matches | url.matches(regex);
			if (matches) {
				break;
			}
		}
		return matches;
	}
	
	public List<Item> getResultList() {
		return resultList;
	}


}
