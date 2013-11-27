package org.openstates.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.openstates.data.Legislator;

public class Legislators {
	
	protected static final Logger LOGGER = Logger.getLogger(Legislators.class.getCanonicalName());

	private static TreeMap<String, Legislator> legislators = new TreeMap<String, Legislator>();
	
	public static Legislator get(String id) {
		return legislators.get(id);
	}
	
	public static void put(String id, Legislator legislator ) {
		legislators.put(id, legislator);
	}

	public static Set<String> keySet() {
		return legislators.keySet();
	}
	
	public static Collection<Legislator> legislators() {
		return legislators.values();
	}
	
	public static void clear() {
		legislators.clear();
	}

}
