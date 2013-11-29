package org.openstates.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import org.openstates.data.Committee;

/**
 * Holds a {@link TreeMap} of {@link Committee} objects indexed by 
 * the committee.id field.
 *
 */
public class Committees {

	private static TreeMap<String, Committee> committees = new TreeMap<String, Committee>();

	/**
	 * Get a Committee by its id.
	 * 
	 * @param key
	 * @return Commiittee
	 */
	public static Committee get(String key) {
		return committees.get(key);
	}
	
	/**
	 * Put a committee into the Committees map.
	 * 
	 * @param id
	 * @param committee
	 */
	public static void put(String id, Committee committee ) {
		committees.put(id, committee);
	}
	
	/**
	 * Get the keySet of the TreeMap
	 * 
	 * @return Set<String>
	 */
	public static Set<String> keySet() {
		return committees.keySet();
	}
	
	/**
	 * Get the values of the Committees map.
	 * 
	 * @return Collection<Committee>
	 */
	public static Collection<Committee> values() {
		return committees.values();
	}
	
	/**
	 * Access the Committees map directly.
	 * 
	 * @return TreeMap<String, Committee>
	 */
	public static TreeMap<String, Committee> committees() {
		return committees;
	}
	
	/**
	 * Clear the Committees map.
	 */
	public static void clear() {
		committees.clear();
	}
}
