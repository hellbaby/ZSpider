package com.bjut.ailib.collector.datamodel;

import java.net.UnknownHostException;


import com.bjut.ailib.collector.controller.AbstractController;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoFrontier implements Frontier {

	private Mongo mongo = null;
	
//	private Iterator<String> iterator = null;
	
	private DBCursor cur = null;
	
	private DBCollection urls = null;
	
	private DB db = null;
	
//	private boolean finished = false;	

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public DBCursor getCur() {
		return cur;
	}

	public void setCur(DBCursor cur) {
		this.cur = cur;
	}

	public DBCollection getUrls() {
		return urls;
	}

	public void setUrls(DBCollection urls) {
		this.urls = urls;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public MongoFrontier(String collectionName) {
		try {
			mongo = new Mongo();
			db = mongo.getDB("collector");
			//db = mongo.getDB("C");
			urls = db.getCollection(collectionName);			
			cur = urls.find();
			cur.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public MongoFrontier(AbstractController controller) {
		this(controller.getName());
	}
	
	@Override
	public String popFirst() {
		String firstUrl = null;
		if (cur.hasNext()) {
			DBObject o = cur.next();
			firstUrl =  (String)o.get("url");
			urls.remove(o);
			o = null;
		}
		
		
		
		return firstUrl;
				
	}

	public boolean hasNext () {
		return cur.hasNext();
	
	}
	@Override
	public boolean putUrl(String url) {
		DBObject o = new BasicDBObject();
		o.put("url", url);
		urls.save(o);
		return true;
	}
		
	@Override
	public long size() {
		return urls.getCount();		
	}
	
	public void close() {
		if (this.cur != null) {
			cur.close();
		}
		if (this.mongo != null) {
			mongo.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MongoFrontier frontier = new MongoFrontier("foo");
		try {
			
			// 查询所有的Database
			for (String name : frontier.mongo.getDatabaseNames()) {
				System.out.println("dbName: " + name);
			}
			
	        //查询所有的聚集集合
	        for (String name : frontier.db.getCollectionNames()) {
	            System.out.println("collectionName: " + name);
	        }
	      
	        //查询所有的数据
	        System.out.println("size:" + frontier.size());
//	       while (frontier.hasNext()) { //	    	   System.out.println(frontier.popFirst());
//	        };
//	        System.out.println("count:" + frontier.size());
	        
//	        System.out.println(cur.count());
//	        System.out.println(cur.getCursorId());
	        
//	        DBObject o = new BasicDBObject();
//	        //o.put("aaaa", false);
//	        //o.put("sexa", "男");
//	        System.out.println("save:" + users.save(o).getN());
//	        //添加多条数据，传递Array对象
//
//	        System.out.println(users.insert(o, new BasicDBObject("name", "tom")).getN());
		} finally {			
			frontier.close();
		}
	}

}
