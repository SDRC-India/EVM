package org.sdrc.devinfo.repository;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface IndicatorRepository
{
	public List<Object []> findByIC_Type(Integer sectorNid) throws DataAccessException;
}
