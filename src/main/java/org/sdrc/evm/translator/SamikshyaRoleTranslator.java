package org.sdrc.evm.translator;

import org.sdrc.evm.domain.SamikshyaRole;
import org.sdrc.evm.model.Role;

public class SamikshyaRoleTranslator {
	
	public static Role toModel(SamikshyaRole srole){
		if(srole !=null){
			Role role =new Role();
			role.setRoleId(srole.getRoleId());
			role.setRoleName(srole.getRoleName());
			role.setDescription(srole.getDescription());
			role.setRoleSchemes(SamikshyaRoleSchemeTranslator.toValueObject(srole.getSamikshyaRoleSchemes()));
			role.setLastUpdatedBy(srole.getLastUpdatedBy());
			role.setLastUpdatedDate(srole.getLastUpdatedDate());
			
			return role;
		}
		return null;
	}

}
