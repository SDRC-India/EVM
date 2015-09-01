package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaMonitoringFormTran;
import org.sdrc.evm.model.MonitoringFormTran;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaMonitoringFormTranTanslator {

	public static List<ValueObject> toValueObject(
			List<SamikshyaMonitoringFormTran> smfTrans) {

		List<ValueObject> vObjects = null;

		if (smfTrans != null && !smfTrans.isEmpty()) {
			vObjects = new ArrayList<ValueObject>();
			for (SamikshyaMonitoringFormTran smfTran : smfTrans) {
				vObjects.add(new ValueObject(Integer.toString(smfTran.getId()),
						smfTran.getFormPath()));
			}
		}

		return vObjects;
	}

	public static MonitoringFormTran toModel(SamikshyaMonitoringFormTran smfTran) {

		MonitoringFormTran mfTran = null;
		if (smfTran != null) {
			mfTran = new MonitoringFormTran();
			mfTran.setId(smfTran.getId());
			mfTran.setFormPath(smfTran.getFormPath());
			mfTran.setLastUpdatedBy(smfTran.getLastUpdatedBy());
			mfTran.setLastUpdatedDate(smfTran.getLastUpdatedDate());
			mfTran.setRemarks(smfTran.getRemarks());
			mfTran.setStatus(smfTran.getStatus());
			mfTran.setTimePeriod(smfTran.getTimePeriod());
			mfTran.setActive(smfTran.isActive());
			mfTran.setSamikshyaMonitoringForm(SamikshyaMonitoringFormTranslator
					.toModel(smfTran.getSamikshyaMonitoringForm()));
		}

		return mfTran;
	}

	public static List<MonitoringFormTran> toModel(List<SamikshyaMonitoringFormTran> samikshyaMonitoringFormTrans) {
		List<MonitoringFormTran> monitoringFormTrans=null;
		
		if(samikshyaMonitoringFormTrans!=null && !samikshyaMonitoringFormTrans.isEmpty()){
			monitoringFormTrans=new ArrayList<MonitoringFormTran>();
			
			for(SamikshyaMonitoringFormTran formTran:samikshyaMonitoringFormTrans){
				MonitoringFormTran monitoringFormTran=toModel(formTran);
				monitoringFormTrans.add(monitoringFormTran);
			}
			
			return monitoringFormTrans;
		}
		
		return null;
	}

}
