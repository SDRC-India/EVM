package org.sdrc.evm.web;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.devinfo.domain.UtIcIus;
import org.sdrc.devinfo.domain.UtIndicatorClassificationsEn;
import org.sdrc.devinfo.domain.UtIndicatorEn;
import org.sdrc.devinfo.repository.SectorRepository;
import org.sdrc.evm.model.GoogleMapData;
import org.sdrc.evm.model.SpiderDataCollection;
import org.sdrc.evm.model.UtDataCollection;
import org.sdrc.evm.model.ValueObject;
import org.sdrc.evm.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

	private final DashboardService dashboardService;

	@Autowired
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@Autowired
	private SectorRepository sectorRepository;

	@RequestMapping(value = { "/api/indicators" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> fetchIndicators(
			@RequestParam("sector") String sector) throws Exception {
		return dashboardService.fetchIndicators(sector);
	}
	
	@RequestMapping(value = { "/api/subSectors" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> subSectors(@RequestParam("sector") String sector) throws Exception {
		return dashboardService.fetchSubSectors(sector);
	}
	
	@RequestMapping(value = { "/api/selectedGranularity" }, method = { RequestMethod.GET })
	@ResponseBody
	public ValueObject selectedGranularity(@RequestParam("sectorName") String sectorName,@RequestParam("parentAreaName") String parentAreaName) throws Exception {
		return dashboardService.fetchSelectedGranularity(sectorName,parentAreaName);
	}
	
	@RequestMapping(value = { "/api/googleMapData" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<GoogleMapData> googleMapData(@RequestParam("sectorName") String sectorName,@RequestParam("indicatorId") String indicatorId,
			   @RequestParam("timeperiodId") String timeperiodId,
			   @RequestParam("selectedGranularity") String selectedGranularity,
			   @RequestParam("parentAreaName") String parentAreaName) throws Exception {
		return dashboardService.fetchGoogleMapData(sectorName,indicatorId,timeperiodId,selectedGranularity,parentAreaName);
	}

	@RequestMapping(value = { "/api/timeformats" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> fetchTimeFormats(@RequestParam("parentAreaName") String parentAreaName) throws Exception {
		 return dashboardService.fetchTimeFormats(parentAreaName);
	}

	@RequestMapping(value = { "/api/sectors" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> fetchAllSectors() throws Exception {
		return dashboardService.fetchAllSectors("SC");
	}
	
	@RequestMapping(value = { "/api/getImages" }, method = { RequestMethod.GET })
	@ResponseBody
	public String getImages(@RequestParam("granularityId") String granularityId) throws Exception {
		return dashboardService.fetchImages(granularityId);
	}

	@RequestMapping(value = { "/api/data" }, method = { RequestMethod.GET })
	@ResponseBody
	public UtDataCollection fetchData(
			@RequestParam(required = false) String indicatorId,
			@RequestParam(required = false) String areaId,
			@RequestParam(required = false) String timeperiodId)
			throws Exception {

		UtDataCollection valList = new UtDataCollection();
		System.out.println("Checking the parameter values for UT DATA ====> "
				+ indicatorId + "  ==  " + areaId + "  ==  " + timeperiodId);
		if (indicatorId != null && timeperiodId != null) {
			String[] arr = null;
			if (areaId == null || "IND021".equals(areaId)) {
				arr = new String[3];
				arr[0] = "IND021001";
				arr[1] = "IND021002";
				arr[2] = "IND021003";
			} else {
				arr = new String[1];
				arr[0] = areaId;
			}
			System.out
					.println("Checking the parameter values for UT DATA ====> "
							+ indicatorId + "  ==  " + arr[0] + "  ==  "
							+ timeperiodId);
			valList = dashboardService
					.fetchData(indicatorId, arr, timeperiodId);
		}

		return valList;
	}

	@RequestMapping(value = { "/api/spiderData" }, method = { RequestMethod.GET })
	@ResponseBody
	public SpiderDataCollection fetchSpiderData(
			@RequestParam(required = false) String sector,
			@RequestParam(required = false) String areaId,
			@RequestParam(required = false) String timeperiodId,
			@RequestParam(required = false) String secondTimeperiodId)
			throws Exception {

		//To do : Remove this hardcoded area Id
		//areaId = areaId;

		//Populate the data collection.
		SpiderDataCollection spiderDataCollection = new SpiderDataCollection();
		
		//Fetch the data for the list of IUS Nids and the current TP
		spiderDataCollection= dashboardService.findSpiderDataByTimePeriod(
				sector, areaId, timeperiodId,secondTimeperiodId);

		return spiderDataCollection;
	}
	
}