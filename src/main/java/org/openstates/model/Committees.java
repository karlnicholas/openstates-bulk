package org.openstates.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.openstates.data.Committee;

public class Committees {

	protected static final Logger LOGGER = Logger.getLogger(Committees.class.getCanonicalName());

	private static TreeMap<String, Committee> committees = new TreeMap<String, Committee>();
	
	public static Committee get(String key) {
		return committees.get(key);
	}
	
	public static void put(String id, Committee committee ) {
		committees.put(id, committee);
	}
	
	public static Set<String> keySet() {
		return committees.keySet();
	}
	
	public static Collection<Committee> committees() {
		return committees.values();
	}
	
	public static void clear() {
		committees.clear();
	}
}
