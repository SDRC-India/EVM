package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaUser;
import org.sdrc.evm.model.User;

public class SamikshyaUserTranslator {
	
	public static SamikshyaUser toDomain(User user){
		
		return null;
	}
	
	public static User toModel(SamikshyaUser samikshyaUser){
		
		if(samikshyaUser !=null){
			User user = new User();
			user.setUserId(samikshyaUser.getUserId());
			user.setUserName(samikshyaUser.getUserName());
			user.setUserEmailId(samikshyaUser.getUserEmailId());
			user.setUserContactNo(samikshyaUser.getUserContactNo());
			user.setLastUpdatedBy(samikshyaUser.getLastUpdatedBy());
			user.setLastUpdatedDate(samikshyaUser.getLastUpdatedDate());
			user.setActive(samikshyaUser.isActivationStatus());
			user.setUserRoleSchemeMappings(SamikshyaUserRoleSchemeMappingTranslator.toModel(samikshyaUser.getSamikshyaUserRoleSchemeMappings()));
			
			return user;
		}
		
		return null;
	}
	
	public static List<SamikshyaUser> toDomain(List<User> user){
		
		return null;
	}
	
	public static List<User> toModel(List<SamikshyaUser> samikshyaUser){
		
		if(samikshyaUser != null && !samikshyaUser.isEmpty()){			
			List<User> userList = new ArrayList<User>();
			
			for(SamikshyaUser suser : samikshyaUser){
				User user = toModel(suser);
				userList.add(user);
			}
			return userList ;
		}
		
		
		return null;
	}
	
	

}
