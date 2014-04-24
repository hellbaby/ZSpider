package com.bjut.ailib.collector.datamodel;

import java.util.HashMap;
import java.util.Map;

import com.bjut.ailib.collector.controller.AbstractController;

public class FrontierFactory {
	
	private static Map<AbstractController, Frontier> taskMap = new HashMap<AbstractController, Frontier>();
	
	public static Frontier getInstance(AbstractController controller){
		Frontier frontier = null;
		if (taskMap.containsKey(controller) ) {
			frontier = taskMap.get(controller);
		}
		else {
			frontier = new MongoFrontier(controller);
			taskMap.put(controller, frontier);
		}
		return frontier;
	}
}
