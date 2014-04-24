package com.bjut.ailib.collector.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlReader {
 
/**
 * 	
 * @param tempurl
 * @return
 */
	public static String getHTML(String tempurl) {
		return getHTML(tempurl, "UTF-8");
	}
 /**
  * 
  * Description: ��ȡָ��URL������
  * @Version1.0 2012-6-1 ����02:18:22 by Albert��albertwxh@gmail.com������
  * @param tempurl url��ַ
  * @param code urlҳ�����
  * @return
  */
	public static String getHTML(String tempurl, String code)  {
		URL url = null;
		BufferedReader breader = null;
		InputStream is = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			url = new URL(tempurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			is = conn.getInputStream();
			breader = new BufferedReader(new InputStreamReader(is, code));
			String line = "";
			int i = 0;
			while ((line = breader.readLine()) != null) {
				resultBuffer.append(line);
				i ++;
				if(i>1500) {
					break;
				}
			}
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			if (breader != null) {
				try {
					breader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
			
		}		
		
		return resultBuffer.toString();
	}
	
	public static void main(String[] args) {
		String result = UrlReader.getHTML(
				"http://link.springer.com/article/10.1007/BF02269416", "UTF-8")
				.toString();
		System.out.println(result);
	}

}