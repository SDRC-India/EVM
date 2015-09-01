package org.sdrc.evm.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sdrc.devinfo.domain.UtAreaEn;
import org.sdrc.devinfo.domain.UtData;
import org.sdrc.devinfo.domain.UtIcIus;
import org.sdrc.devinfo.domain.UtIndicatorClassificationsEn;
import org.sdrc.devinfo.domain.UtIndicatorEn;
import org.sdrc.devinfo.domain.UtTimeperiod;
import org.sdrc.devinfo.repository.IndicatorRepository;
import org.sdrc.devinfo.repository.SectorRepository;
import org.sdrc.devinfo.repository.UtAreaEnRepository;
import org.sdrc.devinfo.repository.UtDataRepository;
import org.sdrc.devinfo.repository.UtTimeperiodRepository;
import org.sdrc.evm.domain.FacilityDetails;
import org.sdrc.evm.model.GoogleMapData;
import org.sdrc.evm.model.SpiderDataCollection;
import org.sdrc.evm.model.SpiderDataModel;
import org.sdrc.evm.model.UtDataCollection;
import org.sdrc.evm.model.UtDataModel;
import org.sdrc.evm.model.ValueObject;
import org.sdrc.evm.repository.FacilityDetailsRepository;
import org.sdrc.evm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;

	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private UtTimeperiodRepository utTimeperiodRepository;

	@Autowired
	private SectorRepository sectorRepository;

	@Autowired
	private UtDataRepository dataRepository;

	@Autowired
	private UtAreaEnRepository areaEnRepository;

	@Autowired
	private FacilityDetailsRepository facilityDetailsRepository;

	private Map<String, Integer> ranks = null;

	private Map<String, List<ValueObject>> dataByTP = null;

	private List<String> topPerformers = null;
	private List<String> bottomPerformers = null;

	@Override
	public List<ValueObject> fetchIndicators(String sector) {
		System.out.println("The selected sector is == > " + sector);
		List<ValueObject> list = new ArrayList<ValueObject>();
		List<UtIndicatorClassificationsEn> utIndicatorClassificationsEnList = sectorRepository.findByIC_Parent_NId(new Integer(sector));
		int listSize = 0;
		for (UtIndicatorClassificationsEn utIndicatorClassificationsEn : utIndicatorClassificationsEnList) {

			int ic_Nid = utIndicatorClassificationsEn.getIC_NId();
			System.out.println("ic_Nid :" + ic_Nid);
			List<Object[]> listofIndicators = indicatorRepository
					.findByIC_Type(ic_Nid);
			
			for (int i = 0; i < listofIndicators.size(); i++) {
				Object[] objects = listofIndicators.get(i);
				if (objects == null || objects.length < 3) {
					continue;
				}
				ValueObject vObject = new ValueObject();
				for (Object obj : objects) {

					if (obj instanceof UtIndicatorEn) {
						UtIndicatorEn utIUS = (UtIndicatorEn) obj;
						System.out.println("Indicator NID  ===>"
								+ utIUS.getIndicator_NId()
								+ "  Indicator Name  ====  "
								+ utIUS.getIndicator_Name() + " ==== ");
						vObject.setKey(Integer.toString(utIUS
								.getIndicator_NId()));
						vObject.setValue(utIUS.getIndicator_Name());
						vObject.setShortName(utIUS.getShort_Name());
					} else if (obj instanceof UtIcIus) {
						UtIcIus utIUS = (UtIcIus) obj;
						System.out.println("IUS NID  ===>" + utIUS.getIUSNId());
						vObject.setDescription(Integer.toString(utIUS
								.getIUSNId()));
					}
				} 
				if(i == listofIndicators.size()-1 && listSize == 0){
					list.add(0,vObject);
				}
				else if (i == listofIndicators.size()-1 && listSize > 0){				
					list.add(listSize,vObject);
				}
				else{
					list.add(vObject);
				}
				
			}
			listSize = list.size();
		}
		List<ValueObject> indList = null;
		if(list!=null){ 
			Collections.sort(list);
			indList = new ArrayList<>(list.size());
			for(int i=list.size()-1;i>=0;i--){
				if(list.get(i).getValue().toString().split(":")[0].contains("E") ){
					indList.add(0,list.get(i));
				}
			}
			for(int i=0;i<list.size();i++){
				if(list.get(i).getValue().toString().split(":")[0].contains("C") ){
					indList.add(list.get(i));
				}
			}
		}
		
		return indList;
	}

	@Override
	public List<ValueObject> indicators(String sector) {
		System.out.println("The selected sector is == > " + sector);
		List<ValueObject> list = new ArrayList<ValueObject>();

		List<Object[]> listofIndicators = indicatorRepository
				.findByIC_Type(new Integer(sector));

		for (int i = 0; i < listofIndicators.size(); i++) {
			Object[] objects = listofIndicators.get(i);
			if (objects == null || objects.length < 3) {
				continue;
			}
			ValueObject vObject = new ValueObject();
			for (Object obj : objects) {

				if (obj instanceof UtIndicatorEn) {
					UtIndicatorEn utIUS = (UtIndicatorEn) obj;
					System.out.println("Indicator NID  ===>"
							+ utIUS.getIndicator_NId()
							+ "  Indicator Name  ====  "
							+ utIUS.getIndicator_Name() + " ==== ");
					vObject.setKey(Integer.toString(utIUS.getIndicator_NId()));
					vObject.setValue(utIUS.getIndicator_Name());
				} else if (obj instanceof UtIcIus) {
					UtIcIus utIUS = (UtIcIus) obj;
					System.out.println("IUS NID  ===>" + utIUS.getIUSNId());
					vObject.setDescription(Integer.toString(utIUS.getIUSNId()));
				}
			}
			list.add(vObject);
		}
		return list;
	}

	@Override
	public List<ValueObject> fetchTimeFormats(String parentAreaName) throws ParseException {
		int sourceNid = Integer.parseInt(applicationMessageSource.getMessage(parentAreaName, null, null));
		List<UtTimeperiod> listofTimeFormats = utTimeperiodRepository.findBySourceNid(sourceNid);
		List<ValueObject> list = new ArrayList<ValueObject>();
		String tp;
		for (UtTimeperiod utTimeperiod : listofTimeFormats) {
			tp = utTimeperiod.getTimePeriod();
			tp = getFormattedTP(tp);
			list.add(new ValueObject(utTimeperiod.getTimePeriod_NId(), tp));
		}

		return list;
	}

	@Override
	public List<ValueObject> fetchAllSectors(String ic_type) {
		// TODO Auto-generated method stub
		List<UtIndicatorClassificationsEn> listofSectors = sectorRepository
				.findByIC_Type(ic_type);
		List<ValueObject> list = new ArrayList<ValueObject>();
		for (UtIndicatorClassificationsEn utIndicatorClasssificationsEn : listofSectors) {
			list.add(new ValueObject(utIndicatorClasssificationsEn.getIC_NId(),
					utIndicatorClasssificationsEn.getIC_Name()));
		}
		return list;

	}

	@Override
	@Transactional
	public UtDataCollection fetchData(String indicatorId, String[] areaCode,
			String timeperiodId) throws ParseException {
		System.out.println("Fetch Data of DashBoard Controller is called");

		if (areaCode != null) {
			for (String s : areaCode)
				System.out.println(" === Area Code === " + s);
		}
		dataByTP = null;
		ranks = null;
		List<Object[]> listData = dataRepository.findData(
				Integer.parseInt("10"), areaCode);

		// This is used to populate the data series for a particular area code
		populateDataByTimePeroid(listData);

		// UtDataCollection utDataCollection = getUtdataCollection(listData,
		// indicatorId, timeperiodId,areaCode);
		UtDataCollection utDataCollection = getUtdataCollection(listData, "10",
				"1", new String[] { "IND010" });

		return utDataCollection;
	}

	public UtDataCollection getUtdataCollection(List<Object[]> dataObjects,
			String indicatorId, String timePeriodNid, String[] area) {

		UtDataCollection collection = new UtDataCollection();

		if (dataObjects != null && !dataObjects.isEmpty()) {
			List<ValueObject> list = new ArrayList<ValueObject>();
			List<ValueObject> dataSeries = new ArrayList<ValueObject>();

			// this will fetch the data for the selected time-period and
			// populate the legend
			list = populateLegends(indicatorId, timePeriodNid, area);
			collection.setLegends(list);
			collection.setTopPerformers(topPerformers);
			collection.setBottomPerformers(bottomPerformers);

			UtData utData = null;
			UtAreaEn utAreaEn = null;
			UtTimeperiod utTimeperiod = null;
			String areaCode = "";
			Double value = null;
			UtDataModel data = null;

			for (Object[] dataObject : dataObjects) {
				utData = (UtData) dataObject[0];
				utAreaEn = (UtAreaEn) dataObject[1];
				utTimeperiod = (UtTimeperiod) dataObject[2];
				value = getFormattedDouble(utData.getData_Value());

				// Check if area code has changed and add the previous data to
				// the collection
				if (!areaCode.equalsIgnoreCase(utAreaEn.getArea_ID())) {

					// Skip adding the data model collection for the first
					// iteration.
					if (!areaCode.equals("")) {
						collection.dataCollection.add(data);
					}

					// Change the area code and create a new data model for the
					// new area.
					areaCode = utAreaEn.getArea_ID();
					data = new UtDataModel();

					dataSeries = new ArrayList<ValueObject>();
					data.setAreaCode(areaCode);
					data.setAreaName(utAreaEn.getArea_Name());

					// check if the time-period is the selected time period
					// and set the data value and the rank for the particular
					// area.
					if (utTimeperiod != null
							&& timePeriodNid != null
							&& (utTimeperiod.getTimePeriod_NId() == Integer
									.parseInt(timePeriodNid))) {

						System.out
								.println("+++++ the time period is the selected time period for area code  "
										+ areaCode);
						data.setValue(value != null ? Double.toString(value)
								: null);
						data.setRank(ranks != null
								&& ranks.get(areaCode) != null ? Integer
								.toString(ranks.get(areaCode)) : null);

						// set the css class for the area for the selected
						// time-period
						if (list != null) {
							setCssForDataModel(list, data, value);
						}
					}

					// get the area-time_period-data_value map and populate the
					// data series
					if (dataSeries != null && dataSeries.isEmpty()) {

						System.out
								.println("+++++ adding the data series for area code "
										+ areaCode);
						List<ValueObject> dataValues = dataByTP.get(areaCode);
						if (dataValues != null) {
							for (ValueObject dataValue : dataValues) {
								dataSeries.add(new ValueObject(dataValue
										.getKey(),
										Double.toString((Double) dataValue
												.getValue())));
							}

							// calculate the % change by comparing the data
							// value with
							// the last available time-period
							BigDecimal percentChange = dataValues.size() >= 2 ? new BigDecimal(
									(Double) (dataValues.get(dataValues.size() - 1)
											.getValue())
											- (Double) (dataValues
													.get(dataValues.size() - 2)
													.getValue())).setScale(2,
									BigDecimal.ROUND_HALF_UP)
									: null;
							data.setPercentageChange(percentChange != null ? percentChange
									.toString() : null);
							data.setIsPositiveTrend(percentChange != null ? percentChange
									.doubleValue() > 0 ? isPositveIndicator(indicatorId) ? true
									: false
									: isPositveIndicator(indicatorId) ? false
											: true
									: false);
						}
						data.setDataSeries(dataSeries);
					}

					data.setUnit("percentage");
				}
				// If the area code did not change, check if the time-period is
				// the selected time period
				// and set the data value and the rank for the particular area.
				else {
					if (utTimeperiod != null
							&& timePeriodNid != null
							&& (utTimeperiod.getTimePeriod_NId() == Integer
									.parseInt(timePeriodNid))) {

						data.setValue(value != null ? Double.toString(value)
								: null);
						data.setRank(ranks != null
								&& ranks.get(areaCode) != null ? Integer
								.toString(ranks.get(areaCode)) : null);

						// set the css class for the area for the selected
						// time-period
						if (list != null) {
							setCssForDataModel(list, data, value);
						}
					}
				}
			}
			collection.dataCollection.add(data);
		}
		return collection;
	}

	private void setCssForDataModel(List<ValueObject> list, UtDataModel data,
			Double value) {

		for (int index = 0; index < list.size(); index++) {
			ValueObject vObject = list.get(index);
			String[] vArray = vObject != null ? ((String) vObject.getKey())
					.split(" - ") : null;
			if (vArray != null
					&& new Double(vArray[0]).doubleValue() <= value
							.doubleValue()
					&& value.doubleValue() <= new Double(vArray[1])
							.doubleValue()) {
				switch (index) {
				case 0:
					data.setCssClass(Constants.Slices.FIRST_SLICE);
					break;
				case 1:
					data.setCssClass(Constants.Slices.SECOND_SLICE);
					break;
				case 2:
					data.setCssClass(Constants.Slices.THIRD_SLICE);
					break;
				case 3:
					data.setCssClass(Constants.Slices.FOUTRH_SLICE);
					break;
				}
			}
		}

	}

	private boolean isPositveIndicator(String indicatorId) {
		// TODO Auto-generated method stub
		return true;
	}

	private List<ValueObject> populateLegends(String indicatorId,
			String timePeriodNid, String[] areaCode) {

		// TO DO: make this configuration based.
		int range = 4;
		Double minDataValue = null;
		Double maxDataValue = null;
		String firstslices = Constants.Slices.FIRST_SLICE;
		String secondslices = Constants.Slices.SECOND_SLICE;
		String thirdslices = Constants.Slices.THIRD_SLICE;
		String fourthslices = Constants.Slices.FOUTRH_SLICE;
		List<ValueObject> list = new ArrayList<ValueObject>();

		// fetch the data for the selected time-period
		List<Object[]> data = dataRepository.findDataByTimePeriod(
				Integer.parseInt(indicatorId), Integer.parseInt(timePeriodNid),
				areaCode);
		if (data != null && !data.isEmpty()) {
			minDataValue = getFormattedDouble(((UtData) data.get(0)[0])
					.getData_Value());
			maxDataValue = getFormattedDouble(((UtData) data
					.get(data.size() - 1)[0]).getData_Value());
			Double difference = (maxDataValue - minDataValue) / range;
			if (difference == 0) {
				String firstSliceValue = Double.toString(minDataValue)
						+ " - "
						+ Double.toString(getFormattedDouble(minDataValue
								+ difference));
				list.add(new ValueObject(firstSliceValue, firstslices));
			} else {
				String firstSliceValue = Double.toString(minDataValue)
						+ " - "
						+ Double.toString(getFormattedDouble(minDataValue
								+ difference));

				String secondSliceValue = Double
						.toString(getFormattedDouble(minDataValue + difference
								+ 0.01))
						+ " - "
						+ Double.toString(getFormattedDouble(minDataValue + 2
								* difference));
				String thirdSliceValue = Double
						.toString(getFormattedDouble(minDataValue + 2
								* difference + 0.01))
						+ " - "
						+ Double.toString(getFormattedDouble(minDataValue + 3
								* difference));
				String fourthSliceValue = Double
						.toString(getFormattedDouble(minDataValue + 3
								* difference + 0.01))
						+ " - "
						+ Double.toString(getFormattedDouble(minDataValue + 4
								* difference));
				list.add(new ValueObject(firstSliceValue, firstslices));
				list.add(new ValueObject(secondSliceValue, secondslices));
				list.add(new ValueObject(thirdSliceValue, thirdslices));
				list.add(new ValueObject(fourthSliceValue, fourthslices));
			}
		}

		// calculates the rank for the area codes for the selected time-period
		populateRank(data);

		return list != null && !list.isEmpty() ? list : null;

	}

	private void populateRank(List<Object[]> data) {

		ranks = new HashMap<String, Integer>();
		topPerformers = new ArrayList<String>();
		bottomPerformers = new ArrayList<String>();
		if (data != null && !data.isEmpty()) {
			int rank = 0;
			double dataValue = 0.0;
			UtAreaEn utArea = null;
			UtData utData = null;
			for (int index = data.size() - 1; index >= 0; index--) {
				utData = (UtData) data.get(index)[0];
				utArea = (UtAreaEn) data.get(index)[1];

				// populate the performance by area list
				if (data.size() >= 6) {
					if (index >= data.size() - 3) {

						topPerformers.add(utArea.getArea_Name() + " - "
								+ utData.getData_Value());
					}
					if (index < 3) {
						bottomPerformers.add(utArea.getArea_Name() + " - "
								+ utData.getData_Value());
					}
				} else if (index <= 2) {
					topPerformers.add(utArea.getArea_Name() + " - "
							+ utData.getData_Value());
				} else {
					bottomPerformers.add(utArea.getArea_Name() + " - "
							+ utData.getData_Value());
				}

				if (dataValue == utData.getData_Value()
						&& index != data.size() - 1) {
					ranks.put(utArea.getArea_ID(), rank);
					continue;
				}
				rank = data.size() - index;
				dataValue = utData.getData_Value();

				ranks.put(utArea.getArea_ID(), rank);

			}
		}
		// TODO Auto-generated method stub

	}

	private void populateDataByTimePeroid(List<Object[]> listData)
			throws ParseException {
		// TODO Auto-generated method stub
		dataByTP = new HashMap<String, List<ValueObject>>();
		if (listData != null && !listData.isEmpty()) {
			List<ValueObject> vList = null;
			String areaCode = "";
			UtData utData = null;
			UtAreaEn utAreaEn = null;
			UtTimeperiod utTimeperiod = null;
			for (Object[] dataObject : listData) {
				utData = (UtData) dataObject[0];
				utAreaEn = (UtAreaEn) dataObject[1];
				utTimeperiod = (UtTimeperiod) dataObject[2];

				if (!(areaCode.equalsIgnoreCase(utAreaEn.getArea_ID()))) {
					if (!areaCode.equals("")) {
						dataByTP.put(areaCode, vList);
					}
					areaCode = utAreaEn.getArea_ID();
					vList = new ArrayList<ValueObject>();
					vList.add(new ValueObject(getFormattedTP(utTimeperiod
							.getTimePeriod()), utData.getData_Value()));

				} else {
					vList.add(new ValueObject(getFormattedTP(utTimeperiod
							.getTimePeriod()), utData.getData_Value()));

				}
			}
			dataByTP.put(areaCode, vList);
		}

	}

	public String getFormattedTP(String timePeriod) throws ParseException {

		Date date = null;
		try {
			date = timePeriod != null ? new SimpleDateFormat("yyyy.MM")
					.parse(timePeriod) : null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = timePeriod != null ? new SimpleDateFormat("yyyy.MMM")
					.parse(timePeriod) : null;
		}
		String formattedTP = date != null ? new SimpleDateFormat("MMM yyyy")
				.format(date) : null;

		return formattedTP;
	}

	public Double getFormattedDouble(Double value) {
		Double formattedValue = value != null ? new BigDecimal(value).setScale(
				2, BigDecimal.ROUND_HALF_UP).doubleValue() : null;
		return formattedValue;
	}

	@Override
	public SpiderDataCollection findSpiderDataByTimePeriod(String sector,
			String areaCode, String timeperiodNid, String secondTimeperiodNid) {

		ArrayList<Integer> timePeriodIdList = new ArrayList<Integer>();
		timePeriodIdList.add(new Integer(timeperiodNid));
		if (secondTimeperiodNid != null && secondTimeperiodNid != "") {
			timePeriodIdList.add(new Integer(secondTimeperiodNid));
		}

		// Populate the data collection.
		SpiderDataCollection spiderDataCollection = new SpiderDataCollection();
		// Fetch the indicators and its corresponding IUS for the selected
		// sector.
		List<ValueObject> vObjects = indicators(sector);

		// Populate the array indicator nid's based on the selected sector.
		Integer[] indicatorNids = vObjects != null && vObjects.size() > 0 ? new Integer[vObjects
				.size()] : null;
		for (int index = 0; index < vObjects.size(); index++) {
			System.out.println("Spider Data + "
					+ vObjects.get(index).getDescription());
			indicatorNids[index] = new Integer(vObjects.get(index)
					.getDescription());
		}

		for (int timePeriodId : timePeriodIdList) {

			List<Object[]> dataValues = dataRepository
					.findSpiderDataByTimePeriod(indicatorNids, timePeriodId,
							areaCode);

			List<SpiderDataModel> spdm = new ArrayList<SpiderDataModel>();

			if (dataValues != null && !dataValues.isEmpty()) {
				for (int i = 0; i < dataValues.size(); i++) {

					SpiderDataModel dataModel = new SpiderDataModel();
					UtData utData = null;
					Object[] dataObject = dataValues.get(i);
					utData = (UtData) dataObject[0];
					System.out.println("Value Object "
							+ vObjects.get(i).getValue() + " Data Value "
							+ utData.getData_Value());
//					dataModel.setAxis((String) vObjects.get(i).getValue());
//					dataModel.setValue(utData.getData_Value().toString());
					
					for (ValueObject obj : vObjects) {

						if (obj.getKey().equals(new Integer(utData.getIndicator_NId()).toString())) {
							dataModel.setAxis((String) obj.getValue());
							dataModel.setValue(utData.getData_Value().toString());
						}
					}
					
					if(i == dataValues.size()-1){
						spdm.add(0,dataModel);
					}else{
					spdm.add(dataModel);}
				}
				spiderDataCollection.dataCollection.add(spdm);
			}
		}

		return spiderDataCollection;
	}

	@Override
	public List<ValueObject> fetchSubSectors(String sector) throws Exception {

		List<ValueObject> list = new ArrayList<ValueObject>();
		List<UtIndicatorClassificationsEn> utIndicatorClassificationsEnList = sectorRepository
				.findByIC_Parent_NId(new Integer(sector));

		for (UtIndicatorClassificationsEn utIndicatorClassificationsEn : utIndicatorClassificationsEnList) {
			ValueObject vObject = new ValueObject();
			int ic_Nid = utIndicatorClassificationsEn.getIC_NId();
			String ic_Name = utIndicatorClassificationsEn.getIC_Name();
			System.out.println("ic_Name :" + ic_Name);
			vObject.setKey(Integer.toString(ic_Nid));
			vObject.setValue(ic_Name);
			list.add(vObject);
		}
		return list;
	}

	@Override
	public ValueObject fetchSelectedGranularity(String sectorName,String parentAreaName)
			throws Exception {
		UtAreaEn areaEn = areaEnRepository.findAreaIdbyAreaName(sectorName,parentAreaName);
		ValueObject vObject = new ValueObject();
		vObject.setKey(areaEn.getArea_ID());
		vObject.setValue(sectorName);
		return vObject;
	}

	@Override
	public List<GoogleMapData> fetchGoogleMapData(String sectorName,
			String indicatorId, String timeperiodId, String selectedGranularity,String parentAreaName)
			throws Exception {
		String bellowPushPin = new String();
		String mediumPushPin = new String();
		String abovePushPin = new String();
		int IUSNId = Integer.parseInt(indicatorId);
		int timeNid = Integer.parseInt(timeperiodId);
		List<GoogleMapData> list = new ArrayList<GoogleMapData>();
//		int sourceNid = sectorRepository.findIcNIdbySourceType("SR");
		int sourceNid = Integer.parseInt(applicationMessageSource.getMessage(parentAreaName, null, null));

		List<Integer> areaNidList = areaEnRepository
				.findAreaNidbyGranularity(selectedGranularity);

		switch (sectorName) {
		case "SVS":
			bellowPushPin = "resources/images/svs/SVS_red.png";
			mediumPushPin = "resources/images/svs/SVS_orange.png";
			abovePushPin = "resources/images/svs/SVS_green.png";
			break;
		case "RVS":
			bellowPushPin = "resources/images/rvs/RVS_red.png";
			mediumPushPin = "resources/images/rvs/RVS_orange.png";
			abovePushPin = "resources/images/rvs/RVS_green.png";
			break;
		case "DVS":
			bellowPushPin = "resources/images/dvs/DVS_red.png";
			mediumPushPin = "resources/images/dvs/DVS_orange.png";
			abovePushPin = "resources/images/dvs/DVS_green.png";
			break;
		case "Health facility":
			bellowPushPin = "resources/images/hf/HF_red.png";
			mediumPushPin = "resources/images/hf/HF_orange.png";
			abovePushPin = "resources/images/hf/HF_green.png";
			break;
		}

		Integer[] arrayAreaNid = new Integer[areaNidList.size()];
		for (int i = 0; i < areaNidList.size(); i++) {
			arrayAreaNid[i] = areaNidList.get(i);
		}

		List<Object[]> objects = dataRepository.findDataAndAreaId(IUSNId,
				arrayAreaNid, timeNid, sourceNid);

		for (Object[] obj : objects) {

			GoogleMapData mapData = new GoogleMapData();
			if (obj[0] instanceof UtData) {
				String pushPin = new String();
				UtData utData = (UtData) obj[0];
				System.out.println("utData.getData_Value() 1----->"
						+ utData.getData_Value());
				// Icon icon = new Icon();
				// icon.setPath("google.maps.SymbolPath.FORWARD_CLOSED_ARROW");
				// icon.setStrokeColor("green");
				double dataValue = utData.getData_Value();
				System.out.println("===================================="
						+ dataValue
						+ "=========================================");
				if (dataValue >= 80) {
					pushPin = abovePushPin;
				} else if (dataValue >= 61 && dataValue < 80) {
					pushPin = mediumPushPin;
				} else if (dataValue >= 0 && dataValue <= 60) {
					pushPin = bellowPushPin;
				} 
				mapData.setId(utData.getArea_NId());
				mapData.setDataValue(dataValue);
				mapData.setShowWindow(false);
				// mapData.setIcon(icon);
				mapData.setIcon(pushPin);
			}
			if (obj[1] instanceof UtAreaEn) {
				UtAreaEn utAreaEn = (UtAreaEn) obj[1];
				System.out
						.println("getArea_ID 2----->" + utAreaEn.getArea_ID());
				mapData.setAreaID(utAreaEn.getArea_ID());
				FacilityDetails facilityDetails = facilityDetailsRepository
						.findByAreaId(utAreaEn.getArea_ID());
				if (facilityDetails == null)
					continue;
				mapData.setTitle(facilityDetails.getAreaName());
				
				if(facilityDetails.getAltitude() != null){
				mapData.setAltitude(facilityDetails.getAltitude());}
				
				if(facilityDetails.getLatitude() != null){
				mapData.setLatitude(facilityDetails.getLatitude());}
				
				if(facilityDetails.getLongitude() != null){
				mapData.setLongitude(facilityDetails.getLongitude());}
				
				StringBuffer finalList = new StringBuffer();
				if (facilityDetails.getImages() != null) {
					String[] listOfImages = facilityDetails.getImages().split(",");
					for (String img : listOfImages) {
						finalList.append("resources/images/facilities/"+img+",");
					}
					if (finalList.length() > 0) {
						finalList.replace(finalList.length() - 1,
								finalList.length(), "");
					}
					mapData.setImages(finalList.toString());
				}
			}
			list.add(mapData);
		}
		return list;
	}

	@Override
	public String fetchImages(String granularityId) throws Exception {

		FacilityDetails fd= facilityDetailsRepository.findByAreaId(granularityId);
		StringBuffer finalList = new StringBuffer();
		if (fd.getImages() != null) {
			String[] listOfImages = fd.getImages().split(",");
			for (String img : listOfImages) {
				finalList.append("resources/images/facilities/"+img+",");
			}
			if (finalList.length() > 0) {
				finalList.replace(finalList.length() - 1,
						finalList.length(), "");
			}
		}
		return finalList.toString();
	}

}
