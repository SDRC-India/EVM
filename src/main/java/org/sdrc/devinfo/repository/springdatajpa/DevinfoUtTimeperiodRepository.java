package org.sdrc.devinfo.repository.springdatajpa;

import java.util.Date;
import java.util.List;

import org.sdrc.devinfo.domain.UtTimeperiod;
import org.sdrc.devinfo.repository.UtTimeperiodRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface DevinfoUtTimeperiodRepository extends UtTimeperiodRepository, Repository<UtTimeperiod , Long> {

	@Override
	@Query("SELECT utTime FROM UtTimeperiod utTime WHERE utTime.startDate=:startDate AND utTime.endDate=:endDate")
	UtTimeperiod getByStartDateAndEndDate(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Override
	@Query("SELECT utTime FROM UtTimeperiod utTime ORDER BY utTime.startDate DESC ")
	List<UtTimeperiod> getTimeperiodByLimit(Pageable pageable) throws DataAccessException;
	
	@Override
	@Query("SELECT DISTINCT utTime FROM UtTimeperiod utTime,UtData utData "
			+ " WHERE utTime.timePeriod_NId = utData.timePeriod_NId "
			+ " AND utData.source_NId = :source_Nid ")
	List<UtTimeperiod> findBySourceNid(@Param("source_Nid")Integer sourceNid) throws DataAccessException;
}
