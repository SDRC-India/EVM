package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaMonitoringForm;
import org.sdrc.evm.model.MonitoringForm;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaMonitoringFormTranslator {
	
	public static MonitoringForm toModel(SamikshyaMonitoringForm smForm){
		
		MonitoringForm mForm = null;
		if(smForm != null){
			mForm = new MonitoringForm();
			mForm.setId(smForm.getId());
			mForm.setFormCode(smForm.getFormCode());
			mForm.setFormPath(smForm.getFormPath());
			mForm.setFormType(smForm.getFormType());
			mForm.setIsActive(smForm.getIsActive());
			mForm.setLastUpdatedBy(smForm.getLastUpdatedBy());
			mForm.setLastUpdatedDate(smForm.getLastUpdatedDate());
			mForm.setSamikshyaBlock(SamikshyaBlockTranslator.toModel(smForm.getSamikshyaBlock()));
			mForm.setSamikshyaCluster(SamikshyaClusterTranslator.toModel(smForm.getSamikshyaCluster()));
			mForm.setSamikshyaDistrict(SamikshyaDistrictTranslator.toModel(smForm.getSamikshyaDistrict()));
			mForm.setSamikshyaMonitoringFormTrans(SamikshyaMonitoringFormTranTanslator.toValueObject(smForm.getSamikshyaMonitoringFormTrans()));
			mForm.setSamikshyaState(SamikshyaStateTranslator.toModel(smForm.getSamikshyaState()));
			mForm.setVersion(smForm.getVersion());
			
		}
		
		return mForm;
	}
	
public static List<MonitoringForm> toModel(List<SamikshyaMonitoringForm> samikshyaMonitoringForms){
		
	List<MonitoringForm> monitoringForms=null;
	if(samikshyaMonitoringForms!=null && !samikshyaMonitoringForms.isEmpty()){
		monitoringForms=new ArrayList<MonitoringForm>();
		
		for(SamikshyaMonitoringForm samikshyaMonitoringForm:samikshyaMonitoringForms){
			MonitoringForm monitoringForm=toModel(samikshyaMonitoringForm);
			monitoringForms.add(monitoringForm);
		}
		
	}
	return monitoringForms;
}

	public static List<ValueObject> toValueObject(
			List<SamikshyaMonitoringForm> smForms) {
		// TODO Auto-generated method stub
		List<ValueObject> vObjects = null ;
		ValueObject vObject = null ;
		
		if(smForms != null && !smForms.isEmpty()){
			for(SamikshyaMonitoringForm smForm : smForms){
				vObjects = new ArrayList<ValueObject>();
				vObject = new ValueObject(Integer.toString(smForm.getId()), smForm.getFormCode());
				vObjects.add(vObject);
			}
			
		}
		
		return vObjects;
	}
}
