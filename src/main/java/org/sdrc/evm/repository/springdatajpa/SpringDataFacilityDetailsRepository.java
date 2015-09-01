package org.sdrc.evm.repository.springdatajpa;

import javax.transaction.Transactional;

import org.sdrc.evm.domain.FacilityDetails;
import org.sdrc.evm.repository.FacilityDetailsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataFacilityDetailsRepository extends FacilityDetailsRepository, Repository<FacilityDetails, String> {

	@Override
    @Query("SELECT DISTINCT fd FROM FacilityDetails fd WHERE fd.areaId LIKE :areaId")
    public FacilityDetails findByAreaId(@Param("areaId") String areaId) throws DataAccessException;
}
