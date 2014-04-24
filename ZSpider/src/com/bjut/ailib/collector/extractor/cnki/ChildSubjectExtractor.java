package com.bjut.ailib.collector.extractor.cnki;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjut.ailib.collector.datamodel.Item;
import com.bjut.ailib.collector.extractor.CustomizedExtractor;
import com.bjut.ailib.collector.extractor.ExtractorUtil;
import com.bjut.ailib.collector.util.HtmlRequest;

public class ChildSubjectExtractor extends CustomizedExtractor {

	
	/** 
	 * 用于匹配链接标签<a href="xxx"> XXX <的正则表达式
	 */
	private static final String PATTERN_A_HREF = 
			"<a\\s.*?href=\"([^\"]+)\"[^>]*>([^<]*)<";
	
	private static final String PATTERN_CNKI_CHILDSUBJECT = 
			"/Journal/[a-jA-J]-[a-zA-J][0-9A-Z].htm";
	
	public ChildSubjectExtractor(CustomizedExtractor parrentExtractor) {
		this.parrentExtractor = parrentExtractor;
		seedList = parrentExtractor.getResultList();
		isUpdate = parrentExtractor.isUpdate();
		//super(parrentExtractor);
	}
	
	public ChildSubjectExtractor() {
		
	}

	@Override
	public void extract() {
		
		for (Item seed : seedList) {
			
			String content = HtmlRequest.getURLSource(seed.getUrl());
			Pattern pattern = Pattern.compile(PATTERN_A_HREF, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				
				String newUrl = ExtractorUtil.linkTrim(matcher.group(1));
				if (newUrl.matches(PATTERN_CNKI_CHILDSUBJECT)) {
					
					String name = ExtractorUtil.linkTrim(matcher.group(2));
					Item resultItem = new Item();
					resultItem.setUrl("http://www.cnki.com.cn" + newUrl);
					resultItem.setName(name);
					resultList.add(resultItem);
				}
			}
		}
		for (Item i : resultList) {
			System.out.println(i.getName() + " : " + i.getUrl());
		}
		
	}
	
	public static void main(String[] args) {
//		String url = "http://www.cnki.com.cn/Journal/A-A1.htm";
//		System.out.println(url.matches(PATTERN_CNKI_CHILDSUBJECT));
		ChildSubjectExtractor extractor = new ChildSubjectExtractor();
		Item item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/A.htm");
		List<Item> urlList = new LinkedList<Item> (); 
		urlList.add(item);
		extractor.seedList = urlList;
		
		extractor.extract();
	}
}
