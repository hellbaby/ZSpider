package com.bjut.ailib.collector.controller;

import java.util.List;

import com.bjut.ailib.collector.datamodel.Frontier;
import com.bjut.ailib.collector.extractor.Extractor;

/**
 * 爬虫控制器基类，控制维护爬取子线程
 * @author Edward
 *
 */
public abstract class AbstractController implements Controller{
	
	//protected SiteProperty property;
	
	protected String name;
	
	protected int threadNum;
	
	protected int currentThreadNum = 0;
	
	protected Extractor extractor;
	
	protected enum COLLECTOR_STATE {WAITING, RUNNING, PAUSED, FINISHED};
	
	protected COLLECTOR_STATE currentState = COLLECTOR_STATE.WAITING;
	
	/**
	 * 对爬取队列CrawlSequence的引用
	 */
	//protected CrawlSequence sequence;
	
	protected Frontier frontier;
	
	protected List<String> seedList;
	
	protected String location;
	
	public String getLocation() {
		return location;
	}
	
	public Frontier getFrontier() {
		return frontier;
	}
	/**
	 * 设置爬虫控制器实例的最大线程数，缺省值为15
	 * @param threadNum
	 * @return
	 */
/*	public Controller setThreadNum(int threadNum) {
		this.threadNum = threadNum;
		return this;
	}
*/	
	public Extractor getExtractor() {
		return extractor;
	}
	
	public String getName() {
		return name;
	}
	
/*	public SiteProperty getProperty() {
		return property;
	}

	public void setProperty(SiteProperty property) {
		this.property = property;
	}
	*/
	/**
	 * 增加一个线程数
	 */
	public synchronized void addThreadNum() {
		currentThreadNum ++;
		//System.out.println("threadNum added : " + threadNum);
	}
	
	/**
	 * 减少一个线程数
	 */
	public synchronized void delThreadNum() {
		currentThreadNum --;
		//System.out.println("threadNum reduced : " + threadNum);
	}
	
	/** 
	 * 设置当前爬取状态，有WAITING, RUNNING, PAUSED, FINISHED状态
	 * @param state
	 */
	public void setState(COLLECTOR_STATE state) {
		currentState = state;
	}
		
	/**
	 * 控制网页爬取策略的方法接口，控制器中的主要方法
	 */
	public abstract void collect();
}