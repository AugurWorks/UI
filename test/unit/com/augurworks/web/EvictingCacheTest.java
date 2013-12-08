package com.augurworks.web;

import org.junit.Test;

import com.augurworks.web.util.EvictingCache;

public class EvictingCacheTest {
	@Test
	public void test() throws Exception {
		EvictingCache<String, Integer> cache = new EvictingCache<String, Integer>();
		cache.put("A", 1, 100);
		cache.put("B", 2, 2000);
		Thread.sleep(1000);
		System.out.println(cache.getIfPresentAndValid("A"));
		System.out.println(cache.getIfPresentAndValid("B"));
		System.out.println(cache.map);
		Thread.sleep(1500);
		System.out.println(cache.map);
		System.out.println(cache.getIfPresentAndValid("B"));
		System.out.println(cache.map);
	}
	
	@Test
	public void testRefresh() throws Exception {
		EvictingCache<String, Integer> cache = new EvictingCache<String, Integer>();
		cache.put("A", 1, 100);
		cache.put("B", 2, 2000);
		Thread.sleep(1000);
		cache.refresh("A", 2000);
		System.out.println(cache.getIfPresentAndValid("A"));
		System.out.println(cache.getIfPresentAndValid("B"));
		System.out.println(cache.map);
		Thread.sleep(1500);
		System.out.println(cache.map);
		System.out.println(cache.getIfPresentAndValid("B"));
		System.out.println(cache.map);
	}
}
