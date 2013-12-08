package com.augurworks.web.util;

import java.util.HashMap;
import java.util.Map;

public class EvictingCache<K, V> {
	public final Map<K, CacheVal<V>> map = new HashMap<K, CacheVal<V>>();
	private final Object lock = new Object();
	
	public V getIfPresentAndValid(K key) {
		synchronized (lock) {
			CacheVal<V> val = map.get(key);
			if (val != null && val.isValid()) {
				return val.getObject();
			} else {
				remove(key);
				return null;
			}
		}
	}
	
	public void put(K key, V value, long timeToExpire) {
		synchronized (lock) {
			CacheVal<V> val = new CacheVal<V>(value, 
					System.currentTimeMillis() + timeToExpire);
			map.put(key, val);
		}
	}
	
	public void refresh(K key, long timeToExpire) {
		synchronized (lock) {
			CacheVal<V> val = map.get(key);
			map.put(key, new CacheVal<V>(val.getObject(), 
					System.currentTimeMillis() + timeToExpire));
		}
	}
	
	public void remove(K key) {
		synchronized (lock) {
			map.remove(key);
		}
	}
	
	public static class CacheVal<T> {
		private T object;
		private long expirationTime;
		
		public CacheVal(T object, long expirationTime) {
			this.object = object;
			this.expirationTime = expirationTime;
		}
		
		public T getObject() {
			return object;
		}
		
		public long getExpiration() {
			return expirationTime;
		}
		
		public boolean isValid() {
			return this.expirationTime > System.currentTimeMillis();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ (int) (expirationTime ^ (expirationTime >>> 32));
			result = prime * result
					+ ((object == null) ? 0 : object.hashCode());
			return result;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheVal other = (CacheVal) obj;
			if (expirationTime != other.expirationTime)
				return false;
			if (object == null) {
				if (other.object != null)
					return false;
			} else if (!object.equals(other.object))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CacheVal [object=" + object + ", expirationTime="
					+ expirationTime + "]";
		}
	}
}
