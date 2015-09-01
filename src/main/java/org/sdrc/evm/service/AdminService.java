package org.sdrc.evm.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaMonitoringForm;
import org.sdrc.evm.domain.SamikshyaMonitoringFormTran;
import org.sdrc.evm.model.MonitoringFormTran;
import org.springframework.dao.DataAccessException;

public interface AdminService {
	List<MonitoringFormTran> findRecordByDate(Timestamp start, Timestamp end) throws DataAccessException;
	List<MonitoringFormTran> findAll()throws DataAccessException;
}

 