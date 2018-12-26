package org.september.easyconf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class LoginController {

	@RequestMapping("/login")
	public ModelAndView login(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
}
