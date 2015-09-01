package org.sdrc.evm.util;

import java.util.List;

import org.sdrc.evm.model.Error;

public interface StateManager {

	Object getValue(String key);
	void setValue(String Key, Object Value);
	void setError(List<Error> errModels);
	List<Error> getError();

}
