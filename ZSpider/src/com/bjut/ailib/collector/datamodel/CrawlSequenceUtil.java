package com.bjut.ailib.collector.datamodel;

import java.util.LinkedList;

public class CrawlSequenceUtil {

	private static final int SEGMENTSIZE = 5000;
	
	public static void add2Sequence(LinkedList<String> sequenceList, String url) {
		
		if (sequenceList.size() <= SEGMENTSIZE) {
			sequenceList.add(url);
			System.out.println("sequence no: " + sequenceList.size());
		} 
		else {
			
		}
	}

	
}
