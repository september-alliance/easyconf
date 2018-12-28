package org.september.easyconf.controller;

import java.io.UnsupportedEncodingException;

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
		try {
			logoName = new String(logoName.getBytes("ISO-8859-1") , "utf8");
		} catch (UnsupportedEncodingException e) {
		}
		mv.addObject("logoName", logoName);
		return mv;
	}
}
