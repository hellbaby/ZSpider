package com.bjut.ailib.collector.extractor.cnki;

import java.util.LinkedList;
import java.util.List;

import com.bjut.ailib.collector.controller.CnkiController;
import com.bjut.ailib.collector.datamodel.Item;
import com.bjut.ailib.collector.extractor.CustomizedExtractor;

public class PaperExtractor extends CustomizedExtractor implements Runnable{
	
	private static final String PATTERN_CNKI_JOURNAL_YEAR = // /Journal/J-J3-XSYS-2000.htm
			"/Journal/[a-jA-J]-[a-jA-J][0-9]-[a-zA-Z]{4}-([0-9]{4})\\.htm";
	
	private static final String PATTERN_CNKI_JOURNAL_VOL = // /Journal/J-J3-GJJJ-2013-04.htm
			"/Journal/[a-jA-J]-[a-jA-J][0-9]-[a-zA-Z]{4}-([0-9]{4})-[0-9A-Z]{2}\\.htm";
	
	private static final String PATTERN_CNKI_PAPER = // /Article/CJFDTotal-XSYS201401044.htm
			"/Article/CJFDTotal-[a-zA-Z]{4}[0-9]+\\.htm";
	
	private static int paperNum;
	
	private CnkiController controller;
	
//	public int getPaperNum() {
//		return paperNum;
//	}
	
	public static synchronized int addPaperNum() {
		paperNum ++;
		return paperNum;
	}
	
	public PaperExtractor(CustomizedExtractor parrentExtractor, CnkiController controller) {
		this.controller = controller;
		this.parrentExtractor = parrentExtractor;
		seedList = parrentExtractor.getResultList();
	}
	
	public PaperExtractor(CustomizedExtractor parrentExtractor) {
		this.parrentExtractor = parrentExtractor;
		seedList = parrentExtractor.getResultList();
		this.isUpdate = parrentExtractor.isUpdate();
	}
	
	public PaperExtractor() {
		
	}

	@Override
	public void extract() {
		
		List<Thread> threads = new LinkedList<Thread>();
		for (int i = 0 ; i < 7546; i++){
			seedList.remove(0);
		}
		paperNum = 7546;
		
		for (int i = 0; i < 40; i ++) {
			Thread thread = new Thread(new PaperExtractorThread(seedList, parrentExtractor));
			threads.add(thread);
			thread.start();
		}
		
		while(!threads.isEmpty()) {
			try {
				threads.remove(0).join();
				System.out.println("1 thread end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		for (Item seed : seedList) {
//			String content = HtmlRequest.getURLSource(seed.getUrl());
//			Pattern pattern = Pattern.compile(PATTERN_CNKI_JOURNAL_YEAR, Pattern.CASE_INSENSITIVE);  
//			Matcher matcher = pattern.matcher(content);
//			while (matcher.find()) {    //find year
//				System.out.println(matcher.group() + ", " + matcher.group(1));
//				String pageOfYear = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcher.group());
//				Pattern patternVol = Pattern.compile(PATTERN_CNKI_JOURNAL_VOL, Pattern.CASE_INSENSITIVE);
//				Matcher matcherVol = patternVol.matcher(pageOfYear);
//				while (matcherVol.find()) {  //find vol
//					System.out.println("---vol: " + matcherVol.group());
//					String pageOfVol = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcherVol.group());
//					Pattern patternPaper = Pattern.compile(PATTERN_CNKI_PAPER, Pattern.CASE_INSENSITIVE);
//					Matcher matcherPaper = patternPaper.matcher(pageOfVol);
//					while (matcherPaper.find()) {   //find paper
//						System.out.println("------paper: " + matcherPaper.group());
//						String pageOfPaper = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcherPaper.group());
//						ExtractorUtil.saveToFile("http://www.cnki.com.cn" + matcherPaper.group(),  pageOfPaper, "D://collector//Customized//test");
//						//Pattern patternPaper = Pattern.compile(PATTERN_CNKI_PAPER, Pattern.CASE_INSENSITIVE);
//					}
//				}
//			}
//		}
	}

	@Override
	public void run() {
		extract();
	}
	
	public static void main(String[] args) {
		//String url = "/Article/CJFDTotal-XSYS201401044.htm";
		//System.out.println(url.matches(PATTERN_CNKI_PAPER));
		PaperExtractor extractor = new PaperExtractor();
		Item item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/J-J3-GJJJ.htm");
		List<Item> urlList = new LinkedList<Item> (); 
		urlList.add(item);
		extractor.seedList = urlList;
		
		extractor.extract();
	}

}
