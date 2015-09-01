package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaRoleScheme;
import org.sdrc.evm.model.RoleScheme;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaRoleSchemeTranslator {
	
	public static RoleScheme toModel(SamikshyaRoleScheme srScheme){
		
		if(srScheme != null){
			RoleScheme rScheme = new RoleScheme();
			rScheme.setRoleSchemeId(srScheme.getRoleSchemeId());
			rScheme.setRoleSchemeName(srScheme.getRoleSchemeName());
			rScheme.setSamikshyaRole(SamikshyaRoleTranslator.toModel(srScheme.getSamikshyaRole()));
			rScheme.setUserRoleSchemeMappings(SamikshyaUserRoleSchemeMappingTranslator.toValueObject(srScheme.getSamikshyaUserRoleSchemeMappings()));
			rScheme.setAreaCode(srScheme.getAreaCode());
			rScheme.setFeaturePermissionMapping(SamikshyaFeaturePermissionMappingTranslator.toModel(srScheme.getSamikshyaFeaturePermissionMapping()));
			rScheme.setLastUpdatedDate(srScheme.getLastUpdatedDate());
			rScheme.setLastUpdatedBy(srScheme.getLastUpdatedBy());
			
			return rScheme;
			
		}
		return null;
	}
	
	public static List<RoleScheme> toModel(List<SamikshyaRoleScheme> srSchemes){
		
		if(srSchemes != null && !srSchemes.isEmpty()){
			List<RoleScheme> rSchemes = new ArrayList<RoleScheme>();
			for(SamikshyaRoleScheme srScheme :srSchemes){
				RoleScheme rScheme = new RoleScheme();
				rScheme = toModel(srScheme);
				rSchemes.add(rScheme);
			}
			
			return rSchemes;
		}
		return null;
	}
	
	public static List<ValueObject> toValueObject(List<SamikshyaRoleScheme> srSchemes){
		
		if(srSchemes != null && ! srSchemes.isEmpty()){
			List<ValueObject> vObjects = new ArrayList<ValueObject>();
			for(SamikshyaRoleScheme srScheme : srSchemes){
				ValueObject vObject = new ValueObject(Integer.toString(srScheme.getRoleSchemeId()), srScheme.getRoleSchemeName());
				vObjects.add(vObject);
			}
			return vObjects;
		}
		return null ;
	}
}
