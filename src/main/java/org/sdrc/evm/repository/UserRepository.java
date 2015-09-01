package org.sdrc.evm.repository;

import java.util.Collection;
import java.util.List;

import org.sdrc.evm.domain.BaseEntity;
import org.sdrc.evm.domain.SamikshyaAreaMapper;
import org.sdrc.evm.domain.SamikshyaBlock;
import org.sdrc.evm.domain.SamikshyaDistrict;
import org.sdrc.evm.domain.SamikshyaMonitoringForm;
import org.sdrc.evm.domain.SamikshyaRole;
import org.sdrc.evm.domain.SamikshyaRoleScheme;
import org.sdrc.evm.domain.SamikshyaState;
import org.sdrc.evm.domain.SamikshyaUser;
import org.sdrc.evm.domain.SamikshyaUserRoleSchemeMapping;
import org.springframework.dao.DataAccessException;

/**
 * Repository class for <code>User</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Harekrishna Panigrahi
 * 
 */



public interface UserRepository {

    /**
     * Retrieve <code>User</code>s from the data store by last name, returning all Users whose last name <i>starts</i>
     * with the given name.
     *
     * @param lastName Value to search for
     * @return a <code>Collection</code> of matching <code>User</code>s (or an empty <code>Collection</code> if none
     *         found)
     */
    Collection<SamikshyaUser> findUserByEmail(String emailId) throws DataAccessException;

//    /**
//     * Retrieve an <code>User</code> from the data store by id.
//     *
//     * @param id the id to search for
//     * @return the <code>User</code> if found
//     * @throws org.springframework.dao.DataRetrievalFailureException
//     *          if not found
//     */
//    SamikshyaUser findById(int id) throws DataAccessException;


    /**
     * Save an <code>User</code> to the data store, either inserting or updating it.
     *
     * @param User the <code>User</code> to save
     * @see BaseEntity#isNew
     */
    void save(SamikshyaUser User) throws DataAccessException;

	List<SamikshyaRole> allEmails(String roleCode) throws DataAccessException;

	SamikshyaRoleScheme samikshyaRoleScheme(String blockAreaCode) throws DataAccessException;

	SamikshyaBlock samikshyaBlock(String blockCode) throws DataAccessException;

	SamikshyaDistrict samikshyaDistrict(String districtAreaCode)throws DataAccessException;

	SamikshyaAreaMapper samikshyaAreaMapper(String areaCode) throws DataAccessException;

	SamikshyaState samikshyaState(String stateCode) throws DataAccessException;
	
	void setUserTrue(int userId)throws Exception;
	void setUserFalse(int userId)throws Exception;

	List<SamikshyaRoleScheme> samikshyaRoleSchemeId(String roleSchemeName)throws DataAccessException;

	SamikshyaUser findUserById(int userId) throws DataAccessException;

	SamikshyaRole samikshyaRole(String roleName) throws DataAccessException;

	SamikshyaRole samikshyaRoleById(int roleId) throws DataAccessException;


	List<SamikshyaUser> samikshyaUserList() throws DataAccessException;

	SamikshyaUser findUserByEmailId(String emailId) throws DataAccessException;

	List<SamikshyaMonitoringForm> samikshyaMonitoringForm()throws DataAccessException;

	List<SamikshyaRoleScheme> findByRoleSchemeName(String roleSchemeName) throws DataAccessException;

	void deleteByRoleSchemeId(int userRoleSchemeId);

	List<SamikshyaRoleScheme> findByMaxRoleSchemeId()throws DataAccessException;
	
	List<SamikshyaUserRoleSchemeMapping> getUserByAreaCode(String areaCode)throws DataAccessException;
	
	List<SamikshyaDistrict> findDistrict() throws DataAccessException;

	SamikshyaMonitoringForm samikshyaMonitoringForm(
			SamikshyaDistrict samikshyaDistrict) throws DataAccessException;

	String getDistrictCode(String areaName) throws DataAccessException;

}

