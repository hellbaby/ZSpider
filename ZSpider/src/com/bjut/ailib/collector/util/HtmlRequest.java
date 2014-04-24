package com.bjut.ailib.collector.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ͨ����վ����URL��ȡ����վ��Դ��
 * @author Administrator
 *
 */
public class HtmlRequest {
    /**
    * @param args
    * @throws MalformedURLException 
    */
    public static void main(String[] args) throws Exception    {
        //URL url = new URL("http://www.ifeng.com"); 
        String url = "http://www.ifeng.com";
    	String urlsource = getURLSource(url);
        System.out.println(urlsource);
    }
    
    /**
     * ͨ����վ����URL��ȡ����վ��Դ��
     * @param url
     * @return String
     * @throws Exception
     */
	public static String getURLSource(String strUrl) {
		URL url = null;
		String htmlSource = null;
		try {
			url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3 * 1000);
			InputStream inStream = conn.getInputStream(); // ͨ����������ȡhtml���������
			byte[] data = readInputStream(inStream); // �Ѷ��������ת��Ϊbyte�ֽ����
			htmlSource = new String(data);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return htmlSource;

	}
    
    /**
     * �Ѷ�������ת��Ϊbyte�ֽ�����
     * @param instream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[]  buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1){
            outStream.write(buffer,0,len);
        }
        instream.close();
        return outStream.toByteArray();         
    }
}
