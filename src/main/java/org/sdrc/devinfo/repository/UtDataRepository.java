package org.sdrc.devinfo.repository;

import java.util.List;

import org.sdrc.devinfo.domain.UtData;
import org.sdrc.devinfo.domain.VEsamikshaAreaMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface UtDataRepository{

	List<Object []> findData(Integer indicatorId, String []areaCode);
	List<Object []> findDataByTimePeriod(Integer indicatorId,Integer timePeriodNid,String []areaCode);

	@Transactional
	void save(UtData data);
	
	UtData findByIUSNIdAndAreaNidAndTimePeriodNidAndSourcNid(int IUSNId,int areaNid,int timeNid,int sourceNid)throws DataAccessException;
	
	void updateDataValue(Double data_Value,int IUSNId,int areaNid,int timeNid,int sourceNid) throws DataAccessException;
	
	List<Object[]> findByIUSNIdAndTimePeriodNidAndSourceNidAndAreaLevel(int IUSNId,int timeNid,int sourceNid,int areaLevel) throws DataAccessException;
	
	List<Object[]> findDataValueByAreaLevel(int IUSNId,int timeNid,int sourceNid,int areaLevel) throws DataAccessException;
	
	Double getDataValueForBlock(int ius_nid,int timeperiod_nid,int source_nid,int area_NId);
	Double getDataValueForDistrict(int ius_nid, Integer timeperiod_nid,int source_nid, String areaCode);
	
	Double getAggregatedDataValueByAreaCode(int iusNid,int timePeriodNid,int sourceNid,String areaId) throws DataAccessException; 
	
	List<VEsamikshaAreaMapper> findByArea();
	List<Object[]> findSpiderDataByTimePeriod(Integer[] indicatorNids,
			Integer timeperiodId, String areaCode);
	
//	List<Object[]> findDataAndAreaId(int IUSNId, int[] arrayAreaNid,
//			int timeNid, int sourceNid) throws DataAccessException;
	List<Object[]> findDataAndAreaId(int IUSNId, Integer[] arrayAreaNid,
			int timeNid, int sourceNid) throws DataAccessException;
	
	
}
