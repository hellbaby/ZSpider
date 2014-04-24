package com.bjut.ailib.collector.datamodel;

import java.util.HashMap;
import java.util.Map;

import com.bjut.ailib.collector.controller.AbstractController;

public class CrawlSequenceFactory {
	
	private static Map<AbstractController, CrawlSequence> taskMap = new HashMap<AbstractController, CrawlSequence>();
	
	public static CrawlSequence getInstance(AbstractController controller){
		CrawlSequence sequence;
		if (taskMap.containsKey(controller) ) {
			sequence = taskMap.get(controller);
		}
		else {
			sequence = new CrawlSequence();
			taskMap.put(controller, sequence);
		}
		return sequence;
	}
}
