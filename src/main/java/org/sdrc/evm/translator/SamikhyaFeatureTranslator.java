package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaFeature;
import org.sdrc.evm.model.Feature;

public class SamikhyaFeatureTranslator {
	
	public static Feature toModel(SamikshyaFeature sFeature){
		if(sFeature != null){
			Feature feature = new Feature();
			feature.setFeatureId(sFeature.getFeatureId());
			feature.setFeatureName(sFeature.getFeatureName());
			feature.setFeatureCode(sFeature.getFeatureCode());
			feature.setDescription(sFeature.getDescription());
			feature.setFeaturePermissionMappings(SamikshyaFeaturePermissionMappingTranslator.toValueObject(sFeature.getSamikshyaFeaturePermissionMappings()));
			feature.setLastUpdatedDate(sFeature.getLastUpdatedDate());
			feature.setLastUpdatedBy(sFeature.getLastUpdatedBy());
			
			return feature;
		}
		return null;
	}
	
	public static List<Feature> toModel(List<SamikshyaFeature> sFeatures){
		
		if(sFeatures != null && !sFeatures.isEmpty()){
			List<Feature> features = new ArrayList<Feature>();
			for(SamikshyaFeature sFeature : sFeatures){
				features.add(toModel(sFeature));
			}
			
			return features;
		}
		return null;
	}

}
