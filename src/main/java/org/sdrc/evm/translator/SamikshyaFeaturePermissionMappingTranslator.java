package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaFeaturePermissionMapping;
import org.sdrc.evm.model.FeaturePermissionMapping;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaFeaturePermissionMappingTranslator {
	
	public static FeaturePermissionMapping toModel(SamikshyaFeaturePermissionMapping sfpMapping){
		
		if(sfpMapping!= null){
			FeaturePermissionMapping fpMapping = new FeaturePermissionMapping();
			fpMapping.setFeaturePermissionId(sfpMapping.getFeaturePermissionId());
			fpMapping.setFeature(SamikhyaFeatureTranslator.toModel(sfpMapping.getSamikshyaFeature()));
			fpMapping.setPermission(SamikshyaPermissionTranslator.toModel(sfpMapping.getSamikshyaPemission()));
			fpMapping.setRoleSchemes(SamikshyaRoleSchemeTranslator.toValueObject(sfpMapping.getSamikshyaRoleSchemes()));
			fpMapping.setLastUpdatedBy(sfpMapping.getLastUpdatedBy());
			fpMapping.setLastUpdatedDate(sfpMapping.getLastUpdatedDate());
			
			return fpMapping;			
		}
		return null;
	}
	
	public static List<FeaturePermissionMapping> toModel(List<SamikshyaFeaturePermissionMapping> sfpMappings){
		
		if(sfpMappings!= null && !sfpMappings.isEmpty()){
			List<FeaturePermissionMapping> fpMappings = new ArrayList<FeaturePermissionMapping>();
			for(SamikshyaFeaturePermissionMapping sfpMapping: sfpMappings){
				FeaturePermissionMapping fpMapping = toModel(sfpMapping);
				fpMappings.add(fpMapping);
			}
			return fpMappings;
		}
		return null;
	}
	
	public static List<ValueObject> toValueObject(List<SamikshyaFeaturePermissionMapping> sfpMappings){
		
		if(sfpMappings != null && ! sfpMappings.isEmpty()){
			List<ValueObject> vObjects = new ArrayList<ValueObject>();
			for(SamikshyaFeaturePermissionMapping sfpMapping:sfpMappings){
				ValueObject vObject = new ValueObject(Integer.toString(sfpMapping.getFeaturePermissionId()), sfpMapping.getSamikshyaFeature().getFeatureName());
				vObjects.add(vObject);
			}
			return vObjects;
		}
		
		return null;
	}
}
