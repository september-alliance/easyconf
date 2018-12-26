package org.september.easyconf.auth;

import org.september.simpleweb.auth.SimpleWebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class WebSecurityConfiguration extends SimpleWebSecurityConfiguration {

	@Autowired
	private UserDataService userDataService;

	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests().antMatchers("/user/**").hasRole("admin").anyRequest().authenticated();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new UserAuthenticationProvider(userDataService));
	}
	
}
