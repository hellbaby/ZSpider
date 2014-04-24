package com.bjut.ailib.collector.controller;

import com.bjut.ailib.collector.datamodel.Frontier;

/**
 * 爬取子线程类
 * @author Edward
 *
 */
public class CommonSubCollector implements SubCollector, Runnable{

	private String url;
	
	private AbstractController controller;
	
	private Frontier frontier;
	
	public CommonSubCollector (AbstractController controller) {
		this.frontier = controller.getFrontier();
		this.controller = controller;
	}
	
	public CommonSubCollector (String url, AbstractController controller) {
		this.url = url;
		this.controller = controller;
	}
	
	@Override
	public void run() {
		controller.getExtractor().extract(url);
		controller.delThreadNum();
//		while (true) {
//			if (frontier.size() > 0) {
//				controller.getExtractor().extract(frontier.popFirst());
//			}
//			else {				
//				if (!sequence.isFinished()) {
//					if (sequence.isClearing() && sequence.isClearing()) {
//						sequence.readFile();
//					}
//				}
//				else {
//					System.out.println("---subcollector finished----");
//					return;
//				}
//			}
//		}
	}
	
	public static void main(String[] args) {
		
		//SubCollector sub = new SubCollector("http://www.cnki.com.cn/Navi/A-A001.htm", new ExtractorCnki(CrawlSequenceFactory.getInstance(controller)));
		//new Thread(sub).start();		
	}

}
