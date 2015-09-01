package org.sdrc.evm.service;

import java.sql.Timestamp;
import java.util.List;

import org.sdrc.evm.model.MonitoringFormTran;
import org.sdrc.evm.repository.springdatajpa.SpringDataAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService{
	
	private SpringDataAdminRepository springDataAdminRepository;
	
	@Autowired
	public AdminServiceImpl(SpringDataAdminRepository springDataAdminRepository)
	{
		this.springDataAdminRepository=springDataAdminRepository;
	}
	
	String level;
	@Override
	@Transactional
	public List<MonitoringFormTran> findRecordByDate(Timestamp start,
			Timestamp end) {
		
		return springDataAdminRepository.findRecordByLastUpdatedDate(start,end);
	}
	@Override
	public List<MonitoringFormTran> findAll() throws DataAccessException {
		
		return springDataAdminRepository.findAllRecords();	}
	
	
}
