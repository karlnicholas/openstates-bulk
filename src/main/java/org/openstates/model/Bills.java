package org.openstates.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import org.openstates.data.Bill;

/**
 * Holds a {@link TreeMap} of {@link Bill} objects indexed
 * by the bill.id field.
 *
 */
public class Bills {
	private static TreeMap<String, Bill> bills = new TreeMap<String, Bill>();

	/**
	 * Get a Bill from the Bills map by its id.
	 * 
	 * @param id
	 * @return Bill
	 */
	public static Bill get(String id) {
		return bills.get(id);
	}
	
	/**
	 * Put a Bill into the Bills map.
	 * 
	 * @param id
	 * @param bill
	 */
	public static void put(String id, Bill bill ) {
		bills.put(id,  bill);
	}
	
	/**
	 * Get the KeySet of the Bills map.
	 * 
	 * @return Set<String>
	 */
	public static Set<String> keySet() {
		return bills.keySet();
	}
	
	/**
	 * Get the values of the Bills map.
	 * 
	 * @return Collection<Bill>
	 */
	public static Collection<Bill> values() {
		return bills.values();
	}
	
	/**
	 * Clear the Bills map.
	 * 
	 */
	public static void clear() {
		bills.clear();
	}
	
	/**
	 * Access the Bills map directly.
	 * 
	 * @return TreeMap<String, Bill>
	 */
	public TreeMap<String, Bill> bills() {
		return bills;
	}

}
