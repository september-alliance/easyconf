package org.september.easyconf.controller;

import org.september.core.exception.BusinessException;
import org.september.easyconf.entity.ConfigUser;
import org.september.easyconf.entity.ProjectMember;
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
@RequestMapping("/member")
public class MemberController {

	protected final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CommonValidator commonValidator;

	@RequestMapping("/memberList")
	public ModelAndView memberList(Long projectId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("projectId", projectId);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "listMemberData")
	public ResponseVo<Page<ConfigUser>> listMemberData(Page<ConfigUser> page, Long projectId) {
		SmartParamMap pm = new SmartParamMap();
		pm.put("projectId", projectId);
		page = commonDao.findPageByParams(ConfigUser.class, page, "ProjectMember.listProjectMemberData", pm);
		return ResponseVo.<Page<ConfigUser>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
	
	@ResponseBody
	@RequestMapping(value = "listUserData")
	public ResponseVo<Page<ConfigUser>> listUserData(Page<ConfigUser> page, String username) {
		SmartParamMap pm = new SmartParamMap();
		pm.put("username", username);
		page = commonDao.findPageByParams(ConfigUser.class, page, "ConfigUser.listMemberUserData", pm);
		return ResponseVo.<Page<ConfigUser>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/doAddMember")
	public ResponseVo<String> doAddMember(ProjectMember entity) {
		 if(commonValidator.exsits(ProjectMember.class, new String[]{"projectId" , "uid"},new Object[]{entity.getProjectId() , entity.getUid()})){
			 throw new BusinessException("用户已添加");
		 }
		 commonDao.save(entity);
		return ResponseVo.<String>BUILDER().setData("").setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteMember")
	public ResponseVo<String> deleteMember(Long id) throws Exception {
		ProjectMember po = commonDao.get(ProjectMember.class, id);
		if (po == null) {
			throw new BusinessException("数据不存在或已删除");
		}
		if(po.getIsCreator()!=null && po.getIsCreator()==1) {
			throw new BusinessException("不能删除创建者");
		}
		commonDao.delete(po);
		return ResponseVo.<String>BUILDER().setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
}
