package com.bjut.ailib.collector.extractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class ExtractorUtil {

	@Test
	public void test() {
		String link = "&#xA;                      http://epub.cnki.net/kns/default.aspx?code=cjfd&amp;f=%E5%85%A8%E6%96%87&amp;kw=%E8%87%AA%E7%84%B6%E7%A7%91%E5%AD%A6%E7%89%88 %E6%95%B0%E5%AD%A6%E6%96%87%E6%91%98 ";
		System.out.println(linkTrim(link));
	}

	public static String linkTrim(String link) {
		return link.replace("&#xA;", "").replace("&amp;", "&").trim();
	}

	public static String nameParser(String url) {
		return url.substring(url.lastIndexOf("/")+1);
	}

	public static int saveToFile(String url, String content, String location) {
		return saveToFile(url, content, location, true);
	}
	
	/**
	 * save content to file. 
	 * @param url
	 * @param content
	 * @param location
	 * @param checkExists
	 * @return
	 * -1 : content error or url error
	 * 0  : file exists
	 * 1  : succeed
	 */
	public static int saveToFile(String url, String content, String location, boolean checkExists) {
		
		if(content == null) {
			return -1;
		}
		
		String fileName = nameParser(url);
		if (fileName.equals("")) {
			return -1;
		}
		//System.out.println(fileName);
		File file0 = new File(location);
		if (!file0.exists()) {
			file0.mkdirs();
		}
		File file = new File(location + "/" + fileName);
		if (checkExists) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
		
		if (file.exists()) {
			return 0;
		}

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
			fw.close();
			//System.out.println("saved: " + location + "\\" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("url:"+url);
			System.out.println("fileName: " + fileName );
			return -1;
		}
		return 1;
	}
}
