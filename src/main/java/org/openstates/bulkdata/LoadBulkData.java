package org.openstates.bulkdata;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.openstates.api.OpenStatesException;
import org.openstates.data.Bill;
import org.openstates.data.Committee;
import org.openstates.data.Legislator;
import org.openstates.model.Bills;
import org.openstates.model.Committees;
import org.openstates.model.Legislators;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * This is the main class of this package. These methods will load bulkdata
 * into the classes in the model package.
 * 
 * <p>This class requires a ResourceBundle named "openstates.properties" in the
 * class path. The bundle must have an entry for "bulkdatadir" which 
 * points to the directory which holds the bulk data.</p>
 * 
 * <p>
 * For example:<br>
 * <pre>
 * bulkdatadir=c:/tmp/bulkdata
 * </pre></p> 
 *
 */
public final class LoadBulkData extends BulkData {
	private static final Logger logger = Logger.getLogger(LoadBulkData.class.getCanonicalName());
	private static final String billsDirectory = "bills";
	private static final String legislatorsDirectory = "legislators";
	private static final String committeesDirectory = "committees";
	
	/**
	 * Default constructor. Reads the ResourceBundle 
	 * that holds the information for the bulkdata directory. 
	 */
	public LoadBulkData() throws OpenStatesException {
		super(ResourceBundle.getBundle("openstates"));
	}

	/**
	 * Loads all bulk data into model objects. The only data currently included 
	 * in the bulkdata are Bills, Committees, and Legislators.
	 * 
	 * @param fileName - name of bulkdata file that is zipped.
	 * @param timeZone - timezone of the state capitol for this bulkdata.
	 */
	public void load(String fileName, TimeZone timeZone) throws OpenStatesException {
		setLoadParameters(timeZone);
		clearStatics();
		ZipFile zipFile = null;
		String entryName = null;
		try {
			File bulkDataFile = new File(bulkDataDir + fileName);
			logger.fine("Reading bulkdata from " + bulkDataFile.toString());
			zipFile = new ZipFile( bulkDataFile );
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while ( entries.hasMoreElements() ) {
				ZipEntry entry = entries.nextElement();
				if ( entry.isDirectory() ) continue;
				entryName = entry.getName();
				if ( entryName.contains(billsDirectory)  ) {
					Bill bill = mapper.readValue( zipFile.getInputStream(entry), Bill.class );
					Bills.put(bill.bill_id, bill);
				} else if ( entryName.contains(legislatorsDirectory)  ) {
					Legislator legislator = mapper.readValue( zipFile.getInputStream(entry), Legislator.class );
					Legislators.put(legislator.id, legislator );
				} else if ( entryName.contains(committeesDirectory)  ) {
					Committee committee = mapper.readValue( zipFile.getInputStream(entry), Committee.class );
					Committees.put(committee.id, committee);
				} else {
					throw new OpenStatesException(-1, "Cannot determine bulkdata content: " + entryName, null, null, null );
				}
			}
		} catch (JsonParseException e) {
			throw new OpenStatesException(e, entryName, null, null, null);
		} catch (JsonMappingException e) {
			throw new OpenStatesException(e, entryName, null, null, null);
		} catch (IOException e) {
			throw new OpenStatesException(e, entryName, null, null, null);
		} finally {
			if ( zipFile != null ) {
				try {
					zipFile.close();
				} catch (IOException e) {
					throw new OpenStatesException(e, entryName, null, null, null);
				}
			}
		}
	}
	
	/**
	 * 
	 * Load the current term. Bills that are loaded are 
	 * bills in the openstates.zip bulkdata file for which the 
	 * directory path contains the "year" string. For example, pass
	 * "2013" to get all bills that have a path which has "2013" in it.
	 * 
	 * <p>All committees are loaded. There is no filtering on them.</p>
	 *  
	 * <p>Legislators with the legislator.active flag is set to true 
	 * are loaded.</p>
	 * 
	 * @param fileName
	 * @param year
	 * @param timeZone
	 */
	public void loadCurrentTerm(String fileName, String year, TimeZone timeZone ) throws OpenStatesException {
		setLoadParameters(timeZone);
		clearStatics();
		ZipFile zipFile = null;
		String entryName = null;
		try {
			File bulkDataFile = new File(bulkDataDir + fileName);
			logger.fine("Reading bulkdata from " + bulkDataFile.toString());
			zipFile = new ZipFile( bulkDataFile );
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while ( entries.hasMoreElements() ) {
				ZipEntry entry = entries.nextElement();
				if ( entry.isDirectory() ) continue;
				entryName = entry.getName();
				if ( entryName.contains(billsDirectory) && entryName.contains(year) ) {
					Bill bill = mapper.readValue( zipFile.getInputStream(entry), Bill.class );
					Bills.put(bill.bill_id, bill);
				} else if ( entryName.contains(legislatorsDirectory)  ) {
					Legislator legislator = mapper.readValue( zipFile.getInputStream(entry), Legislator.class );
					if ( legislator.active == true ) Legislators.put(legislator.id, legislator );
				} else if ( entryName.contains(committeesDirectory)  ) {
					Committee committee = mapper.readValue( zipFile.getInputStream(entry), Committee.class );
					Committees.put(committee.id, committee);
				}
			}
		} catch (JsonParseException e) {
			throw new OpenStatesException(e, entryName, null, null, null);
		} catch (JsonMappingException e) {
			throw new OpenStatesException(e, entryName, null, null, null);
		} catch (IOException e) {
			throw new OpenStatesException(e, entryName, null, null, null);
		} finally {
			if ( zipFile != null ) {
				try {
					zipFile.close();
				} catch (IOException e) {
					throw new OpenStatesException(e, entryName, null, null, null);
				}
			}
		}
	}
	
	private void clearStatics() {
		Bills.clear();
		Legislators.clear();
		Committees.clear();
	}

}