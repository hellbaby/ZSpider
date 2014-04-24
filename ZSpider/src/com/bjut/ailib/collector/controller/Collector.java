package com.bjut.ailib.collector.controller;

import java.util.List;

import com.bjut.ailib.collector.datamodel.SiteProperty;
import com.bjut.ailib.collector.util.SitePropertyLoader;

public class Collector {
	
	private List<SiteProperty> siteProperties;
	
	public Collector() {
		siteProperties = SitePropertyLoader.init();
	}
		
	/**
	 * 爬虫入口类
	 */
	public void start() {
		for (SiteProperty property : siteProperties) {
			new Thread(new CommonController(property)).start();
		}
	}

	public static void main(String[] args) {		
		try {
			new Collector().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
