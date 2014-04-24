package com.bjut.ailib.collector.extractor;

import java.util.List;

import com.bjut.ailib.collector.datamodel.CrawlSequence;
import com.bjut.ailib.collector.datamodel.Frontier;

public abstract class Extractor {
	
	/**
	 * 指向当前解析器共享的控制器爬取队列
	 */
	//protected CrawlSequence sequence;
	
	protected String location;
	
	protected List<String> regexList;

	protected Frontier frontier;
	
	/**
	 * 初始化函数，传入Extractor所共享的爬取队列
	 * @param sequence
	 */
//	public Extractor setSequence(CrawlSequence sequence) {
//		this.sequence = sequence;
//		return this;
//	}
	
	public Extractor setFrontier(Frontier frontier) {
		this.frontier = frontier;
		return this;
	}
	
	public Extractor setLocation(String location) {
		this.location = location;
		return this;
	}
	
	public List<String> getRegexList() {
		return regexList;
	}

	public void setRegexList(List<String> regexList) {
		this.regexList = regexList;
	}
	
	/**
	 * 解析器的解析方法，解析传入url所指向页面的链接，加入爬取队列
	 * @param url
	 */
	public abstract void extract(String url);
}
