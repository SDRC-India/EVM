package org.sdrc.evm.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.sdrc.evm.domain.SamikshyaAreaMapper;
import org.sdrc.evm.domain.SamikshyaBlock;
import org.sdrc.evm.domain.SamikshyaDistrict;
import org.sdrc.evm.domain.SamikshyaMonitoringForm;
import org.sdrc.evm.domain.SamikshyaRole;
import org.sdrc.evm.domain.SamikshyaRoleScheme;
import org.sdrc.evm.domain.SamikshyaState;
import org.sdrc.evm.domain.SamikshyaUser;
import org.sdrc.evm.domain.SamikshyaUserRoleSchemeMapping;
import org.sdrc.evm.domain.V_esmikshya_userRoleScheme_area;
import org.sdrc.evm.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataUserRepository extends UserRepository, Repository<SamikshyaUser, Integer> {
	
	@Transactional
	@Override
    @Query("SELECT DISTINCT user FROM SamikshyaUser user WHERE user.userEmailId LIKE :emailId")
    public Collection<SamikshyaUser> findUserByEmail(@Param("emailId") String emailId);
	
	@Override
    @Query("SELECT DISTINCT sr FROM SamikshyaRole sr WHERE sr.roleCode LIKE :roleCode")
    public List<SamikshyaRole> allEmails(@Param("roleCode")String roleCode) throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT srs FROM SamikshyaRoleScheme srs WHERE srs.areaCode LIKE :areaCode")
	public SamikshyaRoleScheme samikshyaRoleScheme(@Param("areaCode")String areaCode) throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT sb FROM SamikshyaBlock sb WHERE sb.blockCode LIKE :blockCode")
	public SamikshyaBlock samikshyaBlock(@Param("blockCode")String blockCode) throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT sd FROM SamikshyaDistrict sd WHERE sd.districtCode LIKE :districtAreaCode")
	public SamikshyaDistrict samikshyaDistrict(@Param("districtAreaCode")String districtAreaCode) throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT sam FROM SamikshyaAreaMapper sam WHERE sam.childCode LIKE :areaCode")
	public SamikshyaAreaMapper samikshyaAreaMapper(@Param("areaCode")String areaCode)throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT ss FROM SamikshyaState ss WHERE ss.stateCode LIKE :stateCode")
	public SamikshyaState samikshyaState(@Param("stateCode")String stateCode)throws DataAccessException;

	@Modifying
    @Query("UPDATE SamikshyaUser su set su.activationStatus = false where su.userId LIKE :userId")
	public void setUser(@Param("userId")int userId) throws DataAccessException;
	
	@Modifying
    @Query("UPDATE SamikshyaUser su set su.activationStatus = true where su.userId LIKE :userId")
	public void setUserTrue(@Param("userId")int userId)throws Exception;
	
	@Modifying
    @Query("UPDATE SamikshyaUser su set su.activationStatus = false where su.userId LIKE :userId")
	public void setUserFalse(@Param("userId")int userId) throws Exception;
	
	@Override
    @Query("SELECT DISTINCT srs FROM SamikshyaRoleScheme srs WHERE srs.roleSchemeName LIKE :roleSchemeName")
	public List<SamikshyaRoleScheme> samikshyaRoleSchemeId(@Param("roleSchemeName")String roleSchemeName) throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT user FROM SamikshyaUser user WHERE user.userId LIKE :userId")
    public SamikshyaUser findUserById(@Param("userId") int userId)throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT sr FROM SamikshyaRole sr WHERE sr.roleName LIKE :roleName")
    public SamikshyaRole samikshyaRole(@Param("roleName") String roleName)throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT sr FROM SamikshyaRole sr WHERE sr.roleId LIKE :roleId")
    public SamikshyaRole samikshyaRoleById(@Param("roleId") int i)throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT su FROM SamikshyaUser su")
	public List<SamikshyaUser> samikshyaUserList()throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT user FROM SamikshyaUser user WHERE user.userEmailId LIKE :emailId")
    public	SamikshyaUser findUserByEmailId(@Param("emailId") String emailId)throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT smf FROM SamikshyaMonitoringForm smf WHERE smf.samikshyaBlock LIKE :null")
    public	List<SamikshyaMonitoringForm> samikshyaMonitoringForm()throws DataAccessException;
	
	@Override
    @Query("SELECT DISTINCT srs FROM SamikshyaRoleScheme srs WHERE srs.roleSchemeName LIKE :roleSchemeName")
	public List<SamikshyaRoleScheme> findByRoleSchemeName(@Param("roleSchemeName")String roleSchemeName) throws DataAccessException;

	@Override
	@Modifying
	@Query("DELETE FROM SamikshyaUserRoleSchemeMapping usrm WHERE usrm.userRoleSchemeId = :userRoleSchemeId")
	public void deleteByRoleSchemeId(@Param("userRoleSchemeId")int userRoleSchemeId);
	
	@Override
	@Query("SELECT  srs  FROM SamikshyaRoleScheme srs where  srs.roleSchemeId IN ( SELECT MAX(sr.roleSchemeId) FROM SamikshyaRoleScheme sr GROUP BY (sr.areaCode))")
	public List<SamikshyaRoleScheme>  findByMaxRoleSchemeId()throws DataAccessException;
	
	@Query("SELECT  srs  FROM V_esmikshya_userRoleScheme_area srs where  srs.role_scheme_id IN ( SELECT MAX(sr.role_scheme_id) FROM V_esmikshya_userRoleScheme_area sr GROUP BY (sr.area_Code))")
	public List<V_esmikshya_userRoleScheme_area>  findByAreaRoleSchemeId()throws DataAccessException;
	
	@Override
	@Query("SELECT userScheme FROM SamikshyaUserRoleSchemeMapping userScheme where userScheme.samikshyaRoleScheme.areaCode=:areaCode")
	public List<SamikshyaUserRoleSchemeMapping> getUserByAreaCode(@Param("areaCode")String areaCode)throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT smf FROM SamikshyaMonitoringForm smf WHERE smf.samikshyaDistrict LIKE :samikshyaDistrict")
    public	SamikshyaMonitoringForm samikshyaMonitoringForm(@Param("samikshyaDistrict") SamikshyaDistrict samikshyaDistrict)throws DataAccessException;

	@Override
    @Query("SELECT DISTINCT sd FROM SamikshyaDistrict sd")
	public List<SamikshyaDistrict> findDistrict() throws DataAccessException;
	
	@Override
    @Query("SELECT sd.districtCode FROM SamikshyaDistrict sd WHERE sd.districtName LIKE :areaName")
	public String getDistrictCode(@Param("areaName")String areaName) throws DataAccessException;
}
