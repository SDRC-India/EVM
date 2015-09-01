package org.sdrc.evm.repository;
import java.sql.Timestamp;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaMonitoringFormTran;
import org.sdrc.evm.model.MonitoringFormTran;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Adminrepository
{
	public List<MonitoringFormTran> findRecordByLastUpdatedDate(Timestamp start ,Timestamp end) throws DataAccessException;
    public List<MonitoringFormTran> findAllRecords() throws DataAccessException;
}
