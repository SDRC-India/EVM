package org.sdrc.evm.repository;


import org.sdrc.evm.domain.FacilityDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface FacilityDetailsRepository {

	@Transactional
	public void save(FacilityDetails facilityDetails);
	
	FacilityDetails findByAreaId(String areaId) throws DataAccessException;

}
