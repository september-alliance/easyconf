package org.september.easyconf.controller;

import javax.validation.Valid;

import org.september.core.exception.BusinessException;
import org.september.core.exception.NotAuthorizedException;
import org.september.easyconf.entity.ConfigUser;
import org.september.easyconf.entity.Project;
import org.september.easyconf.entity.ProjectMember;
import org.september.easyconf.model.ProjectVo;
import org.september.easyconf.service.ProjectService;
import org.september.simpleweb.controller.BaseController;
import org.september.simpleweb.model.ResponseVo;
import org.september.simpleweb.utils.SessionHelper;
import org.september.smartdao.CommonDao;
import org.september.smartdao.CommonValidator;
import org.september.smartdao.model.Page;
import org.september.smartdao.model.SmartParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController{

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CommonValidator commonValidator;
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping("/projectList")
	public ModelAndView projectList() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId", SessionHelper.getSessionUser(ConfigUser.class).getId());
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "listProjectData")
	public ResponseVo<Page<ProjectVo>> listProjectData(Page<ProjectVo> page, String name) {
		SmartParamMap pm = new SmartParamMap();
		if(1!=SessionHelper.getSessionUser(ConfigUser.class).getIsAdmin()) {
			// 非管理员只能看自己的数据
			pm.put("createUid", SessionHelper.getSessionUser(ConfigUser.class).getId());
		}
		page = commonDao.findPageByParams(ProjectVo.class, page, "Project.listProjectData", pm);
		return ResponseVo.<Page<ProjectVo>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/addProject")
	public ModelAndView addProject() throws Exception {
		ModelAndView mv = new ModelAndView();
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doAddProject")
	public ResponseVo<String> doAddProject(@Valid Project entity) {
		if (commonValidator.exsits(Project.class, new String[] { "name"},new Object[] { entity.getName()})) {
			throw new BusinessException("工程名重复");
		}
		
		projectService.createProject(entity);
		return ResponseVo.<String>BUILDER().setData("").setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/editProject")
	public ModelAndView editProject(Long id) {
		checkDataPriviledge(id);
		ModelAndView mv = new ModelAndView();
		Project po = commonDao.get(Project.class, id);
		mv.addObject("project", po);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doUpdateProject")
	public ResponseVo<String> doUpdateProject(@Valid Project entity) {
		checkDataPriviledge(entity.getId());
		if(commonValidator.exsitsNotMe(Project.class, new String[]{"name"},new Object[]{entity.getName()} , entity.getId())){
			 throw new BusinessException("工程名称重复");
		 }
		commonDao.update(entity);
		return ResponseVo.<String>BUILDER();
	}

	@ResponseBody
	@RequestMapping(value = "/deleteProject")
	public ResponseVo<String> deleteProject(Long id) throws Exception {
		checkDataPriviledge(id);
		Project po = commonDao.get(Project.class, id);
		if (po == null) {
			throw new BusinessException("数据不存在或已删除");
		}
		
		projectService.deleteProject(po);
		return ResponseVo.<String>BUILDER().setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
	
	private void checkDataPriviledge(Object dataId) {
		Project po = commonDao.get(Project.class, (Long) dataId);
		ProjectMember pmVo = new ProjectMember();
		pmVo.setProjectId(po.getId());
		ConfigUser me = SessionHelper.getSessionUser(ConfigUser.class);
		pmVo.setUid(me.getId());
		
		ProjectMember pm = commonDao.getByExample(pmVo);
		if(1!=me.getIsAdmin() && pm==null) {
			throw new NotAuthorizedException("没有权限操作");
		}
	}
}
