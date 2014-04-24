package com.bjut.ailib.collector.datamodel;

public interface Frontier {
	public String popFirst();

	public boolean putUrl(String url);
	// public boolean visited(CrawlUrl url);
	
	
	public long size();
}
