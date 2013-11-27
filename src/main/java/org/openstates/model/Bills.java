package org.openstates.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.openstates.data.Bill;

public class Bills {
	protected static final Logger logger = Logger.getLogger(Bills.class.getCanonicalName());

	private static TreeMap<String, Bill> bills = new TreeMap<String, Bill>();
	
	public static Bill get(String id) {
		return bills.get(id);
	}
	
	public static void put(String id, Bill bill ) {
		bills.put(id,  bill);
	}
	
	public static Set<String> keySet() {
		return bills.keySet();
	}
	
	public static Collection<Bill> bills() {
		return bills.values();
	}
	
	public static void clear() {
		bills.clear();
	}

}
