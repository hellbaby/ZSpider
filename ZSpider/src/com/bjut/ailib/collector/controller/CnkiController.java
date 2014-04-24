package com.bjut.ailib.collector.controller;


import java.util.Date;

import com.bjut.ailib.collector.extractor.cnki.ChildSubjectExtractor;
import com.bjut.ailib.collector.extractor.cnki.JournalExtractor;
import com.bjut.ailib.collector.extractor.cnki.PaperExtractor;
import com.bjut.ailib.collector.extractor.cnki.SubjectNaviExtractor;

public class CnkiController implements CustomizedController{
	
	public static String location;	
	
	public enum COLLECTOR_STATE {WAITING, RUNNING, PAUSED, FINISHED};
	
	private COLLECTOR_STATE state = COLLECTOR_STATE.WAITING;
	
	private Date latest;
	
	
	public Date getLatest() {
		return latest;
	}

	public void setLatest(Date latest) {
		this.latest = latest;
	}

	public COLLECTOR_STATE getState() {
		return state;
	}
	
	public CnkiController () {
		String date = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		location = "d:\\collector\\" + "cnki" + date;
	}
	
	public void collect() {
		doCollect(false);
	}
	
	public void update() {
		doCollect(true);
	}
	
	private void doCollect(boolean isUpdate) {
		
		state = COLLECTOR_STATE.RUNNING;
		SubjectNaviExtractor extractor = new SubjectNaviExtractor(isUpdate);
		extractor.extract();
		ChildSubjectExtractor childSubject = new ChildSubjectExtractor(extractor);
		childSubject.extract();
		JournalExtractor journal = new JournalExtractor(childSubject);
		journal.extract();
		PaperExtractor paper = new PaperExtractor(journal);
		paper.extract();
		//Thread paperExtractor = new Thread(paper);
		//paperExtractor.start();
		//while()
		state = COLLECTOR_STATE.FINISHED;
		
	}
	
	public static void main(String[] args) {
		CnkiController controller = new CnkiController();
		controller.collect();
		
	}
}
