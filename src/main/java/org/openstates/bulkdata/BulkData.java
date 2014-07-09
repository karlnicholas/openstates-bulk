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

/**
 * This is the "driver" class for this package.
 * It initializes the JSON ObectMapper 
 * and holds various information such as the 
 * current timezone setting. It should not
 * be used generally by the user. Use the 
 * LoadBulkData class.
 *
 */
public class BulkData {
	private static final String bulkDataKey = "bulkdatadir";
	protected static ObjectMapper mapper;
	private static SimpleDateFormat dateFormat;
	protected static String bulkDataDir;
	
	public BulkData(ResourceBundle openStatesResources) throws OpenStatesException {
		mapper = new ObjectMapper();
		dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		mapper.setDateFormat( dateFormat );
		if ( !openStatesResources.containsKey(bulkDataKey) ) {
			throw new OpenStatesException(-1, bulkDataKey + " must be set in openstates.properties!", null, null, null);
		}
		bulkDataDir = openStatesResources.getString(bulkDataKey);
		if ( bulkDataDir.charAt(bulkDataDir.length()-1) != '/' ) bulkDataDir = bulkDataDir + "/";
		File dataDir = new File(bulkDataDir);
		if ( !dataDir.exists() ) dataDir.mkdirs();
		
		mapper.addHandler(new MyDeserializationProblemHandler() );
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

	public void setLoadParameters(TimeZone timeZone) {
		dateFormat.setTimeZone(timeZone);
	}

}
