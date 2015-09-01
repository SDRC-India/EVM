package org.sdrc.evm.repository.springdatajpa;

import java.util.List;

import org.sdrc.evm.repository.CommonXFormRepository;
import org.sdrc.odkaggregate.domain.CommonXForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataCommonXFormRepository extends CommonXFormRepository,
Repository<CommonXForm, Integer>{

	@Override
	@Query("SELECT xform from CommonXForm xform WHERE xform.area_code =:areaCode")
	public List<CommonXForm> getListOfXFormDetailByAreaCode(@Param("areaCode") String areaCode);
}
