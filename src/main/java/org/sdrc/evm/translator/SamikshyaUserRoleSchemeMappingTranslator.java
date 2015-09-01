package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaUserRoleSchemeMapping;
import org.sdrc.evm.model.UserRoleSchemeMapping;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaUserRoleSchemeMappingTranslator {
	
	public static List<UserRoleSchemeMapping> toModel(List<SamikshyaUserRoleSchemeMapping> samikshyaUserRoleSchemeMapping){
		
		List<UserRoleSchemeMapping> ursMappings = null;
		if(samikshyaUserRoleSchemeMapping != null && !samikshyaUserRoleSchemeMapping.isEmpty()){
			ursMappings= new ArrayList<UserRoleSchemeMapping>();
			for(SamikshyaUserRoleSchemeMapping surscMapping : samikshyaUserRoleSchemeMapping){
				UserRoleSchemeMapping ursm = toModel(surscMapping) ;
				ursMappings.add(ursm);
			}
			return ursMappings;
		}
		
		return null;
	}
	
	public static UserRoleSchemeMapping toModel(SamikshyaUserRoleSchemeMapping samikshyaUserRoleSchemeMapping){
		
		
		if(samikshyaUserRoleSchemeMapping != null){
			UserRoleSchemeMapping ursm = new UserRoleSchemeMapping();
			ursm.setUserRoleSchemeId(samikshyaUserRoleSchemeMapping.getUserRoleSchemeId());
			ursm.setUser(new ValueObject(Integer.toString(samikshyaUserRoleSchemeMapping.getSamikshyaUser().getUserId()), samikshyaUserRoleSchemeMapping.getSamikshyaUser().getUserName()));
			ursm.setRoleScheme(SamikshyaRoleSchemeTranslator.toModel(samikshyaUserRoleSchemeMapping.getSamikshyaRoleScheme()));
			ursm.setLastUpdatedBy(samikshyaUserRoleSchemeMapping.getLastUpdatedBy());
			ursm.setLastUpdatedDate(samikshyaUserRoleSchemeMapping.getLastUpdatedDate());
			return ursm;
		}

		return null;
	}
	
	public static List<ValueObject> toValueObject(List<SamikshyaUserRoleSchemeMapping> sursMappings){
		
		if(sursMappings != null && !sursMappings.isEmpty()){
			List<ValueObject> vObjects = new ArrayList<ValueObject>();
			for(SamikshyaUserRoleSchemeMapping sursMapping : sursMappings){
				ValueObject vObject= new ValueObject(Integer.toString(sursMapping.getUserRoleSchemeId()), sursMapping.getSamikshyaRoleScheme().getRoleSchemeName());
				vObjects.add(vObject);
			}
			return vObjects;
		}
		
		return null ;
		
	}

}
