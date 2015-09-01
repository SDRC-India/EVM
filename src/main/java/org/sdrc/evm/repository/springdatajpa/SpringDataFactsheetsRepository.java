package org.sdrc.evm.repository.springdatajpa;

import java.util.List;

import org.sdrc.evm.domain.FacilityDetails;
import org.sdrc.evm.domain.Factsheets;
import org.sdrc.evm.repository.FactsheetsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataFactsheetsRepository extends FactsheetsRepository, Repository<Factsheets, String>{
	
	@Override
    @Query("SELECT DISTINCT fs FROM Factsheets fs where fs.area_parentId = :areaId")
    public List<Factsheets> getFactsheets(@Param("areaId") String areaId) throws DataAccessException;

}
