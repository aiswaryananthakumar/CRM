package com.techietact.crm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techietact.crm.entity.Login;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.entity.Register;
import com.techietact.crm.service.RegisterService;

@Controller
public class LoginController {
	
	@Autowired
    private RegisterService registerService;

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("login", new Login());  // empty object for form
		mv.setViewName("login"); // show login.jsp
		return mv;
	}
	
	@PostMapping("/login")
	public ModelAndView doLogin(@ModelAttribute("login") Login login, HttpServletRequest req) {
	    ModelAndView mv = new ModelAndView();

	    Register reg = registerService.login(login.getUserName(), login.getPassword());
	    if (reg != null) {
	        HttpSession session = req.getSession();
	        session.setAttribute("loggedInUser", reg);
	        mv.setViewName("home");
	    } else {
	        mv.setViewName("login");
	        mv.addObject("login", new Login());
	        mv.addObject("error", "Invalid username or password.");
	    }
	    return mv;
	}


   protected MyCRMUser getUserSecurity() {
		MyCRMUser user=null;
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		if(null!=auth.getPrincipal() && !auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
		user=(MyCRMUser) auth.getPrincipal();
		}
	return user;

	}
   
   //Shows the home page if user is authenticated; else redirects to login.
   @GetMapping("/home")
	public ModelAndView landingpage(HttpServletRequest req) {	
		ModelAndView mv=new ModelAndView();
		MyCRMUser user=getUserSecurity();
		
		//HttpSession session = req.getSession();
		if(null!=user) {
			//Redirect the user to home page after the successful login.
			mv.setViewName("home");
		}else {
			mv.setViewName("login");
		}
		
		return mv;
	}


	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = req.getSession();
		// check whether user has loggedin,if not return to login page
		session.removeAttribute("loggedInUser");
		session.invalidate();
		// Redirect the user to home page after the successful login.
		mv.addObject("login", new Login());
		mv.setViewName("login");
		return mv;
	}

}
