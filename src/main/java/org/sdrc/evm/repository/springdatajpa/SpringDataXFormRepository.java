package org.sdrc.evm.repository.springdatajpa;

import java.util.List;

import org.sdrc.evm.repository.XFormRepository;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataXFormRepository extends XFormRepository,
		Repository<XForm, Integer> {

	@Override
	@Query("SELECT xform from XForm xform WHERE xform.area_code LIKE :areaCode")
	public List<XForm> getListOfXFormDetailByArea(@Param("areaCode") String areaCode);
	
	@Override
	@Query("SELECT xform from XForm xform")
	public List<XForm> getAll();
	
	@Override
	@Query("SELECT xform from XForm xform WHERE xform.form_id=:formID")
	public List<XForm> getXFormDetailsByFormID(@Param("formID") String formID);
}
