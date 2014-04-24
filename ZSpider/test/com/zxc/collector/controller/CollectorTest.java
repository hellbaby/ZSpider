package com.zxc.collector.controller;

import org.junit.Test;

import com.bjut.ailib.collector.controller.Collector;

public class CollectorTest {
	
	/**
	 * JUNIT不适用于多线程程序的单元测试= =
	 */		
	public static void main(String[] args) {
		try {
			new Collector().start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
