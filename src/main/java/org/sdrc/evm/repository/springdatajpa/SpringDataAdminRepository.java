package org.sdrc.evm.repository.springdatajpa;

import java.sql.Timestamp;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaMonitoringFormTran;
import org.sdrc.evm.model.MonitoringFormTran;
import org.sdrc.evm.repository.Adminrepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataAdminRepository extends Adminrepository , JpaRepository<SamikshyaMonitoringFormTran , Long> { 
	
	@Query
	("SELECT DISTINCT form FROM SamikshyaMonitoringFormTran form WHERE form.lastUpdatedDate >= ?1 AND form.lastUpdatedDate <= ?2")
	public List<MonitoringFormTran> findRecordByLastUpdatedDate(Timestamp start,Timestamp end);
	
	@Query
	("SELECT form FROM SamikshyaMonitoringFormTran form")
	public List<MonitoringFormTran> findAllRecords();

}
