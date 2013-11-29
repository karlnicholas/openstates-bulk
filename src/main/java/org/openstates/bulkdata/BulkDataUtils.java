package org.openstates.bulkdata;

import java.util.ArrayList;
import java.util.List;

import org.openstates.data.Committee;
import org.openstates.model.Legislators;
import org.openstates.data.Legislator;

/**
 * Utilities for the model classes.
 *
 */
public class BulkDataUtils {
	
	/**
	 * Get a list of the members of a committee as legislator objects.
	 *  
	 * @param committee
	 * @return List<Legislator> 
	 */
	public static List<Legislator> getCommitteeMembers(Committee committee) {
		ArrayList<Legislator> members = new ArrayList<Legislator>();
		for ( Committee.Member member: committee.members ) {
			if ( member.leg_id != null ) {
				Legislator legislator = Legislators.get(member.leg_id);
				if ( legislator != null ) members.add(legislator);
			}
		}
		return members;
	}
/*
	public static TreeMap<String, Committee> findAllCommittees(String legislatorId) {
		TreeMap<String, Committee> byLegislator = new TreeMap<String, Committee>();
		for ( Committee committee: committees.values() ) {
			for ( Committee.Member member: committee.members ) {
				if ( member.legislator.id != null && member.legislator.id.equals(legislatorId) ) {
					byLegislator.put(committee.id, committee);
				}
			}
		}
		return byLegislator;
	}

	 * This will fill out the Legislator references in the committee
	 * class with references to pre-loaded legislators. Without
	 * this call, then only a couple of Legislator objects are filled 
	 * out in the Committee class.

	private static void PostProcess() {
		// weave Legislator into Committee.Member
		for ( Committee committee: Committees.committees() ) {
			// Sort it, but I'm not sure why
			// Anyway, if you want to get find a committee.member, now
			// you can use Collections.binarySearch(.., Legislator.id)
			Collections.sort(committee.members);
			for ( Committee.Member member: committee.members ) {
				if ( member.legislator != null && member.legislator.id != null ) {
					Legislator tleg = Legislators.get(member.legislator.id);
					if ( tleg != null ) member.legislator = tleg;
				}
			}
		}
		// sort actions ...
		for ( Bill bill: Bills.bills() ) {
			Collections.sort(bill.actions);
		}

	}
	
*/
}
