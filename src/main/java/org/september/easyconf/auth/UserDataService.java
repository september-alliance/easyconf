package org.september.easyconf.auth;

import org.september.core.exception.BusinessException;
import org.september.easyconf.entity.ConfigUser;
import org.september.smartdao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDataService  implements UserDetailsService {

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//TODO 从数据库查
		ConfigUser vo = new ConfigUser();
		vo.setUsername(username);
		ConfigUser user = commonDao.getByExample(vo);
		if(!user.getUsername().equals(username)) {
			throw new BusinessException("用户名或密码错误");
		}
		user.setUsername(username);
		user.setPassword(user.getPassword());
		if(1==user.getIsAdmin()) {
			user.getAuthorities().add(new SimpleGrantedAuthority("ROLE_admin"));
		}else {
			user.getAuthorities().add(new SimpleGrantedAuthority("ROLE_default"));
		}
		return user;
	}

}
