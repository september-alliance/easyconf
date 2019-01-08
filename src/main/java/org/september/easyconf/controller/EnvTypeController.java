package org.september.easyconf.controller;

import javax.validation.Valid;

import org.september.core.exception.BusinessException;
import org.september.easyconf.entity.Config;
import org.september.easyconf.entity.EnvType;
import org.september.simpleweb.model.ResponseVo;
import org.september.smartdao.CommonDao;
import org.september.smartdao.CommonValidator;
import org.september.smartdao.model.Page;
import org.september.smartdao.model.SmartParamMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/envType")
public class EnvTypeController {

	protected final static Logger logger = LoggerFactory.getLogger(EnvTypeController.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CommonValidator commonValidator;

	@RequestMapping("/envTypeList")
	public ModelAndView envTypeList() {
		ModelAndView mv = new ModelAndView();
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "listEnvTypeData")
	public ResponseVo<Page<EnvType>> listEnvTypeData(Page<EnvType> page, String name) {
		SmartParamMap pm = new SmartParamMap();
		pm.put("name", name);
		page = commonDao.findPageByParams(EnvType.class, page, "EnvType.listEnvTypeData", pm);
		return ResponseVo.<Page<EnvType>>BUILDER().setData(page).setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/addEnvType")
	public ModelAndView addEnvType() throws Exception {
		ModelAndView mv = new ModelAndView();
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doAddEnvType")
	public ResponseVo<String> doAddEnvType(@Valid EnvType entity) {
		 if(commonValidator.exsits(EnvType.class, new String[]{"name"},new Object[]{entity.getName()})){
			 throw new BusinessException("环境类型名称重复");
		 }
		 if(0==entity.getPublicFlag() && StringUtils.isEmpty(entity.getSecret())) {
			 throw new BusinessException("机密型环境必须设置安全密码");
		 }
		 commonDao.save(entity);
		return ResponseVo.<String>BUILDER().setData("").setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}

	@RequestMapping(value = "/editEnvType")
	public ModelAndView editEnvType(Long id) {
		ModelAndView mv = new ModelAndView();
		EnvType po = commonDao.get(EnvType.class, id);
		mv.addObject("envType", po);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/doUpdateEnvType")
	public ResponseVo<String> doUpdateEnvType(@Valid EnvType entity) {
		if(commonValidator.exsitsNotMe(EnvType.class, new String[]{"name"},new Object[]{entity.getName()} , entity.getId())){
			 throw new BusinessException("环境类型名称重复");
		 }
		if(0==entity.getPublicFlag() && StringUtils.isEmpty(entity.getSecret())) {
			 throw new BusinessException("机密型环境必须设置安全密码");
		 }
		commonDao.update(entity);
		return ResponseVo.<String>BUILDER();
	}

	@ResponseBody
	@RequestMapping(value = "/deleteEnvType")
	public ResponseVo<String> deleteEnvType(Long id) throws Exception {
		EnvType po = commonDao.get(EnvType.class, id);
		if (po == null) {
			throw new BusinessException("数据不存在或已删除");
		}
		//TODO 删除的限制条件
		Config cfgVo = new Config();
		cfgVo.setEnvTypeId(po.getId());
		cfgVo.setDeleteFlag(0);
		int total = commonDao.countByExample(cfgVo);
		if(total>0) {
			throw new BusinessException("请先删除环境类型"+po.getName()+"下的配置信息");
		}
		commonDao.delete(po);
		return ResponseVo.<String>BUILDER().setCode(ResponseVo.BUSINESS_CODE.SUCCESS);
	}
}
