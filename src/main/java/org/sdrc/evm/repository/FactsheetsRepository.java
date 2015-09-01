package org.sdrc.evm.repository;

import java.util.List;

import org.sdrc.evm.domain.Factsheets;
import org.springframework.dao.DataAccessException;

public interface FactsheetsRepository {

	List<Factsheets> getFactsheets(String areaId) throws DataAccessException;

}
