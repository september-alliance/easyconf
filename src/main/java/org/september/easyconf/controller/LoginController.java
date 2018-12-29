package org.september.easyconf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class LoginController {

	@Value(value="${easyconf.logoname}")
	private String logoName;
	
	@RequestMapping("/login")
	public ModelAndView login(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("logoName", logoName);
		return mv;
	}
}
