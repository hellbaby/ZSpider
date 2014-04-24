package com.bjut.ailib.collector.extractor.cnki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjut.ailib.collector.datamodel.Item;
import com.bjut.ailib.collector.extractor.CustomizedExtractor;
import com.bjut.ailib.collector.extractor.ExtractorUtil;
import com.bjut.ailib.collector.util.HtmlRequest;

public class JournalExtractor extends CustomizedExtractor implements Runnable{
		

	
	private static final String PATTERN_CNKI_JOURNAL = 
			"/Journal/[a-jA-J]-[a-jA-J][0-9]*+-[a-zA-Z]{4}\\.htm";
	
	private static int journalNum = 0;
	
//	public JournalExtractor(CustomizedExtractor extractor) {
//		this.parentItem = 
//	}
	
	public JournalExtractor(CustomizedExtractor extractor) {
		this.parrentExtractor = extractor;
		seedList = parrentExtractor.getResultList();
		this.isUpdate = parrentExtractor.isUpdate();
		//super(extractor);
		
	}

	@Override
	public void extract() {
		
		for (Item seed : seedList) {
			String content = HtmlRequest.getURLSource(seed.getUrl());
			if (content == null) {
				for (int i = 0; i < 10; i ++){
					content = HtmlRequest.getURLSource(seed.getUrl());
					if (content != null) {
						break;
					}
				}
			}
			if (content == null) {
				continue;
			}
			//System.out.println(content);
			Pattern pattern = Pattern.compile(PATTERN_A_HREF, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				
				String newUrl = ExtractorUtil.linkTrim(matcher.group(1));
				if (newUrl.matches(PATTERN_CNKI_JOURNAL)) {
					//System.out.println("journal");
					String name = ExtractorUtil.linkTrim(matcher.group(2));
					Item resultItem = new Item();
					resultItem.setName(name);
					resultItem.setCode(newUrl.replace("/Journal/", "").replace(".htm", ""));
					resultItem.setUrl("http://www.cnki.com.cn" + newUrl);
					resultList.add(resultItem);
					System.out.println(++journalNum + name + " : " + "http://www.cnki.com.cn" + newUrl);
				}
			}
		}
//		System.out.println("-----journals:-----");
//		for (Item i : resultList) {
//			
//			System.out.println(i.getClassName() + " : " + i.getUrl());
//		}
	}
	
	@Override
	public void run() {
		extract();
	}
	
	public static void main(String[] args) {
		String url = "http://www.cnki.com.cn/Journal/J-J9-HLJC.htm";
		System.out.println(url.replace("/Journal/", "").replace(".htm", ""));
	}

	
}
