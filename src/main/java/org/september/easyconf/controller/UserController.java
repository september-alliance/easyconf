package org.september.easyconf.controller;

import java.util.Date;

import javax.validation.Valid;

import org.september.core.exception.BusinessException;
import org.september.core.util.MD5;
import org.september.core.util.PasswordUtil;
import org.september.easyconf.entity.ConfigUser;
import org.september.easyconf.service.NofityService;
import org.september.simpleweb.model.ResponseVo;
import org.september.smartdao.CommonDao;
import org.september.smartdao.CommonValidator;
import org.september.smartdao.model.Page;
import org.september.smartdao.model.SmartParamMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {

	protected final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CommonValidator commonValidator;

	@Autowired
	private NofityService nofityService;

	@RequestMapping("/userList")
	public ModelAndView userList() {
		ModelAndView mv = new ModelAndView();
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "listUserData")
	public ResponseVo<Page<ConfigUser>> listUserData(Page<ConfigUser> page, String username) {
		SmartParamMap pm = new SmartParamMap();
		pm.put("username", username);
		page = commonDao.findPageByParams(ConfigUser.class, page, "ConfigUser.listConfigUserData", pm);
		return ResponseVo.<Page<ConfigUser>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/addUser")
	public ModelAndView addUser() throws Exception {
		ModelAndView mv = new ModelAndView();
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doAddUser")
	public ResponseVo<String> doAddUser(@Valid ConfigUser entity) {
		if (commonValidator.exsits(ConfigUser.class, new String[] { "username" }, new Object[] { entity.getUsername() })) {
			throw new BusinessException("用户名重复");
		}
		entity.setCreateTime(new Date());
		entity.setIsAdmin(0);
		String pwd = PasswordUtil.randomPassword();
		entity.setPassword(MD5.md5(pwd));
		commonDao.save(entity);
		// send email
		nofityService.userAccountSetup(entity, pwd);
		return ResponseVo.<String>BUILDER().setData("").setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/editUser")
	public ModelAndView editUser(Long id) {
		ModelAndView mv = new ModelAndView();
		ConfigUser po = commonDao.get(ConfigUser.class, id);
		mv.addObject("user", po);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doUpdateUser")
	public ResponseVo<String> doUpdateUser(@Valid ConfigUser entity) {
		if (commonValidator.exsitsNotMe(ConfigUser.class, new String[] { "username" },
				new Object[] { entity.getUsername() }, entity.getId())) {
			throw new BusinessException("用户名重复");
		}
		commonDao.update(entity);
		return ResponseVo.<String>BUILDER();
	}

	@ResponseBody
	@RequestMapping(value = "/resetPassword")
	public ResponseVo<String> resetPassword(Long id) {
		ConfigUser user = new ConfigUser();
		user.setId(id);
		String pwd = PasswordUtil.randomPassword();
		user.setPassword(MD5.md5(pwd));
		commonDao.update(user);
		// send email
		ConfigUser entity = commonDao.get(ConfigUser.class, id);
		nofityService.passwodReseted(entity, pwd);
		return ResponseVo.<String>BUILDER();
	}

	@ResponseBody
	@RequestMapping(value = "/deleteUser")
	public ResponseVo<String> deleteUser(Long id) throws Exception {
		ConfigUser po = commonDao.get(ConfigUser.class, id);
		if (po == null) {
			throw new BusinessException("数据不存在或已删除");
		}
		if (1 == po.getIsAdmin()) {
			throw new BusinessException("系统管理员账号不能删除");
		}
		commonDao.delete(po);
		return ResponseVo.<String>BUILDER().setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
}
