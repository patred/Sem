package it.synclab.patred.sem.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

public class MetadataMap<K, V> implements MultivaluedMap<K, V> {
	private Map<K, List<V>> m;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MetadataMap() {
		this(new HashMap());
	}

	public MetadataMap(Map<K, List<V>> store) {
		this.m = store;
	}

	public void add(K key, V value) {
		List<V> data = (List<V>) this.m.get(key);
		if (data == null) {
			data = new ArrayList<V>();
			this.m.put(key, data);
		}
		data.add(value);
	}

	public V getFirst(K key) {
		List<V> data = (List<V>) this.m.get(key);
		return data == null ? null : data.get(0);
	}

	public void putSingle(K key, V value) {
		List<V> data = new ArrayList<V>();
		data.add(value);
		this.m.put(key, data);
	}

	public void clear() {
		this.m.clear();
	}

	public boolean containsKey(Object key) {
		return this.m.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.m.containsValue(value);
	}

	public Set<Map.Entry<K, List<V>>> entrySet() {
		return this.m.entrySet();
	}

	public List<V> get(Object key) {
		return (List<V>) this.m.get(key);
	}

	public boolean isEmpty() {
		return this.m.isEmpty();
	}

	public Set<K> keySet() {
		return this.m.keySet();
	}

	public List<V> put(K key, List<V> value) {
		return (List<V>) this.m.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends List<V>> map) {
		this.m.putAll(map);
	}

	public List<V> remove(Object key) {
		return (List<V>) this.m.remove(key);
	}

	public int size() {
		return this.m.size();
	}

	public Collection<List<V>> values() {
		return this.m.values();
	}

	public int hashCode() {
		return this.m.hashCode();
	}

	public boolean equals(Object o) {
		return this.m.equals(o);
	}

	public String toString() {
		return this.m.toString();
	}
}