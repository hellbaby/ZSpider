package com.bjut.ailib.collector.extractor;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjut.ailib.collector.datamodel.Frontier;
import com.bjut.ailib.collector.util.HtmlRequest;
import com.bjut.ailib.collector.util.UrlReader;

public class ExtractorHtml extends Extractor {		
	
	/** 
	 * 用于匹配链接标签<a href="xxx">的正则表达式
	 */
	private static final String PATTERN_A_HREF = "<a\\s.*?href=\"([^\"]+)\"[^>]*>";
	
	/**
	 * 用于匹配http(s)链接头 http(s)://的正则表达式
	 */
	private static final String PATTERN_HTTPLINK = "http[s]?://[^?]*";
	
	
	public void extract(String url) {
		String content = HtmlRequest.getURLSource(url);
		if (content == null) {
			return;
		}

		File file = new File(location + "/" + ExtractorUtil.nameParser(url));
		
		if (file.exists()) {
			return;
		} 
		else {
			ExtractorUtil.saveToFile(url, content, location + "/site");
		}

		//判断是否是链接
		Pattern pattern = Pattern.compile(PATTERN_A_HREF,
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			
			String newUrl = ExtractorUtil.linkTrim(matcher.group(1));
			if (customMatcher(newUrl, regexList)) {
				try {
					frontier.putUrl(newUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	public static boolean customMatcher(String url, List<String> regexList) {
		boolean matches = false;
		for (String regex : regexList) {
			matches = matches | url.matches(regex);
			if (matches) {
				break;
			}
		}
		return matches;
	}

}
