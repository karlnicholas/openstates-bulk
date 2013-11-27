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

public final class LoadBulkData extends BulkData {
	private static final Logger logger = Logger.getLogger(LoadBulkData.class.getCanonicalName());
	public static final String billsDirectory = "bills";
	public static final String legislatorsDirectory = "legislators";
	public static final String committeesDirectory = "committees";
	
	public LoadBulkData() throws OpenStatesException {
		super(ResourceBundle.getBundle("openstates"));
	}

	public void load(String fileName, TimeZone timeZone) throws OpenStatesException {
		setLoadParameters(timeZone);
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
					throw new OpenStatesException("Cannot determine bulkdata content: " + entryName );
				}
			}
		} catch (JsonParseException e) {
			throw new OpenStatesException(entryName, e);
		} catch (JsonMappingException e) {
			throw new OpenStatesException(entryName, e);
		} catch (IOException e) {
			throw new OpenStatesException(e);
		} finally {
			if ( zipFile != null ) {
				try {
					zipFile.close();
				} catch (IOException e) {
					throw new OpenStatesException(e);
				}
			}
		}
	}
}