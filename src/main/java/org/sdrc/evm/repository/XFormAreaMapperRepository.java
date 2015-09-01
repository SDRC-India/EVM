package org.sdrc.evm.repository;

import org.sdrc.odkaggregate.domain.XFormAreaMapper;
import org.springframework.dao.DataAccessException;

public interface XFormAreaMapperRepository {

	XFormAreaMapper findByAreaNameAndAreaLevel(String areaName,String areaLevel) throws DataAccessException;
}
