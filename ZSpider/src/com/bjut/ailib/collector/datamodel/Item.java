package com.bjut.ailib.collector.datamodel;

public class Item {

	private int id;
	
	//private List<Item> children;
	
	private String name;
	
	private String url;
	
	private int parrentId;
	
	private String code;
		
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
/*	public List<Item> getChildren() {
		return children;
	}
	
	public void setChildren(List<Item> children) {
		this.children = children;
	}
	*/
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setParrentId(int parrentId) {
		this.parrentId = parrentId;
	}
	
	public int getParrentId() {
		return parrentId;
	}
}
