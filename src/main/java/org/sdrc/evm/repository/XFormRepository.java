package org.sdrc.evm.repository;

import java.util.List;

import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.transaction.annotation.Transactional;


public interface XFormRepository{
	
	@Transactional
	public void save(XForm form);

	public List<XForm> getListOfXFormDetailByArea(String areaCode);

	public List<XForm> getAll();

	public List<XForm> getXFormDetailsByFormID(String formID);
	
}