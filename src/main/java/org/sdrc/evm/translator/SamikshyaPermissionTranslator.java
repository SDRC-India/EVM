package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaPemission;
import org.sdrc.evm.model.Permission;



public class SamikshyaPermissionTranslator {
	
	public static Permission toModel(SamikshyaPemission sPermission){
		if(sPermission != null){
			
			Permission permission = new Permission();
			permission.setPermissionId(sPermission.getPermissionId());
			permission.setPermissionName(sPermission.getPermissionName());
			permission.setPermissionCode(sPermission.getPermissionCode());
			permission.setDescription(sPermission.getDescription());
			permission.setFeaturePermissionMappings(SamikshyaFeaturePermissionMappingTranslator.toValueObject(sPermission.getSamikshyaFeaturePermissionMappings()));
			permission.setLastUpdatedDate(sPermission.getLastUpdatedDate());
			permission.setLastUpdatedBy(sPermission.getLastUpdatedBy());
			
			return permission;
		}
		return null;
	}

	public static List<Permission> toModel(List<SamikshyaPemission> sPermissions){
		if(sPermissions != null && sPermissions.isEmpty()){
			List<Permission> permissions = new ArrayList<Permission>();
			for(SamikshyaPemission sPermission : sPermissions){
				permissions.add(toModel(sPermission));
			}
			return permissions;
		}
		return null;
	}
}
