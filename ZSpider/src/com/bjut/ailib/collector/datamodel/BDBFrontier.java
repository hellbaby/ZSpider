package com.bjut.ailib.collector.datamodel;

import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Set;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseException;

public class BDBFrontier extends AbstractFrontier implements Frontier {
	private StoredMap pendingUrisDB = null;

	// 使用默认的路径和缓存大小构造函数
	public BDBFrontier(String homeDirectory) throws DatabaseException,
			FileNotFoundException {
		super(homeDirectory);
		EntryBinding keyBinding = new SerialBinding(javaCatalog, String.class);
		EntryBinding valueBinding = new SerialBinding(javaCatalog,
				String.class);
		pendingUrisDB = new StoredMap(database, keyBinding, valueBinding, true);
	}

	// 获得下一条记录
	public String getNext() throws Exception {
		String result = null;
		if (!pendingUrisDB.isEmpty()) {
			Set entrys = pendingUrisDB.entrySet();
			Entry<String, String> entry = (Entry<String, String>) pendingUrisDB
					.entrySet().iterator().next();
			//result = entry.getValue();
			result = entry.getKey();
			delete(entry.getKey());
		}
		
		return result;
	}

	// 存入URL
	public boolean putUrl(String url) {

		put(url, "");
		return true;
	}

	// 存入数据库的方法
	protected void put(Object key, Object value) {
		pendingUrisDB.put(key, value);
	}

	// 取出
	protected Object get(Object key) {
		return pendingUrisDB.get(key);
	}

	// 删除
	protected Object delete(Object key) {
		return pendingUrisDB.remove(key);
	}

	// 根据URL计算键值，可以使用各种压缩算法，包括MD5等压缩算法
	private String caculateUrl(String url) {
		return url;
	}
	
	public long size() {
		return (long)pendingUrisDB.size();
	}

	// 测试函数
	public static void main(String[] strs) {
		BDBFrontier bBDBFrontier = null;
		try {
			bBDBFrontier = new BDBFrontier("D:\\berkeleyDb\\test");
			//S url = new CrawlUrl();
			//url.setOriUrl("http://www.163.com");
			String url = "adas";
			bBDBFrontier.putUrl(url);
			bBDBFrontier.putUrl("231211");
			bBDBFrontier.putUrl("00000000");
			String tempStr;
			while ( (tempStr = (String) bBDBFrontier.getNext()) != null)
			System.out.println(tempStr);
			bBDBFrontier.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bBDBFrontier!=null) {
				try {
					bBDBFrontier.close();
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

	@Override
	public String popFirst() {
		// TODO Auto-generated method stub
		return null;
	}
}