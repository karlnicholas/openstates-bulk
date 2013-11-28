package org.openstates.bulkdata;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.TreeMap;

import org.openstates.api.OpenStatesException;
import org.openstates.data.DataBase;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

public class BulkData {
	
	public static final String bulkDataKey = "bulkdatadir";
	protected static ObjectMapper mapper;
	private static SimpleDateFormat dateFormat;
	protected static String bulkDataDir;

	
	public BulkData(ResourceBundle openStatesResources) throws OpenStatesException {
		mapper = new ObjectMapper();
		dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		mapper.setDateFormat( dateFormat );
		bulkDataDir = openStatesResources.getString(bulkDataKey);
		if ( bulkDataDir != null && !bulkDataDir.isEmpty() ) {
			
		} else {
			throw new OpenStatesException(bulkDataKey + " must be set in openstates.properties!");
		}
		if ( bulkDataDir.charAt(bulkDataDir.length()-1) != '/' ) bulkDataDir = bulkDataDir + "/";
		File dataDir = new File(bulkDataDir);
		if ( !dataDir.exists() ) dataDir.mkdirs();
		
		mapper.addHandler(new MyDeserializationProblemHandler() );
/*		
		mapper.addMixInAnnotations(Legislator.class, LegislatorMixIn.class);
		mapper.addMixInAnnotations(Legislator.Role.class, LegislatorMixIn.RoleMixIn.class);
		mapper.addMixInAnnotations(Committee.class, CommitteeMixIn.class);
		mapper.addMixInAnnotations(Committee.Member.class, CommitteeMixIn.MemberMixIn.class);
		mapper.addMixInAnnotations(Bill.class, BillMixIn.class);
		mapper.addMixInAnnotations(Bill.Action.class, BillMixIn.ActionMixIn.class);
		mapper.addMixInAnnotations(Bill.Document.class, BillMixIn.DocumentMixIn.class);
		mapper.addMixInAnnotations(Bill.Sponsor.class, BillMixIn.SponsorMixIn.class);
		mapper.addMixInAnnotations(Bill.Version.class, BillMixIn.VersionMixIn.class);
		mapper.addMixInAnnotations(Bill.Vote.class, BillMixIn.VoteMixIn.class);
*/		
	}
	
	private static class MyDeserializationProblemHandler extends DeserializationProblemHandler {
		public boolean handleUnknownProperty(
			DeserializationContext ctxt,
	        JsonParser jp,
	        JsonDeserializer<?> deserializer,
	        Object beanOrClass,
	        String propertyName) throws IOException, JsonProcessingException 
	    {
			if ( propertyName.charAt(0) == '+' ) {
				if ( beanOrClass instanceof DataBase ) {
					DataBase base = (DataBase)beanOrClass; 
					if ( base.pluses == null ) base.pluses = new TreeMap<String, TreeNode>();
					base.pluses.put(propertyName, jp.readValueAsTree());
				} else {
					throw new RuntimeException("beanOrClass type unknown");
				}
			} else {
				if ( beanOrClass instanceof DataBase ) {
					DataBase base = (DataBase)beanOrClass; 
					if ( base.newFields == null ) base.newFields = new TreeMap<String, TreeNode>();
					base.newFields.put(propertyName, jp.readValueAsTree());
				} else {
					throw new RuntimeException("beanOrClass type unknown");
				}
			}
//					ctxt.getParser().skipChildren();
			return true;
	    }
	}
/*	
	public static class LegislatorMixIn {
		public @JsonProperty("+phone") String plusphone;
		public @JsonProperty("+capitol_office") String pluscapitol_office;
		public @JsonProperty("+district_offices") String plusdistrict_offices;
		public @JsonProperty("+district") String plusdistrict;
		public @JsonProperty("+party") String plusparty;
		public @JsonProperty("+occupation") String plusoccupation;
		public @JsonProperty("+fax") String plusfax;
		public @JsonProperty("+email") String plusemail;
		public @JsonProperty("+url") String plusurl;
		public @JsonProperty("+room") String room;
		public @JsonProperty("+office_fax") String plusoffice_fax;
		public @JsonProperty("+photo") String plusphoto;
		public static class RoleMixIn {
			public @JsonProperty("+active") String plusactive;
		}
	}
	
	public static class CommitteeMixIn {
		public @JsonProperty("+status") String plusstatus;
		public @JsonProperty("+action_code") String plusaction_code;
		public @JsonProperty("+session") String plussession;
		public @JsonProperty("+az_committee_id") String plusaz_committee_id;
		public @JsonProperty("+short_name") String plusshort_name;
		public @JsonProperty("+twitter") String plustwitter;
		public static class MemberMixIn {
			public @JsonProperty("+chamber") String pluschamber;
		}
	}
	
	public static class BillMixIn {
		public @JsonProperty("+bill_url") String plusbillurl;
		public @JsonProperty("+bill_lr") String plusbill_lr;
		public @JsonProperty("+official_title") String plusofficial_title;
		public @JsonProperty("+short_title") String plusshort_title;
		public @JsonProperty("+impact_clause") String plusimpact_clause;
		public @JsonProperty("+type_") String plustype_;
		public @JsonProperty("+final_disposition") String plusfinal_disposition;
		public static class ActionMixIn {
			public @JsonProperty("+amendment") String plusamendment;
			public @JsonProperty("+actor_info") String plusactor_info;
			public @JsonProperty("+no_votes") String plusno_votes;
			public @JsonProperty("+yes_votes") String plusyes_votes;
			public @JsonProperty("+abbrv") String plusabbrv;
			public @JsonProperty("+concur") String plusconcur;
			public @JsonProperty("+chapter") String pluschapter;
			public @JsonProperty("+chaptered_version") String pluschaptered_version;
			public @JsonProperty("+brief_action_name") String plusbrief_action_name;
			public @JsonProperty("+assigned_ctty") String plusassigned_ctty;
		}
		public static class DocumentMixIn {
			public @JsonProperty("+type") String plustype;
			public @JsonProperty("+date") String plusdate;
		}
		public static class SponsorMixIn {
			public @JsonProperty("+sponsor_link") String plussponsorlink;
		}
		public static class VersionMixIn {
			public @JsonProperty("+pdf_url") String pluspdf_url;			
			public @JsonProperty("+mimetype") String plusmimetype;			
		}
		public static class VoteMixIn {
			public @JsonProperty("+threshold") String plusthreshold;
			public @JsonProperty("+type_") String plustype_;
			public @JsonProperty("+seconded") String plusseconded;
			public @JsonProperty("+present") String pluspresent;
			public @JsonProperty("+excused") String plusexcused;
			public @JsonProperty("+absent") String plusabsent;
			public @JsonProperty("+vacant") String plusvacant;
			public @JsonProperty("+not_voting") String plusnot_voting;
			public @JsonProperty("+amended") String plusamended;
			public @JsonProperty("+AB") String plusAB;
			public @JsonProperty("+NV") String plusNV;
			public @JsonProperty("+P") String plusP;
			public @JsonProperty("+V") String plusV;
			public @JsonProperty("+E") String plusE;
			public @JsonProperty("+EMER") String plusEMER;
			public @JsonProperty("+EXC") String plusEXC;
		}
	}
*/
	public void setLoadParameters(TimeZone timeZone) {
		dateFormat.setTimeZone(timeZone);
	}

}
