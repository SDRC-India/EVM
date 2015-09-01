package org.sdrc.evm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.sdrc.evm.domain.SamikshyaAreaMapper;
import org.sdrc.evm.domain.SamikshyaBlock;
import org.sdrc.evm.domain.SamikshyaDistrict;
import org.sdrc.evm.domain.SamikshyaRoleScheme;
import org.sdrc.evm.domain.SamikshyaUser;
import org.sdrc.evm.domain.SamikshyaUserRoleSchemeMapping;
import org.sdrc.evm.model.Mail;
import org.sdrc.evm.model.User;
import org.sdrc.evm.repository.springdatajpa.SpringDataUserRepository;
import org.sdrc.evm.translator.SamikshyaUserTranslator;
import org.sdrc.evm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private final ResourceBundleMessageSource messageSource;
	
    private SpringDataUserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(SpringDataUserRepository userRepository,ResourceBundleMessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) throws DataAccessException {
       // return userRepository.findById(id);
    	return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> findUserByEmail(String emailId) throws DataAccessException {
    	
    	Collection<SamikshyaUser> user = userRepository.findUserByEmail(emailId);
        
        for(SamikshyaUser sUser : user){
        	System.out.println("User Mapping => " + sUser.getSamikshyaUserRoleSchemeMappings()
        			!= null?"   Size   == > "+sUser.getSamikshyaUserRoleSchemeMappings().size():"NULL Mappings");
        	if(sUser.getSamikshyaUserRoleSchemeMappings() != null && sUser.getSamikshyaUserRoleSchemeMappings().size() >0){
        		System.out.println(" ======= "+sUser.getSamikshyaUserRoleSchemeMappings().get(0).getSamikshyaRoleScheme()
        				.getSamikshyaRole().getRoleName());
        	}
        }
        
        if(user != null && user.size()>0){
        	 System.out.println("Translaing User Domain ====> ");
        	User userModel = new User();
        	userModel = SamikshyaUserTranslator.toModel(user.iterator().next());
        	System.out.println(" Checking if user exists ===> "+ userModel != null ? userModel.getUserName() : "=== :( user is NULL");
        	List<User> users = new ArrayList<User>();
        	users.add(userModel);
        	return users;
        }
    	return null;
    }

    @Override
    @Transactional
    public void saveUser(User user) throws DataAccessException {
        //userRepository.save(user);
    	
    }

	@Override
	public String dpcNotify(String blockAreaCode) throws Exception{//Notification from dpc to brcc
	String toEmailId = null ;
	String toUserName = null ;
	String blockName = null ;
	Mail mail = new Mail();
	StringBuffer fromUserName = new StringBuffer();
	fromUserName.append("<dpc user name>");// **********************comes from User Principal******************************** .
	StringBuffer subject = new StringBuffer();
	subject.append(messageSource.getMessage(Constants.DPC_NOTIFYBUTTON_NOTIFICATION_SUBJECT, null, null));
	StringBuffer msg =  new StringBuffer();
	msg.append(messageSource.getMessage(Constants.DPC_NOTIFYBUTTON_NOTIFICATION_MSG, null, null));
	try{
			SamikshyaRoleScheme roleScheme=userRepository.samikshyaRoleScheme(blockAreaCode);
			List<SamikshyaUserRoleSchemeMapping> roleSchemeMappingList = roleScheme.getSamikshyaUserRoleSchemeMappings();
			for(SamikshyaUserRoleSchemeMapping roleSchemeMapping : roleSchemeMappingList)
			{
				SamikshyaUser samikshyaUser = roleSchemeMapping.getSamikshyaUser();
				toEmailId = samikshyaUser.getUserEmailId(); // BRCC EMAIL ID
				toUserName = samikshyaUser.getUserName();	// BRCC NAME
			}
			if(toEmailId!=null)
			{
				SamikshyaBlock block = userRepository.samikshyaBlock(blockAreaCode);
				blockName = block.getBlockName(); // BRCC BLOCK NAME
			}
			System.out.println("emailId : "+toEmailId);
			System.out.println("userName : "+toUserName);
			System.out.println("blockName : "+blockName);
			
			mail.setFromUserName(fromUserName);
			mail.setToUserName(toUserName);
			mail.setToEmailId(toEmailId);
			mail.setCc("");
			mail.setSubject(subject);
			mail.setMsg(msg);
			
	     }catch (Exception e) {
	 		System.out.println(e);
			return null;
			}	
			String status = sendMail(mail);
			if(status!=null)
			{return "Done";	}
			else{return null;}
	}

	@Override
	public String ptcNotify(String districtAreaCode) throws Exception{ // Notification from ptc to dpc
	String toEmailId = null ;
	String toUserName = null ;
	String districtName = null ;
	Mail mail = new Mail();
	StringBuffer fromUserName = new StringBuffer();
	fromUserName.append("<ptc user name>");// **********************comes from User Principal******************************** .
	StringBuffer subject = new StringBuffer();
	subject.append(messageSource.getMessage(Constants.PTC_NOTIFYBUTTON_NOTIFICATION_SUBJECT, null, null));
	StringBuffer msg =  new StringBuffer();
	msg.append(messageSource.getMessage(Constants.PTC_NOTIFYBUTTON_NOTIFICATION_MSG, null, null));
	msg.append(" "+"<month>");//******************************************************************************************** MONTH ????
	try {
			SamikshyaRoleScheme roleScheme=userRepository.samikshyaRoleScheme(districtAreaCode);
			List<SamikshyaUserRoleSchemeMapping> roleSchemeMappingList = roleScheme.getSamikshyaUserRoleSchemeMappings();
			for(SamikshyaUserRoleSchemeMapping roleSchemeMapping : roleSchemeMappingList)
			{
				SamikshyaUser samikshyaUser = roleSchemeMapping.getSamikshyaUser();
				toEmailId = samikshyaUser.getUserEmailId(); // DPC EMAIL ID
				toUserName = samikshyaUser.getUserName();	// DPC NAME
				System.out.println();
			}
			if(toEmailId!=null)
			{
				SamikshyaDistrict district = userRepository.samikshyaDistrict(districtAreaCode);
				districtName = district.getDistrictName(); // DPC DISTRICT NAME
			}
			System.out.println("toEmailId : "+toEmailId);
			System.out.println("toUserName : "+toUserName);
			System.out.println("districtName : "+districtName);
			
			mail.setFromUserName(fromUserName);
			mail.setToUserName(toUserName);
			mail.setToEmailId(toEmailId);
			mail.setCc("");
			mail.setSubject(subject);
			mail.setMsg(msg);
			
		} catch (Exception e) {
		System.out.println(e);
		return null;
		}	
		String status = sendMail(mail);
		if(status!=null)
		{return "Done";	}
		else{return null;}
	}
    
	@Override
	public String brccFileUploadNotification(String emailOfBrcc) throws Exception{
	String dpcEmailId;
	String dpcUserName;
	Mail mail = new Mail();
	StringBuffer brccUserName = new StringBuffer();
	brccUserName.append("<Brcc User name >");// *********comes from user pricipal of brcc*************************************
	String blockName = new String();
	StringBuffer subject = new StringBuffer();
	subject.append(messageSource.getMessage(Constants.BRCC_FILEUPLOAD_NOTIFICATION_SUBJECT, null, null));
	StringBuffer msg=new StringBuffer();
	msg.append(messageSource.getMessage(Constants.BRCC_FILEUPLOAD_NOTIFICATION_MSG, null, null));
	msg.append(brccUserName);
	msg.append(" "+messageSource.getMessage(Constants.BRCC_FILEUPLOAD_NOTIFICATION_MSG1, null, null));
	try {
			Collection<SamikshyaUser> userList = userRepository
					.findUserByEmail(emailOfBrcc);
			SamikshyaUser user = (SamikshyaUser) userList.toArray()[0];
			List<SamikshyaUserRoleSchemeMapping> UserRoleSchemeMappingList = user
					.getSamikshyaUserRoleSchemeMappings();
			SamikshyaUserRoleSchemeMapping userRoleSchemeMapping = (SamikshyaUserRoleSchemeMapping) UserRoleSchemeMappingList
					.toArray()[0];
			SamikshyaRoleScheme roleScheme = userRoleSchemeMapping
					.getSamikshyaRoleScheme();
			String blockAreaCode = roleScheme.getAreaCode();

			System.out.println("Brcc areaCode : " + blockAreaCode);

			SamikshyaAreaMapper areaMapper = userRepository
					.samikshyaAreaMapper(blockAreaCode);
			String districtAreaCode = areaMapper.getParentCode();

			System.out.println("Dpc areaCode : " + districtAreaCode);

			SamikshyaRoleScheme samikshyaRoleScheme = userRepository
					.samikshyaRoleScheme(districtAreaCode);
			List<SamikshyaUserRoleSchemeMapping> roleSchemeMappingList = samikshyaRoleScheme
					.getSamikshyaUserRoleSchemeMappings();
			SamikshyaUserRoleSchemeMapping roleSchemeMapping = (SamikshyaUserRoleSchemeMapping) roleSchemeMappingList
					.toArray()[0];
			SamikshyaUser samikshyaUser = roleSchemeMapping.getSamikshyaUser();
			dpcEmailId = samikshyaUser.getUserEmailId(); // DPC EMAIL ID
			dpcUserName = samikshyaUser.getUserName(); // DPC NAME

			if (dpcEmailId != null) {
				SamikshyaBlock block = userRepository
						.samikshyaBlock(blockAreaCode);
				blockName = block.getBlockName(); // BRCC BLOCK NAME
				subject.append(" "+blockName);
				msg.append(" "+blockName);
				msg.append(messageSource.getMessage(Constants.BRCC_FILEUPLOAD_NOTIFICATION_MSG2, null, null));
				System.out.println("blockName :" + blockName);
			}
			System.out.println("dpcEmailId :" + dpcEmailId);
			System.out.println("dpcUserName :" + dpcUserName);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		mail.setFromUserName(brccUserName);
		mail.setToUserName(dpcUserName);
		mail.setToEmailId(dpcEmailId);
		mail.setSubject(subject);
		mail.setCc("");
		mail.setMsg(msg);
		
		String status = sendMail(mail);
		if(status!=null)
		{return "Done";	}
		else{return null;}
	}
	
	@Override
	public String sendMail(Mail mail) throws Exception{
	try {
		 	System.out.println("**************************Mail Method is called**************************");
		 	System.out.println("**************************Mail Method is called************"+mail.getToEmailId() +mail.getToUserName());
		 	String to = mail.getToUserName() != null && mail.getToUserName() != "" ? "Dear ":"Hello";
			Properties props = new Properties();
			props.put(messageSource.getMessage(Constants.SMTP_HOST_KEY, null, null), messageSource.getMessage(Constants.SMTP_HOST, null, null));
			props.put(messageSource.getMessage(Constants.SOCKETFACTORY_PORT_KEY, null, null), messageSource.getMessage(Constants.SOCKETFACTORY_PORT, null, null));
			props.put(messageSource.getMessage(Constants.SOCKETFACTORY_CLASS_KEY, null, null), messageSource.getMessage(Constants.SOCKETFACTORY_CLASS, null, null));
			props.put(messageSource.getMessage(Constants.SMTP_AUTH_KEY, null, null), messageSource.getMessage(Constants.SMTP_AUTH, null, null));
			props.put(messageSource.getMessage(Constants.SMTP_PORT_KEY, null, null), messageSource.getMessage(Constants.SMTP_PORT, null, null));

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									messageSource.getMessage(Constants.AUTHENTICATION_USERID, null, null),
									messageSource.getMessage(Constants.AUTHENTICATION_PASSWORD, null, null));
						}
					});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(messageSource.getMessage(Constants.MESSAGE_SETFORM, null, null)));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mail.getToEmailId()));
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(mail.getCc()));
			message.setSubject(mail.getSubject().toString());
			message.setText(to + mail.getToUserName()+"," + "\n"
					+ mail.getMsg()
					+ "\n\n" + "Regards," + "\n" + mail.getFromUserName());
			Transport.send(message);
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return "Done";
	}
	
	@Override
	public String sendMail(String fromUserName,String toUserName,String toEmailId,StringBuffer subject,StringBuffer msg) {
	try {
			Properties props = new Properties();
			props.put(messageSource.getMessage(Constants.SMTP_HOST_KEY, null, null), messageSource.getMessage(Constants.SMTP_HOST, null, null));
			props.put(messageSource.getMessage(Constants.SOCKETFACTORY_PORT_KEY, null, null), messageSource.getMessage(Constants.SOCKETFACTORY_PORT, null, null));
			props.put(messageSource.getMessage(Constants.SOCKETFACTORY_CLASS_KEY, null, null), messageSource.getMessage(Constants.SOCKETFACTORY_CLASS, null, null));
			props.put(messageSource.getMessage(Constants.SMTP_AUTH_KEY, null, null), messageSource.getMessage(Constants.SMTP_AUTH, null, null));
			props.put(messageSource.getMessage(Constants.SMTP_PORT_KEY, null, null), messageSource.getMessage(Constants.SMTP_PORT, null, null));

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									messageSource.getMessage(Constants.AUTHENTICATION_USERID, null, null),
									messageSource.getMessage(Constants.AUTHENTICATION_PASSWORD, null, null));
						}
					});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(messageSource.getMessage(Constants.MESSAGE_SETFORM, null, null)));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmailId));
			message.setSubject(subject.toString());
			message.setText("Dear " + toUserName + "\n\n"
					+ "NOTIFICATION DETAILS:" + "\n" + "Message : " + msg
					+ "\n\n" + "Regards," + "\n" + fromUserName);
			Transport.send(message);
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return "Done";
	}
}
