package org.sdrc.evm.service;

import org.sdrc.odkaggregate.model.FormCollection;

public interface XFormService {

	public String getAllForms() throws Exception;
	public String insertXformDetail(FormCollection collection) throws Exception;

}
