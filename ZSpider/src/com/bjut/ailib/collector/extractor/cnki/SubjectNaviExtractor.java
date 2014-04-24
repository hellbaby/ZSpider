package com.bjut.ailib.collector.extractor.cnki;

import java.util.LinkedList;

import com.bjut.ailib.collector.datamodel.Item;
import com.bjut.ailib.collector.extractor.CustomizedExtractor;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class SubjectNaviExtractor extends CustomizedExtractor {
	
	//private List<String> strCurrentPages = new LinkedList<String>();
	
	/** 
	 * 用于匹配链接标签<a href="xxx">的正则表达式
	 */
	//private static final String PATTERN_A_HREF = "<a\\s.*?href=\"([^\"]+)\"[^>]*>";
	
	//private static final String PATTERN_CNKI_SUBJECTNAVI = "http://www\.cnki\.com\.cn/Navi/\w\.htm";
	
	private Mongo mongo = null;
	
//	private Iterator<String> iterator = null;
	
	private DBCursor cur = null;
	
	private DBCollection subjects = null;
	
	private DB db = null;
	
	BasicDBList condList = new BasicDBList(); 	
	
	public SubjectNaviExtractor() {
		
	}
	
	public SubjectNaviExtractor(boolean isUpdate) {
		super(isUpdate);
	}
	
	public SubjectNaviExtractor(CustomizedExtractor extractor) {
		this.parrentExtractor = extractor;
	}
	
//	@Override
//	public void extract(CustomizedExtractor parrentExtractor) {
//		this.extract();
//	}
	
	public void extract() {
		//this.seedList = parrentExtractor.getResultList();
		
//		for (String seed : seedList) {
//			String currentPage = HtmlRequest.getURLSource(seed);
//			//strCurrentPages.add(HtmlRequest.getURLSource(seed));
//			System.out.println(currentPage);
//			Pattern pattern = Pattern.compile(PATTERN_A_HREF,
//				Pattern.CASE_INSENSITIVE);
//			Matcher matcher = pattern.matcher(currentPage);
//			while (matcher.find()) {
//				String newUrl = ExtractorUtil.linkTrim(matcher.group(1));
//				if (newUrl.matches(PATTERN_CNKI_SUBJECTNAVI))) {
//					resultList.add(newUrl);					
//				}
//			}
//		}
		
		Item item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/A.htm");
		item.setName("理工A(数学物理力学天地生)");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/B.htm");
		item.setName("理工B(化学化工冶金环境矿业)");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/C.htm");
		item.setName("理工C(机电航空交通水利建筑能源)");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/D.htm");
		item.setName("农业");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/E.htm");
		item.setName("医疗卫生");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/F.htm");
		item.setName("文史哲)");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/G.htm");
		item.setName("政治军事与法律");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/H.htm");
		item.setName("教育与社会科学综合");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/I.htm");
		item.setName("电子技术及信息科学");
		resultList.add(item);
		item = new Item();
		item.setUrl("http://www.cnki.com.cn/Journal/J.htm");
		item.setName("经济与管理");
		resultList.add(item);
		
		for (int i = 1; i <= resultList.size(); i ++) {
			Item it = resultList.get(i - 1);
			it.setId(i);
		}
		
	/*	try {
			Mongo mongo = new Mongo();
			db = mongo.getDB("collector");
			//db = mongo.getDB("C");
			subjects = db.getCollection("cnki_subject");
			BasicDBObject doc=new BasicDBObject();
			for (Item it : resultList) {
				doc.put("id", it.getId());
				doc.put("name", it.getName());
				doc.put("url", it.getUrl());
				subjects.insert(doc);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		*/
		
		
		
//		if (childExtractor != null) {
//			childExtractor.extract(resultList);
//		}
		
	}
	
	public static void main(String[] args) {
		//LinkedList<String> seedList = new LinkedList<String>();
		//seedList.add("http://www.cnki.com.cn/Journal/A-A1.htm");
		SubjectNaviExtractor extractor = new SubjectNaviExtractor();
		extractor.extract();
		ChildSubjectExtractor childSubject = new ChildSubjectExtractor(extractor);
		childSubject.extract();
		JournalExtractor journal = new JournalExtractor(childSubject);
		journal.extract();
		PaperExtractor paper = new PaperExtractor(journal);
		paper.extract();		
	}

}
