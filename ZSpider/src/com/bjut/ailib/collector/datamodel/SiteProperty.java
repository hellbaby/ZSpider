package com.bjut.ailib.collector.datamodel;

import java.util.List;

import com.bjut.ailib.collector.extractor.Extractor;

public class SiteProperty {

	private String name;
	
	private Extractor extractor;
	
	private List<String> regex;
	
	private boolean sitemap = false;
	
	private int threadNum;
	
	private List<String> seeds;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	
	public Extractor getExtractor() {
		return extractor;
	}
	public void setExtractor(Extractor extractor) {
		this.extractor = extractor;
	}	
	
	public List<String> getRegex() {
		return regex;
	}
	public void setRegex(List<String> regex) {
		this.regex = regex;
	}
	
	public boolean getSitemap() {
		return sitemap;
	}
	
	public void setSitemap(boolean sitemap) {
		this.sitemap = sitemap;
	}
	
	public List<String> getSeeds() {
		return seeds;
	}
	public void setSeeds(List<String> seeds) {
		this.seeds = seeds;
	}
	
	@Override
	public String toString() {
		return "===========SiteProperty=========="
				+ "\nname: " + name
				+ "\nsitemap: " + sitemap
				+ "\nextractor: " + extractor.getClass().getName()
				+ "\nseeds: " + seeds
				+ "\nregexList: " + regex;
	}
	
}
