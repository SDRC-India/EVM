package org.sdrc.evm.repository;

import java.util.List;

import org.sdrc.odkaggregate.domain.CommonXForm;

public interface CommonXFormRepository {

	public List<CommonXForm> getListOfXFormDetailByAreaCode(String areaCode);
}
