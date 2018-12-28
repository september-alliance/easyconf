package org.september.easyconf.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.september.core.exception.BusinessException;
import org.september.core.exception.NotAuthorizedException;
import org.september.easyconf.entity.Config;
import org.september.easyconf.entity.ConfigHistory;
import org.september.easyconf.entity.ConfigUser;
import org.september.easyconf.entity.EnvType;
import org.september.easyconf.entity.Project;
import org.september.easyconf.entity.ProjectMember;
import org.september.simpleweb.auth.PublicMethod;
import org.september.simpleweb.model.ResponseVo;
import org.september.simpleweb.utils.SessionHelper;
import org.september.smartdao.CommonDao;
import org.september.smartdao.CommonValidator;
import org.september.smartdao.model.Page;
import org.september.smartdao.model.SmartParamMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;

@RestController
@RequestMapping("/config")
public class ConfigController {

	protected final static Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CommonValidator commonValidator;

	@RequestMapping("/configList")
	public ModelAndView configList(String projectName) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId", SessionHelper.getSessionUser(ConfigUser.class).getId());
		mv.addObject("projectName",projectName);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "listConfigData")
	public ResponseVo<Page<Config>> listConfigData(Page<Config> page, String projectName , String envName) {
		SmartParamMap pm = new SmartParamMap();
		pm.put("projectName", projectName);
		pm.put("envName", envName);
		if(1!=SessionHelper.getSessionUser(ConfigUser.class).getIsAdmin()) {
			// 非管理员只能看自己的数据
			pm.put("uid", SessionHelper.getSessionUser(ConfigUser.class).getId());
			// 非管理员只能看开放型环境的配置
			pm.put("publicFlag", 1);
		}
		page = commonDao.findPageByParams(Config.class, page, "Config.listConfigData", pm);
		return ResponseVo.<Page<Config>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/addConfig")
	public ModelAndView addConfig(Long projectId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Project po = commonDao.get(Project.class, projectId);
		mv.addObject("project", po);
		
		EnvType envType = new EnvType();
		if(0==SessionHelper.getSessionUser(ConfigUser.class).getIsAdmin()) {
			//非管理员只能添加开放型环境配置
			envType.setPublicFlag(1);
		}
		List<EnvType> envTypeList = commonDao.listByExample(envType);
		mv.addObject("envTypeList", envTypeList);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doAddConfig")
	public ResponseVo<String> doAddConfig(@Valid Config entity) {
		if (commonValidator.exsits(Config.class, new String[] { "projectId" , "envName"},new Object[] { entity.getProjectId() , entity.getEnvName()})) {
			throw new BusinessException("环境名称重复");
		}
		entity.setLastModifyTime(new Date());
		entity.setCreatorUid(SessionHelper.getSessionUser(ConfigUser.class).getId());
		entity.setLastModifyUid(entity.getCreatorUid());
		commonDao.save(entity);
		return ResponseVo.<String>BUILDER().setData("").setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/editConfig")
	public ModelAndView editConfig(Long id) {
		checkDataPriviledge(id);
		ModelAndView mv = new ModelAndView();
		Config po = commonDao.get(Config.class, id);
		mv.addObject("config", po);
		Project project = commonDao.get(Project.class, po.getProjectId());
		mv.addObject("project", project);
		EnvType envType = commonDao.get(EnvType.class, po.getEnvTypeId());
		mv.addObject("envTypeName", envType.getName());
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doUpdateConfig")
	public ResponseVo<String> doUpdateConfig(@Valid Config entity) {
		checkDataPriviledge(entity.getId());
		if(commonValidator.exsitsNotMe(Config.class, new String[]{"projectId","envName"},new Object[]{entity.getProjectId() , entity.getEnvName()} , entity.getId())){
			 throw new BusinessException("环境名称重复");
		 }
		entity.setLastModifyUid(SessionHelper.getSessionUser(ConfigUser.class).getId());
		entity.setLastModifyTime(new Date());
		commonDao.update(entity);
		return ResponseVo.<String>BUILDER();
	}
	
	@RequestMapping(value = "/editContent")
	public ModelAndView editContent(Long id) {
		checkDataPriviledge(id);
		ModelAndView mv = new ModelAndView();
		Config po = commonDao.get(Config.class, id);
		mv.addObject("config", po);
		Project project = commonDao.get(Project.class, po.getProjectId());
		mv.addObject("projectName", project.getName());
		mv.addObject("envName", po.getEnvName());
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doUpdateContent")
	public ResponseVo<String> doUpdateContent(Long id ,String format, String content) {
		checkDataPriviledge(id);
		Config cfg = new Config();
		cfg.setId(id);
		cfg.setContent(content);
		cfg.setLastModifyTime(new Date());
		cfg.setFormat(format);
		cfg.setLastModifyUid(SessionHelper.getSessionUser(ConfigUser.class).getId());
		commonDao.update(cfg);
		return ResponseVo.<String>BUILDER();
	}

	@ResponseBody
	@RequestMapping(value = "/deleteConfig")
	public ResponseVo<String> deleteConfig(Long id) throws Exception {
		checkDataPriviledge(id);
		Config po = commonDao.get(Config.class, id);
		if (po == null) {
			throw new BusinessException("数据不存在或已删除");
		}
		Long myUid = SessionHelper.getSessionUser(ConfigUser.class).getId();
		if(po.getCreatorUid()!=null && po.getCreatorUid()!=myUid) {
			throw new BusinessException("只有创建者才可以删除");
		}
		commonDao.delete(po);
		return ResponseVo.<String>BUILDER().setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
	
	@PublicMethod
	@ResponseBody
	@RequestMapping(value = "/getConfig*/{project}/{env}/{version}")
	public String getConfig2(@PathVariable String project , @PathVariable String env , @PathVariable String version) throws Exception {
		return getConfig(project , env , version , "");
	}
	@PublicMethod
	@ResponseBody
	@RequestMapping(value = "/getConfig*/{project}/{env}/{version}/{secret}")
	public String getConfig(@PathVariable String project , @PathVariable String env , @PathVariable String version 
			, @PathVariable String secret) throws Exception {
		if(StringUtils.isEmpty(project)) {
			throw new BusinessException("project can not be empty");
		}
		
		if(StringUtils.isEmpty(env)) {
			throw new BusinessException("env can not be empty");
		}
		
		if(StringUtils.isEmpty(version)) {
			throw new BusinessException("version can not be empty");
		}
		logger.info("get config ,project = {} ，env = {} ， version ={}, secret={}",project , env , version , secret);
		Project projectVo = new Project();
		projectVo.setName(project);
		Project projectPo = commonDao.getByExample(projectVo);
		if(projectPo==null) {
			throw new BusinessException("project name {"+project+"} not found");
		}
		
		Config cfgVo = new Config();
		cfgVo.setProjectId(projectPo.getId());
		cfgVo.setEnvName(env);
		cfgVo.setDeleteFlag(0);
		
		Config po = commonDao.getByExample(cfgVo);
		if (po == null) {
			throw new BusinessException("config not found");
		}
		EnvType envType = commonDao.get(EnvType.class, po.getEnvTypeId());
		if(0==envType.getPublicFlag()) {
			if(StringUtils.isEmpty(secret)) {
				throw new BusinessException("secret for env ["+env+"] is required.");
			}
			//机密型
			if(!StringUtils.equals(secret, envType.getSecret())) {
				throw new BusinessException("secret not correct, please check again , do not contains #%? ");
			}
		}
		
		if(version.equals(po.getVersion())) {
			return po.getContent();
		}else {
			ConfigHistory hisVo = new ConfigHistory();
			hisVo.setConfigId(po.getId());
			hisVo.setVersion(version);
			ConfigHistory hisPo = commonDao.getByExample(hisVo);
			if(hisPo==null) {
				throw new BusinessException("history config with version "+version+" not found");
			}
			return hisPo.getContent();
		}
	}
	
	private void checkDataPriviledge(Object dataId) {
		Config po = commonDao.get(Config.class, (Long) dataId);
		ProjectMember pmVo = new ProjectMember();
		pmVo.setProjectId(po.getProjectId());
		ConfigUser me = SessionHelper.getSessionUser(ConfigUser.class);
		pmVo.setUid(me.getId());
		
		ProjectMember pm = commonDao.getByExample(pmVo);
		if(1!=me.getIsAdmin() && pm==null) {
			throw new NotAuthorizedException("没有权限操作");
		}
	}
}
