package com.bjut.ailib.collector.extractor.cnki;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjut.ailib.collector.controller.CnkiController;
import com.bjut.ailib.collector.datamodel.Item;
import com.bjut.ailib.collector.extractor.CustomizedExtractor;
import com.bjut.ailib.collector.extractor.ExtractorUtil;
import com.bjut.ailib.collector.util.HtmlRequest;

public class PaperExtractorThread implements Runnable{
	
	private List<Item> seedList;
	
	private static final String PATTERN_CNKI_JOURNAL_YEAR = // /Journal/J-J3-XSYS-2000.htm
			"/Journal/[a-jA-J]-[a-jA-J][0-9]-[a-zA-Z]{4}-([0-9]{4})\\.htm";
	
	private static final String PATTERN_CNKI_JOURNAL_VOL = // /Journal/J-J3-GJJJ-2013-04.htm
			"/Journal/[a-jA-J]-[a-jA-J][0-9]-[a-zA-Z]{4}-([0-9]{4})-[0-9A-Z]{2}\\.htm";
	
	private static final String PATTERN_CNKI_PAPER = // /Article/CJFDTotal-XSYS201401044.htm
			"/Article/CJFDTotal-[a-zA-Z]{4}[0-9a-zA-Z]+\\.htm";
	
	private CustomizedExtractor parrentExtractor;
	
	private boolean isUpdate = false;
	
//	private CnkiController controller;
	
	private static final int times = 2;
	

	public PaperExtractorThread(List<Item> seedList, CustomizedExtractor parrentExtractor) {
		this.seedList = seedList;
		this.parrentExtractor = parrentExtractor;
		this.isUpdate = parrentExtractor.isUpdate();
	}
	
	public void extract(Item seed) {
		
		System.out.println(PaperExtractor.addPaperNum() + " : " + seed.getName());
		String content = HtmlRequest.getURLSource(seed.getUrl());
		
		if (content == null) { //若未获取到内容，重新尝试五次
			content = retryHtmlRequest(seed.getUrl(), times);
		}
		if (content == null) {
			return;
		}
		
		Pattern patternVol = Pattern.compile(PATTERN_CNKI_JOURNAL_VOL, Pattern.CASE_INSENSITIVE);
		Pattern patternPaper = Pattern.compile(PATTERN_CNKI_PAPER, Pattern.CASE_INSENSITIVE);
		Matcher matcherVol = patternVol.matcher(content);
		while (matcherVol.find()) {  //find vol
		//	System.out.println("---vol: " + matcherVol.group());
			String pageOfVol = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcherVol.group());
			if (pageOfVol == null) {
				pageOfVol = retryHtmlRequest("http://www.cnki.com.cn" + matcherVol.group(), times);
			}
			if (pageOfVol == null) {
				continue;
			}
			
			
			Matcher matcherPaper = patternPaper.matcher(pageOfVol);
			while (matcherPaper.find()) {   //find paper
			//	System.out.println("------paper: " + matcherPaper.group());
				String pageOfPaper = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcherPaper.group());
				if (pageOfPaper == null) {
					pageOfPaper = retryHtmlRequest("http://www.cnki.com.cn" + matcherPaper.group(), times);
				}
				if (pageOfPaper == null) {
					continue;
				}
				int saveResult = ExtractorUtil.saveToFile("http://www.cnki.com.cn" + matcherPaper.group(),  pageOfPaper,
						"D:\\collector\\cnki20140303_220747" + "/" + seed.getCode(), false);
						//CnkiController.location + "/" + seed.getCode(),	false);
				//Pattern patternPaper = Pattern.compile(PATTERN_CNKI_PAPER, Pattern.CASE_INSENSITIVE);
				if (this.isUpdate) {
					if (saveResult == 0) {
						return;   //if it is an update task, saveResult == 0 means this file has been collected and the update of this journal is done.
					}
				}
			}
		}
		
		
		Pattern pattern = Pattern.compile(PATTERN_CNKI_JOURNAL_YEAR, Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(content);
		
		while (matcher.find()) {    //find year
		//	System.out.println(matcher.group() + ", " + matcher.group(1));
			String pageOfYear = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcher.group());
			if (pageOfYear == null) {
				pageOfYear = retryHtmlRequest("http://www.cnki.com.cn" + matcher.group(), times);
			}
			if (pageOfYear == null) {
				continue;
			}
			
			//patternVol = Pattern.compile(PATTERN_CNKI_JOURNAL_VOL, Pattern.CASE_INSENSITIVE);
			matcherVol = patternVol.matcher(pageOfYear);
			while (matcherVol.find()) {  //find vol
			//	System.out.println("---vol: " + matcherVol.group());
				String pageOfVol = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcherVol.group());
				if (pageOfVol == null) {
					pageOfVol = retryHtmlRequest("http://www.cnki.com.cn" + matcherVol.group(), times);
				}
				if (pageOfVol == null) {
					continue;
				}
				
				//Pattern patternPaper = Pattern.compile(PATTERN_CNKI_PAPER, Pattern.CASE_INSENSITIVE);
				Matcher matcherPaper = patternPaper.matcher(pageOfVol);
				while (matcherPaper.find()) {   //find paper
				//	System.out.println("------paper: " + matcherPaper.group());
					String pageOfPaper = HtmlRequest.getURLSource("http://www.cnki.com.cn" + matcherPaper.group());
					if (pageOfPaper == null) {
						pageOfPaper = retryHtmlRequest("http://www.cnki.com.cn" + matcherPaper.group(), times);
					}
					if (pageOfPaper == null) {
						continue;
					}
					ExtractorUtil.saveToFile("http://www.cnki.com.cn" + matcherPaper.group(),  pageOfPaper,
							"D:\\collector\\cnki20140303_220747" + "/" + seed.getCode(), false);
							//CnkiController.location + "/" + seed.getCode(),	false);
					//Pattern patternPaper = Pattern.compile(PATTERN_CNKI_PAPER, Pattern.CASE_INSENSITIVE);
				}
			}
		}
	}
	
	@Override
	public void run() {
		while (!seedList.isEmpty()) {
			Item seed = seedList.remove(0);	
			
			extract(seed);
		}
	}
	
	public String retryHtmlRequest(String url, int times) {
		String content = null;
		for (int i = 0; i < times; i++) {
			content = HtmlRequest.getURLSource(url);
			if (content != null) {
				break;
			}
		}
		return content;
	}

}
