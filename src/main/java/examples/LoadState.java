package examples;

import java.util.TimeZone;
import java.util.logging.Logger;

import org.openstates.api.OpenStatesException;
import org.openstates.bulkdata.LoadBulkData;
import org.openstates.model.Bills;
import org.openstates.model.Committees;
import org.openstates.model.Legislators;

public class LoadState {
	private static final Logger logger = Logger.getLogger(LoadState.class.getCanonicalName());
	private static LoadBulkData bulkData;
//    private static Runtime runtime;
//    private static int mb = 1024*1024;
	
	public static void main(String[] args) throws OpenStatesException {
		bulkData = new LoadBulkData();
//		runtime = Runtime.getRuntime();

		test( "2013-10-01-dc-json.zip", "US/Eastern" );
		test( "2013-10-01-ky-json.zip", "US/Eastern" );		
		test( "2013-10-07-al-json.zip", "US/Central" );
		test( "2013-10-07-ak-json.zip", "US/Alaska" );
		test( "2013-10-07-ar-json.zip", "US/Central" );
		test( "2013-10-07-az-json.zip", "US/Mountain" );
		test( "2013-10-07-ca-json.zip", "US/Pacific" );
		test( "2013-10-07-co-json.zip", "US/Mountain" );
		test( "2013-10-07-de-json.zip", "US/Eastern" );
		test( "2013-10-07-fl-json.zip", "US/Eastern" );
		test( "2013-10-07-ga-json.zip", "US/Eastern" );
		test( "2013-10-07-ia-json.zip", "US/Central" );
//		test( "2013-10-07-il-json.zip", "US/Central" );
		test( "2013-10-07-ks-json.zip", "US/Central" );
//		test( "2013-10-07-la-json.zip", "US/Central" );
		test( "2013-10-07-md-json.zip", "US/Eastern" );
		test( "2013-10-07-me-json.zip", "US/Eastern" );
		test( "2013-10-07-ms-json.zip", "US/Central" );
		test( "2013-10-07-nd-json.zip", "US/Central" );		
		test( "2013-10-07-ne-json.zip", "US/Central" );
		test( "2013-10-07-nh-json.zip", "US/Eastern" );
		test( "2013-10-07-nm-json.zip", "US/Mountain" );		
		test( "2013-10-07-nv-json.zip", "US/Pacific" );		
		test( "2013-10-08-nc-json.zip", "US/Eastern" );
		test( "2013-10-08-ny-json.zip", "US/Eastern" );
		test( "2013-10-08-oh-json.zip", "US/Eastern" );
		test( "2013-10-09-ct-json.zip", "US/Eastern" );
		test( "2013-10-09-hi-json.zip", "Pacific/Honolulu" );
		test( "2013-10-09-id-json.zip", "America/Hawaii-Aleutian" );
		test( "2013-10-09-in-json.zip", "US/Eastern" );
		test( "2013-10-09-ma-json.zip", "US/Eastern" );
		test( "2013-10-09-mo-json.zip", "US/Central" );
		test( "2013-10-09-mt-json.zip", "US/Mountain" );
		test( "2013-10-09-ok-json.zip", "US/Central" );
		test( "2013-11-01-mi-json.zip", "US/Eastern" );
//		test( "2013-11-01-mn-json.zip", "US/Central" );
		test( "2013-11-01-nj-json.zip", "US/Eastern" );
		test( "2013-10-07-ri-json.zip", "US/Eastern" );
		test( "2013-10-08-or-json.zip", "US/Pacific" );
		test( "2013-10-09-pr-json.zip", "US/Atlantic" );
		test( "2013-10-08-pa-json.zip", "US/Eastern" );
		test( "2013-10-08-sd-json.zip", "US/Central" );
		test( "2013-10-07-sc-json.zip", "US/Eastern" );
		test( "2013-10-08-ut-json.zip", "US/Mountain" );
		test( "2013-10-07-tn-json.zip", "US/Central" );
		test( "2013-10-08-vt-json.zip", "US/Eastern" );
		test( "2013-10-08-wi-json.zip", "US/Central" );
		test( "2013-10-07-wy-json.zip", "US/Mountain" );
		test( "2013-10-08-tx-json.zip", "US/Central" );
		test( "2013-10-08-va-json.zip", "US/Eastern" );

	}
	
	private static void test(String bulkDataFile, String timeZone ) throws OpenStatesException {
		logger.info("Starting:" + bulkDataFile );
		bulkData.load( bulkDataFile, TimeZone.getTimeZone(timeZone) );
/*		
		logger.info("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
		logger.info("Free Memory:" + runtime.freeMemory() / mb);
		logger.info("Total Memory:" + runtime.totalMemory() / mb);
		logger.info("Max Memory:" + runtime.maxMemory() / mb);
*/		
		Bills.clear();
		Legislators.clear();
		Committees.clear();
		logger.info("Done:" + bulkDataFile );
		
	}

}
