package org.september.easyconf.controller;

import javax.validation.Valid;

import org.september.core.exception.BusinessException;
import org.september.core.util.MD5;
import org.september.core.util.PasswordUtil;
import org.september.easyconf.entity.ConfigUser;
import org.september.simpleweb.model.ResponseVo;
import org.september.simpleweb.utils.SessionHelper;
import org.september.smartdao.CommonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController {

	protected final static Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	@Autowired
	private CommonDao commonDao;

	@RequestMapping(value = "/editProfile")
	public ModelAndView editProfile() {
		ModelAndView mv = new ModelAndView();
		ConfigUser po = commonDao.get(ConfigUser.class, SessionHelper.getSessionUser(ConfigUser.class).getId());
		mv.addObject("user", po);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doUpdateProfile")
	public ResponseVo<String> doUpdateProfile(@Valid ConfigUser entity) {
		commonDao.update(entity);
		SessionHelper.setSessionUser(entity);
		return ResponseVo.<String>BUILDER();
	}

	@RequestMapping(value = "/updatePwd")
	public ModelAndView updatePwd() {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doUpdatePwd")
	public ResponseVo<String> doUpdatePwd(String oldPwd , String newPwd , String confirmPwd) {
		if(StringUtils.isEmpty(oldPwd)) {
			throw new BusinessException("请先输入原密码");
		}
		if(!ObjectUtils.nullSafeEquals(newPwd, confirmPwd)) {
			throw new BusinessException("两次输入的密码不一致");
		}
		
		ConfigUser po = commonDao.get(ConfigUser.class, SessionHelper.getSessionUser(ConfigUser.class).getId());
		if(!ObjectUtils.nullSafeEquals(MD5.md5(oldPwd), po.getPassword())) {
			throw new BusinessException("原密码不正确");
		}
		
		if(!PasswordUtil.isComplexPwd(newPwd)) {
			throw new BusinessException("密码必须大于等于8位，包含数字，大写字母，小写字母，特殊字符中任意两种组合");
		}
		po.setPassword(MD5.md5(newPwd));
		commonDao.update(po);
		return ResponseVo.<String>BUILDER();
	}

}
