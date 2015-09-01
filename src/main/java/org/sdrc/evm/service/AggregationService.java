package org.sdrc.evm.service;


public interface AggregationService {
//	void doPercentAggregate();
//	void updateUtData(Double dataValue,int iusNid,int areaNid,int timeNid,int sourceNid)throws DataAccessException;
//	void doRankAggregateByAreaLevel();
//	void doRankAggregateByArea();
//	List<Map<String, Object>> getIndicatorInfo(String configuration) throws Exception;
//	List<Error> doAggregateByArea(String areaId,int timePeriodNid,int sourceNid) throws DataAccessException;
//	AggregationInfo getAggregationInfo(UtIndicatorUnitSubgroup ius)throws DataAccessException;
//	UtData setUtData(UtIndicatorUnitSubgroup ius,int areaNid,int timePeriodNid,int sourceNid,Double dataValue);
//	void saveUtData(UtData data)throws DataAccessException;
//	List<UtTimeperiod> getTimePeriodByLimit(int rowSize) throws DataAccessException;
	void doAggregateByForm();
	void insertAggregationDetails();
	
	
}
