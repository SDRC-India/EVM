package org.sdrc.evm.service;

import java.util.Collection;

import org.sdrc.evm.model.Mail;
import org.sdrc.evm.model.User;
import org.springframework.dao.DataAccessException;

public interface UserService {
	
	User findUserById(int id) throws DataAccessException;

	void saveUser(User user) throws DataAccessException;

    Collection<User> findUserByEmail(String emailId) throws DataAccessException;

	String dpcNotify(String string) throws Exception;

	String ptcNotify(String string) throws Exception;

	String brccFileUploadNotification(String string) throws Exception;

	String sendMail(Mail mail) throws Exception;

	String sendMail(String fromUserName, String toUserName, String toEmailId,StringBuffer subject, StringBuffer msg) ;
}
