package org.sdrc.evm.web;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.sdrc.evm.domain.SamikshyaUserRoleSchemeMapping;
import org.sdrc.evm.model.ContactUs;
import org.sdrc.evm.model.DPCMail;
import org.sdrc.evm.model.Error;
import org.sdrc.evm.model.Mail;
import org.sdrc.evm.model.PtcMail;
import org.sdrc.evm.model.User;
import org.sdrc.evm.repository.UserRepository;
import org.sdrc.evm.service.UserService;
import org.sdrc.evm.util.Constants;
import org.sdrc.evm.util.StateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller

public class EMailController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StateManager stateManager;
	
	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;
	
	@Autowired
	private ResourceBundleMessageSource workspaceMessageSource;
	
	@RequestMapping("/mail")
	public ModelAndView showMailWindow(){
		ModelAndView modelAndView = new ModelAndView("mailwindow");
		Mail mail=new Mail();
		mail.setFromUserName(new StringBuffer("Kamal"));
		mail.setToUserName("Admin");
		mail.setToEmailId("kamallochan095@gmail.com");
		mail.setCc("");
		mail.setSubject(new StringBuffer("subject"));
		mail.setMsg(new StringBuffer("message"));
		modelAndView.addObject("mailModel", mail);
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/submitMail",headers = {"Content-type=application/json"})
	@ResponseBody
	public Error submitMail(@RequestBody Mail mail){
		Error error=new Error();
		try {
			userService.sendMail(mail);
		} catch (Exception e) {
			error.setErrorMessage("Mail sent failed!");
			return error;
		}
		error.setErrorMessage("Mail sent successfully");
		return error;
	}
	@RequestMapping(method=RequestMethod.POST,value="/dpcMail",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DPCMail notifyMail(@RequestBody DPCMail mail){
		StringBuffer fromUserName = new StringBuffer();
		StringBuffer subject = new StringBuffer();
		StringBuffer message = new StringBuffer();
		String emailID_Blok  = "";
		String blockUserName = "";
		String emailID_District  = "";
		
		
		List<SamikshyaUserRoleSchemeMapping> roleMapping = userRepository.getUserByAreaCode(mail.getBlockcode());
		if(roleMapping.size()>0){
			emailID_Blok = roleMapping.get(0).getSamikshyaUser().getUserEmailId();
			blockUserName = roleMapping.get(0).getSamikshyaUser().getUserName();
		}
		roleMapping = userRepository.getUserByAreaCode(mail.getDistrictcode());	
		if(roleMapping.size()>0){
			emailID_District = roleMapping.get(0).getSamikshyaUser().getUserEmailId();
			fromUserName.append(roleMapping.get(0).getSamikshyaUser().getUserName());
		}	
		
		subject.append("Notification Subject");
		message.append("\nStatus : "+mail.getStatus());
		message.append("\nRemark : "+mail.getRemark());
		
		mail.setCc(emailID_District);// Set DPC email id
		mail.setToEmailId(emailID_Blok); // set block email id
		mail.setToUserName(blockUserName); // Dear block User
		mail.setFromUserName(fromUserName); // thanks and regards
		mail.setSubject(subject);//
		mail.setMsg(message); // content of the mail
		return mail;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/ptcMail",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PtcMail ptcMail(@RequestBody PtcMail mail){
		StringBuffer fromUserName = new StringBuffer();
		StringBuffer subject = new StringBuffer();
		StringBuffer message = new StringBuffer();
		String emailID_District  = "";
		String districtUserName = "";
		
		User user= (User) stateManager.getValue(Constants.USER_PRINCIPAL);
		fromUserName.append(user.getUserName());
		
		//find area code form User
		System.out.println(user.getUserName()+"-------------------user.getUserName());");
		
		List<SamikshyaUserRoleSchemeMapping> roleMapping = userRepository.getUserByAreaCode(mail.getAreaCode());
		if(roleMapping.size()>0){
			emailID_District = roleMapping.get(0).getSamikshyaUser().getUserEmailId();
			districtUserName = roleMapping.get(0).getSamikshyaUser().getUserName();
		}
//		roleMapping = userRepository.getUserByAreaCode(mail.getDistrictcode());	
//		if(roleMapping.size()>0){
//			emailID_District = roleMapping.get(0).getSamikshyaUser().getUserEmailId();
//			fromUserName.append(roleMapping.get(0).getSamikshyaUser().getUserName());
//		}	
//		
		subject.append("Notification Subject for dpc");
		message.append("\nStatus : "+mail.getStatus());
		message.append("\nRemark : "+mail.getRemark());
		message.append("\nArea Name : "+mail.getAreaName());
		message.append("\nTime Period : "+mail.getTimePeriod());
		System.out.println(mail.getRemark()+"----remark");
		
		mail.setCc("");// Set DPC email id
		mail.setToEmailId(emailID_District); // set block email id
		mail.setToUserName(districtUserName); // Dear district User
		mail.setFromUserName(fromUserName); // thanks and regards
		mail.setSubject(subject);//
		mail.setMsg(message); // content of the mail
		return mail;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/ptcToBrccMail",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PtcMail ptcToBrccMail(@RequestBody PtcMail mail){
		StringBuffer fromUserName = new StringBuffer();
		StringBuffer subject = new StringBuffer();
		StringBuffer message = new StringBuffer();
		String emailID_District  = "";
		String districtUserName = "";
		
		User user= (User) stateManager.getValue(Constants.USER_PRINCIPAL);
		fromUserName.append(user.getUserName());
		
		//find area code form User
		System.out.println(user.getUserName()+"-------------------user.getUserName());");
		
		List<SamikshyaUserRoleSchemeMapping> roleMapping = userRepository.getUserByAreaCode(mail.getAreaCode());
		if(roleMapping.size()>0){
			emailID_District = roleMapping.get(0).getSamikshyaUser().getUserEmailId();
			districtUserName = roleMapping.get(0).getSamikshyaUser().getUserName();
		}
//		roleMapping = userRepository.getUserByAreaCode(mail.getDistrictcode());	
//		if(roleMapping.size()>0){
//			emailID_District = roleMapping.get(0).getSamikshyaUser().getUserEmailId();
//			fromUserName.append(roleMapping.get(0).getSamikshyaUser().getUserName());
//		}	
//		
		subject.append("Notification Subject for brcc");
		message.append("\nStatus : "+mail.getStatus());
		message.append("\nRemark : "+mail.getRemark());
		message.append("\nArea Name : "+mail.getAreaName());
		message.append("\nTime Period : "+mail.getTimePeriod());
		System.out.println(mail.getRemark()+"----remark");
		
		mail.setCc("");// Set DPC email id
		mail.setToEmailId(emailID_District); // set block email id
		mail.setToUserName(districtUserName); // Dear district User
		mail.setFromUserName(fromUserName); // thanks and regards
		mail.setSubject(subject);//
		mail.setMsg(message); // content of the mail
		return mail;
	}
	
//	@RequestMapping(value="/contactUs",method = RequestMethod.POST)
	void contactUs(ContactUs cu,RedirectAttributes redirectAttributes){
		try
		{
			String msg = cu.getMsg();
			StringBuffer msgbuff = new StringBuffer(msg);
//			String mailIdOfSender = "\n My Mail Id : ".concat(cu.getMail());
			String mailIdOfSender = workspaceMessageSource.getMessage(Constants.CONTACT_US_MAIL_SENDER_MAILID, null, null).concat(cu.getMail());
			msgbuff.append(mailIdOfSender);
			Mail mail=new Mail();
			mail.setFromUserName(new StringBuffer(cu.getName()));
//			mail.setToUserName("Admin");
			mail.setToUserName(workspaceMessageSource.getMessage(Constants.CONTACT_US_MAIL_TOUSERNAME, null, null));
//			mail.setToEmailId("shanti.swarup.biswal@gmail.com");
			mail.setToEmailId(workspaceMessageSource.getMessage(Constants.CONTACT_US_MAIL_TOMAILID, null, null));
			mail.setCc("");
//			mail.setSubject(new StringBuffer("eSamiksha contact notification"));
			mail.setSubject(new StringBuffer(workspaceMessageSource.getMessage(Constants.CONTACT_US_MAIL_SUBJECT, null, null)));
			mail.setMsg(msgbuff);
			userService.sendMail(mail);
			
			List<String> sucessMsg=new ArrayList<String>();
//			sucessMsg.add("Thank you for contact us. We will catch you soon ");
			sucessMsg.add(workspaceMessageSource.getMessage(Constants.CONTACT_US_MAIL_SEND_SUCCESS, null, null));
			redirectAttributes.addFlashAttribute("formError", sucessMsg);
			redirectAttributes.addFlashAttribute("className", "alert alert-success");
//			return "redirect:/contactus";
			
		}catch(Exception e)
		{
			e.printStackTrace();
			List<String> err=new ArrayList<String>();
//			err.add("Some Eception Occur, try after some time ");
			err.add(workspaceMessageSource.getMessage(Constants.CONTACT_US_MAIL_SEND_FAIL, null, null));
			redirectAttributes.addFlashAttribute("formError", err);
			redirectAttributes.addFlashAttribute("className", "alert alert-danger");
//			return "redirect:/contactus";
		}
	}
	
	 @RequestMapping(value = "/validate",method = RequestMethod.POST)
	    public String validateCaptcha(ModelMap model,
	            HttpServletRequest request,
	            ContactUs contactUs,
	            RedirectAttributes redirectAttributes)
	    {
	        
	        ReCaptchaImpl captcha = new ReCaptchaImpl();
//	        captcha.setPrivateKey("6LdEmfcSAAAAAI4o3jXRsun428TVYUzvPR7v3CRN");
	        captcha.setPrivateKey(applicationMessageSource.getMessage(Constants.CAPTCHA_PRIVATE_KEY, null, null));
	        
	        String challenge = request.getParameter("recaptcha_challenge_field");
	        String uresponse = request.getParameter("recaptcha_response_field");
	        ReCaptchaResponse reCaptchaResponse =
	                captcha.checkAnswer(request.getRemoteAddr(),
	                challenge, uresponse
	            );
	 
	        if (reCaptchaResponse.isValid()) {
	        	
	        		contactUs(contactUs,redirectAttributes);
	        		return "redirect:/contactus";
	           
	        } else {
//	            model.addAttribute("message", "Captcha Validation failed.");
//	            return "contactus";
	        	List<String> err=new ArrayList<String>();
//				err.add("Sorry, Captcha Validation failed. Please try again");
	        	err.add(workspaceMessageSource.getMessage(Constants.CONTACT_US_CAPTCHA_VALIDATION_FAIL, null, null));
				redirectAttributes.addFlashAttribute("formError", err);
				redirectAttributes.addFlashAttribute("className", "alert alert-danger");
				return "redirect:/contactus";
	        }
	        
	    }
	
}
