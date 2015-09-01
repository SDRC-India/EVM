package org.sdrc.evm.translator;

import org.sdrc.evm.domain.SamikshyaState;
import org.sdrc.evm.model.State;

public class SamikshyaStateTranslator {

	public static State toModel(SamikshyaState sState) {

		State state = null;
		if (sState != null) {
			state = new State();
			state.setStateId(sState.getStateId());
			state.setStateCode(sState.getStateCode());
			state.setStateName(sState.getStateName());
			state.setSamikshyaDistricts(SamikshyaDistrictTranslator
					.toModel(sState.getSamikshyaDistricts()));
		}
		return state;
	}

}
