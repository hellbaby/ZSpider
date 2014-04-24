package com.bjut.ailib.collector.datamodel;

import java.util.HashSet;
import java.util.LinkedList;

import com.bjut.ailib.collector.util.MD5Util;

/**
 * 单例的爬取队列类，为爬取维护全局的顺序队列
 * 
 * @author Edward
 * 
 */
public class CrawlSequence {

	private LinkedList<String> sequenceList;

	// private HashMap<String, Boolean> sequenceMap;
	private HashSet<String> sequenceSet;

	// private HTreeMap<String, Boolean> sequenceMap;


	
	public CrawlSequence() {
		sequenceList = new LinkedList<String>();
		sequenceSet = new HashSet<String>();
		/*
		 * File file0 = new File("d:/mapdb/CommonTest"); if (!file0.exists()) {
		 * file0.mkdirs(); } File file = new
		 * File("d:/mapdb/CommonTest/collector"); // configure and open database
		 * using builder pattern. // all options are available with code
		 * auto-completion. DB db = DBMaker.newFileDB(file)
		 * .closeOnJvmShutdown() .make(); // open existing an collection (or
		 * create new) sequenceMap = db.getHashMap("sequenceMap");
		 */
	}

	public LinkedList<String> getList() {
		return sequenceList;
	}

	public int size() {
		return sequenceList.size();
	}

	public void push(String url) {
		// if (!sequenceMap.containsKey(url)) {
		synchronized (this) {
			String urlMd5 = MD5Util.MD5(url);
			if (!sequenceSet.contains(urlMd5)) {
				//CrawlSequenceUtil.add2Sequence (sequenceList, url);
				sequenceList.add(url);
				System.out.println("sequence no: " + sequenceList.size());	
				sequenceSet.add(urlMd5);
			}
		}
	}

	public String pop() {
		String element = null;
		synchronized (this) {
			sequenceSet.remove(sequenceList.getLast());
			System.out.println("sequence size: " + sequenceList.size());
			element = sequenceList.pop();
		}
		return element;
	}

	
	
	public boolean isEmpty() {
		return sequenceList.isEmpty();
	}

	/*
	 * public synchronized void setDownloadState(String url, boolean state) {
	 * sequenceMap.put(url, true); }
	 */

	/**
	 * url对应的boolean value若为true，则已下载，false则未下载
	 * 
	 * @param url
	 * @return
	 */
	public boolean isDownloadedUrl(String url, String location) {
		boolean downloadState = false;
		// 首先在sequenceMap中检查是否已下载
		/*
		 * if (sequenceMap.get(url) != null) { downloadState =
		 * sequenceMap.get(url); } //sequenceMap中无记录，则在已下载文件中查找 else { File file
		 * = new File(location + "/" + ExtractorUtil.nameParser(url));
		 * downloadState = file.exists(); if (downloadState == true)
		 * {//若已下载且未在map中保存，则将查找结果加入map已提高下次查找效率 sequenceMap.put(url, true); } }
		 */
		return downloadState;
	}

}
