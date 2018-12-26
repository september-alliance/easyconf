package org.september.easyconf.auth;

import org.september.core.exception.BusinessException;
import org.september.core.util.MD5;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserAuthenticationProvider  extends DaoAuthenticationProvider {

	public UserAuthenticationProvider(UserDetailsService udService) {
		super.setUserDetailsService(udService);
	}
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			throw new BusinessException("用户名或密码错误");
		}

		String presentedPassword = authentication.getCredentials().toString();
		presentedPassword = MD5.md5(presentedPassword);
		if(!presentedPassword.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("用户名或密码错误");
		}
	}
}
