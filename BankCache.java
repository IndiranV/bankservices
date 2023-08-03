package org.in.com.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import java.io.Serializable;

public class BankCache {
	public static void main(String[] args) {
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache("bankCache");
		cache.put(new Element(1, "101"));
		cache.put(new Element(2, "Saren"));
		cache.put(new Element(3, "IT"));
		Element e1 = cache.get(3);
		Cache cache1 = cacheManager.getCache("bankCache");
		Element element = cache1.get(3);
		Serializable value = element.getValue();

		String cacheString = (e1 == null ? null : e1.getObjectValue().toString());
		System.out.println(cacheString);
		System.out.println(cache.isKeyInCache(2));
		System.out.println(cache.isKeyInCache(3));
		cacheManager.removeCache("bankCache");
		cacheManager.shutdown();
	}
}
