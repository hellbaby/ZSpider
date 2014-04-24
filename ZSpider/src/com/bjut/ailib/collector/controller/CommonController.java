package com.bjut.ailib.collector.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjut.ailib.collector.controller.AbstractController.COLLECTOR_STATE;
import com.bjut.ailib.collector.datamodel.FrontierFactory;
import com.bjut.ailib.collector.datamodel.SiteProperty;

/**
 * 爬虫控制器，控制维护爬取子线程
 * @author Edward
 *
 */
public class CommonController extends AbstractController implements Runnable{
	
	/**
	 * 
	 * @param property
	 */
	public CommonController(SiteProperty property) {
		loadProperty(property);		
		String date = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		location = "d:\\collector\\" + property.getName()+date;
		frontier = FrontierFactory.getInstance(this);
		extractor.setFrontier(frontier).setLocation(location);
	}

	public void loadProperty(SiteProperty property) {
		//this.property = property;
		name = property.getName();		
		seedList = property.getSeeds();		
		extractor = property.getExtractor();
		extractor.setRegexList(property.getRegex());
		threadNum = property.getThreadNum();
	}
	
	/**
	 * 实现控制网页爬取策略的方法接口，控制器中的主要方法
	 * @param seedList
	 */
	public void run() {		
		collect();
	}

	public void collect() {
		this.setState(COLLECTOR_STATE.RUNNING);
		System.out.println(name + " Collector running...");
		for (String seed : seedList) {
			frontier.putUrl(seed);
		}

		List<Thread> threadList = new ArrayList<Thread>(threadNum);
//		new SubCollector(this).run();
		
		for (int i = 0; i < threadNum; i++) {			
			//String url = sequence.pop();
			//addThreadNum();
			Thread t = new Thread(new CommonSubCollector(this));
			t.start();
			threadList.add(t);
			// }
		}

		while (threadList.size() > 0) {
			Thread child = threadList.remove(0);
			try {
				child.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		while (frontier.size() > 0 || currentThreadNum > 0) {

			if (currentThreadNum < frontier.size()
					&& currentThreadNum < threadNum) {
				
				String url = frontier.popFirst();
				addThreadNum();
				new Thread(new CommonSubCollector(url, this)).start();
			}
		}

		this.setState(COLLECTOR_STATE.FINISHED);
		
		System.out.println("task "+name+" finished");
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
	}

}