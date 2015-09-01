package org.sdrc.evm.service;

import java.text.ParseException;
import java.util.List;

import org.sdrc.evm.model.GoogleMapData;
import org.sdrc.evm.model.SpiderDataCollection;
import org.sdrc.evm.model.UtDataCollection;
import org.sdrc.evm.model.ValueObject;

public interface DashboardService {

	List<ValueObject> fetchIndicators(String param);

	List<ValueObject> fetchTimeFormats(String parentAreaName) throws ParseException;
	
	List<ValueObject> fetchAllSectors(String ic_type);
	
	UtDataCollection fetchData(String indicatorId, String []areaId, String timeperiodId) throws ParseException;
	
//	SpiderDataCollection findSpiderDataByTimePeriod(String sector,String areaId ,String timeperiodId);

	

	SpiderDataCollection findSpiderDataByTimePeriod(String sector,String areaCode, String timeperiodNid, String secondTimeperiodNid);

	List<ValueObject> indicators(String sector);

	ValueObject fetchSelectedGranularity(String sectorName,String parentAreaName) throws Exception;

	List<ValueObject> fetchSubSectors(String sector) throws Exception;

	List<GoogleMapData> fetchGoogleMapData(String sectorName,String indicatorId, String timeperiodId,
			String selectedGranularity,String parentAreaName )throws Exception;

	String fetchImages(String granularityId)throws Exception;

}
