package com.zxc.collector.util;

import java.util.List;

import org.junit.Test;

import com.bjut.ailib.collector.datamodel.SiteProperty;
import com.bjut.ailib.collector.util.SitePropertyLoader;

public class SitePropertyLoaderTest {
	
	@Test
	public void test() {
		List<SiteProperty> properties = SitePropertyLoader.init();
		for (SiteProperty p: properties) {
			System.out.println(p);
		}
	}
}
