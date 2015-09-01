package org.sdrc.evm.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.sdrc.evm.model.User;
import org.sdrc.evm.service.UserService;
import org.sdrc.evm.util.Constants;
import org.sdrc.evm.util.StateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Controller used to authenticate an user.
 * 
 * @author Harekrishna
 * modified by Kahnu Charan
 *        
 */
@Controller
public class OauthController {

	
	private final UserService userService;
	private final StateManager stateManager;
	
	private final ResourceBundleMessageSource messageSource;
	
	private static final Logger logger = Logger.getLogger(OauthController.class);
	
	@Autowired
	public OauthController(UserService userService, StateManager stateManager,ResourceBundleMessageSource messageSource) {
		this.userService = userService;
		this.stateManager = stateManager;
		this.messageSource=messageSource;
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request) throws IOException {
		logger.info("Controller is called");
		System.out.println("Controller is called");

		ClientAuthenticationToken token = (ClientAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		UserProfile profile = token.getUserProfile();
		
		String emailId = (String) profile.getAttribute(Constants.ATTRIBUTE_EMAIL);
		Collection<User> user = userService.findUserByEmail(emailId);

		if(user == null || user.size() ==0)
			throw new BadCredentialsException(messageSource.getMessage(Constants.BAD_CREDENTIALS_LOGIN, null, null));
		User authUser = user != null ?(User) user.toArray()[0] : null;
		stateManager.setValue(Constants.USER_PRINCIPAL, authUser);
		
		//TO DO: null check
		String referer = request.getHeader(Constants.REFERER);
		System.out.println(referer);
		
		return Constants.REDIRECT+ "/";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public void logout(HttpServletRequest request,HttpServletResponse resp) throws IOException, ServletException
	{
		stateManager.setValue(Constants.USER_PRINCIPAL, null);
		request.getSession().setAttribute(Constants.USER_PRINCIPAL, null);
		request.getSession().invalidate();
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		attr.getRequest().getSession(true).removeAttribute(Constants.USER_PRINCIPAL);
		attr.getRequest().getSession(true).invalidate();
		
		
		for(Cookie cookie : request.getCookies()){
		cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.setContentType("text/html");
        resp.addCookie(cookie);
		}
		
		request.logout();
		
		Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        cookie = new Cookie("LSOSID",null);
        resp.addCookie(cookie); 
	}
}
