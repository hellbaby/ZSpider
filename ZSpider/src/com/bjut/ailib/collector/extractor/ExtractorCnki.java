package com.bjut.ailib.collector.extractor;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjut.ailib.collector.util.UrlReader;

public class ExtractorCnki extends Extractor {
		
	/** 
	 * 用于匹配链接标签<a href="xxx">的正则表达式
	 */
	private static final String PATTERN_A_HREF = "<a\\s.*?href=\"([^\"]+)\"[^>]*>";
	
	/**
	 * 用于匹配CNKI Article的格式的正则表达式
	 */
	private static final String PATTERN_CNKI_ARTICLE = "http://www\\.cnki\\.com\\.cn/Article/CJFDTOTAL-.+\\.htm[l]?$";
	
	/**
	 * 用于匹配CNKI导航页的正则表达式
	 */
	private static final String PATTERN_CNKI_NAVI = "http://www\\.cnki\\.com\\.cn/Navi/.+\\.htm[l]?$";
	
	@Override
	public void extract(String url) {
		
		String content = UrlReader.getHTML(url).toString();
		if (content == null) {
			return;
		}
		File file = new File(location + "/" + ExtractorUtil.nameParser(url));
		if (file.exists()) {
			return;
		} 
		else {
			ExtractorUtil.saveToFile(url, content, location);
		}

		//判断是否是链接标签
		Pattern pattern = Pattern.compile(PATTERN_A_HREF,
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String newUrl = ExtractorUtil.linkTrim(matcher.group(1));
			if (cnkiUrlMatcher(newUrl)) {
		//		sequence.push(newUrl);
			}
		}
	}
	
	public static boolean cnkiUrlMatcher(String url) {
		return (url.matches(PATTERN_CNKI_ARTICLE) || url.matches(PATTERN_CNKI_NAVI));
			
	}

}
