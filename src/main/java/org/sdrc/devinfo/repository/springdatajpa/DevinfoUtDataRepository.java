package org.sdrc.devinfo.repository.springdatajpa;

import java.util.List;

import org.sdrc.devinfo.domain.UtData;
import org.sdrc.devinfo.domain.VEsamikshaAreaMapper;
import org.sdrc.devinfo.repository.UtDataRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DevinfoUtDataRepository extends UtDataRepository,
		Repository<UtData, Long> {
	@Override
	@Query("SELECT utData , utArea , utTimePeriod FROM UtAreaEn utArea,UtData utData, UtTimeperiod utTimePeriod "
			+ "WHERE utArea.area_NId = utData.area_NId AND "
			+ "utData.timePeriod_NId = utTimePeriod.timePeriod_NId AND "
			+ "utArea.area_Parent_NId "
			+ "IN "
			+ "( SELECT ar.area_NId from UtAreaEn ar WHERE ar.area_ID IN (:areaCode) ) AND "
			+ "utData.IUSNId = :indicatorId "
			+ "ORDER BY utArea.area_ID,utTimePeriod.timePeriod")
	public List<Object[]> findData(@Param("indicatorId") Integer indicatorId,
			@Param("areaCode") String[] areaCode);

	@Override
	@Query("SELECT utData , utArea , utTimePeriod FROM UtAreaEn utArea,UtData utData, UtTimeperiod utTimePeriod "
			+ "WHERE utArea.area_NId = utData.area_NId AND "
			+ "utData.timePeriod_NId = utTimePeriod.timePeriod_NId AND"
			+ " utArea.area_Parent_NId "
			+ "IN "
			+ "( SELECT ar.area_NId from UtAreaEn ar WHERE ar.area_ID IN (:areaCode) ) AND "
			+ " utData.timePeriod_NId = :timeperiodId AND"
			+ " utData.IUSNId = :indicatorId " + "ORDER BY utData.data_Value")
	public List<Object[]> findDataByTimePeriod(
			@Param("indicatorId") Integer indicatorId,
			@Param("timeperiodId") Integer timeperiodId,
			@Param("areaCode") String[] areaCode);

	@Query("select area.area_ID,area.area_Name, ar.area_ID, ar.area_Name from UtAreaEn area , UtAreaEn ar where ar.area_NId = area.area_Parent_NId")
	public List<Object[]> findByAreaCode();

	@Override
	@Query("SELECT data FROM UtData data WHERE data.IUSNId=:IUSNId "
			+ "AND data.area_NId=:area_NId "
			+ "AND data.timePeriod_NId=:timePeriod_NId "
			+ "AND data.source_NId=:source_NId")
	UtData findByIUSNIdAndAreaNidAndTimePeriodNidAndSourcNid(
			@Param("IUSNId") int IUSNId, @Param("area_NId") int areaNid,
			@Param("timePeriod_NId") int timeNid,
			@Param("source_NId") int sourceNid) throws DataAccessException;

	@Override
	@Modifying
	@Query("UPDATE UtData data SET data.data_Value=:data_Value WHERE data.IUSNId=:IUSNId "
			+ "AND data.area_NId=:area_NId AND data.timePeriod_NId=:timePeriod_NId "
			+ "AND data.source_NId=:source_NId")
	@Transactional
	void updateDataValue(@Param("data_Value") Double data_Value,
			@Param("IUSNId") int IUSNId, @Param("area_NId") int areaNid,
			@Param("timePeriod_NId") int timeNid,
			@Param("source_NId") int sourceNid) throws DataAccessException;

	@Override
	@Query("SELECT utData , utArea  FROM UtAreaEn utArea,UtData utData "
			+ " WHERE utArea.area_NId = utData.area_NId "
			+ " AND utArea.area_Level = :area_Level "
			+ " AND utData.timePeriod_NId = :timePeriod_NId "
			+ " AND utData.IUSNId = :IUSNId "
			+ " AND utData.source_NId=:source_NId ORDER BY utData.data_Value")
	List<Object[]> findByIUSNIdAndTimePeriodNidAndSourceNidAndAreaLevel(
			@Param("IUSNId") int IUSNId, @Param("timePeriod_NId") int timeNid,
			@Param("source_NId") int sourceNid,
			@Param("area_Level") int areaLevel) throws DataAccessException;

	@Override
	@Query("SELECT utArea.area_Parent_NId,utArea.area_NId,utArea.area_Name,utData.data_Value FROM UtAreaEn utArea,UtData utData "
			+ " WHERE utArea.area_NId = utData.area_NId "
			+ " AND utArea.area_Level = :area_Level "
			+ " AND utData.timePeriod_NId = :timePeriod_NId "
			+ " AND utData.IUSNId = :IUSNId "
			+ " AND utData.source_NId=:source_NId ORDER BY utData.data_Value")
	List<Object[]> findDataValueByAreaLevel(@Param("IUSNId") int IUSNId,
			@Param("timePeriod_NId") int timeNid,
			@Param("source_NId") int sourceNid,
			@Param("area_Level") int areaLevel) throws DataAccessException;

	@Override
	@Query("SELECT data_Value FROM UtData WHERE IUSNId=:ius_nid AND timePeriod_NId=:timeperiod_nid AND source_NId=:source_nid AND area_NId=:area_NId")
	Double getDataValueForBlock(@Param("ius_nid") int ius_nid,
			@Param("timeperiod_nid") int timeperiod_nid,
			@Param("source_nid") int source_nid, @Param("area_NId") int area_NId);
	
	@Override
	@Query("SELECT u.data_Value FROM UtData u WHERE u.IUSNId=:ius_nid AND u.timePeriod_NId=:timeperiod_nid AND u.source_NId=:source_nid AND u.area_NId=(SELECT a.area_NId From UtAreaEn a where a.area_ID=:areaCode)")
	Double getDataValueForDistrict(@Param("ius_nid")int ius_nid,@Param("timeperiod_nid") Integer timeperiod_nid,@Param("source_nid")int source_nid,@Param("areaCode") String areaCode);
	
	@Override
	@Query("SELECT SUM(utData.data_Value) FROM UtData utData "
			+ " WHERE utData.area_NId IN (SELECT utArea.area_NId FROM UtAreaEn utArea WHERE utArea.area_Parent_NId=(SELECT utArea.area_NId FROM UtAreaEn utArea WHERE utArea.area_ID=:area_ID)) "
			+ " AND utData.IUSNId=:IUSNId "
			+ " AND utData.timePeriod_NId=:timePeriod_NId "
			+ " AND utData.source_NId=:source_NId")
	Double getAggregatedDataValueByAreaCode(@Param("IUSNId")int iusNid,@Param("timePeriod_NId")int timePeriodNid,@Param("source_NId")int sourceNid,@Param("area_ID")String areaId) throws DataAccessException;
	
	@Override
	@Query("Select v from VEsamikshaAreaMapper v")
	List<VEsamikshaAreaMapper> findByArea();
	
	@Override
	@Query("SELECT utData , utArea , utTimePeriod FROM UtAreaEn utArea,UtData utData, UtTimeperiod utTimePeriod "
			+ "WHERE utArea.area_NId = utData.area_NId AND "
			+ "utData.timePeriod_NId = utTimePeriod.timePeriod_NId AND"
			+ " utArea.area_NId "
			+ "IN "
			+ "( SELECT ar.area_NId from UtAreaEn ar WHERE ar.area_ID = :areaCode ) AND "
			+ " utData.timePeriod_NId = :timeperiodId AND"
			+ " utData.IUSNId IN (:indicatorNids) order by utData.IUSNId")
	public List<Object[]> findSpiderDataByTimePeriod(
			@Param("indicatorNids") Integer[] indicatorNids,
			@Param("timeperiodId") Integer timeperiodId,
			@Param("areaCode") String areaCode);
	
	@Override
	@Query("SELECT utData,utArea FROM UtData utData,UtAreaEn utArea WHERE utArea.area_NId = utData.area_NId "
			+ "AND utData.IUSNId=:IUSNId "
			+ "AND utData.area_NId IN (:arrayAreaNid) "
			+ "AND utData.timePeriod_NId=:timeNid "
			+ "AND utData.source_NId=:sourceNid")
	public List<Object[]> findDataAndAreaId(
			@Param("IUSNId") int IUSNId, 
			@Param("arrayAreaNid") Integer[] arrayAreaNid,
			@Param("timeNid") int timeNid,
			@Param("sourceNid") int sourceNid) throws DataAccessException;
	

}
