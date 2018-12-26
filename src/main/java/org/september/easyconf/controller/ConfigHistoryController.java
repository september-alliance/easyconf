package org.september.easyconf.controller;

import java.util.Date;

import javax.validation.Valid;

import org.september.core.exception.BusinessException;
import org.september.easyconf.entity.Config;
import org.september.easyconf.entity.ConfigHistory;
import org.september.easyconf.entity.Project;
import org.september.easyconf.model.ConfigHistoryVo;
import org.september.simpleweb.model.ResponseVo;
import org.september.smartdao.CommonDao;
import org.september.smartdao.CommonValidator;
import org.september.smartdao.model.Page;
import org.september.smartdao.model.ParamMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/configHistory")
public class ConfigHistoryController {

	protected final static Logger logger = LoggerFactory.getLogger(ConfigHistoryController.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CommonValidator commonValidator;

	@RequestMapping("/configHistoryList")
	public ModelAndView configHistoryList(Long configId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("configId", configId);
		Config cfg = commonDao.get(Config.class, configId);
		Project project = commonDao.get(Project.class, cfg.getProjectId());
		mv.addObject("projectName", project.getName());
		mv.addObject("envName", cfg.getEnvName());
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "listConfigHistoryData")
	public ResponseVo<Page<ConfigHistoryVo>> listConfigHistoryData(Page<ConfigHistoryVo> page, Long configId) {
		ParamMap pm = new ParamMap();
		pm.put("configId", configId);
		page = commonDao.findPageByParams(ConfigHistoryVo.class, page, "ConfigHistory.listConfigHistoryData", pm);
		return ResponseVo.<Page<ConfigHistoryVo>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/addConfigHistory")
	public ModelAndView addConfig(Long configId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Config po = commonDao.get(Config.class, configId);
		mv.addObject("configId", configId);
		mv.addObject("version", po.getVersion());
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doAddConfigHistory")
	public ResponseVo<String> doAddConfig(@Valid ConfigHistory entity) {
		if (commonValidator.exsits(ConfigHistory.class, new String[] { "configId" , "version"},new Object[] { entity.getConfigId() , entity.getVersion()})) {
			throw new BusinessException("版本重复");
		}
		entity.setCreateTime(new Date());
		Config config = commonDao.get(Config.class, entity.getConfigId());
		entity.setContent(config.getContent());
		commonDao.save(entity);
		return ResponseVo.<String>BUILDER().setData("").setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteConfigHistory")
	public ResponseVo<String> deleteConfigHistory(Long id) throws Exception {
		ConfigHistory po = commonDao.get(ConfigHistory.class, id);
		if (po == null) {
			throw new BusinessException("数据不存在或已删除");
		}
		commonDao.delete(po);
		return ResponseVo.<String>BUILDER().setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
}
