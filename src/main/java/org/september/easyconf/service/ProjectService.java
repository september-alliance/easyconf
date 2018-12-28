package org.september.easyconf.service;

import java.util.Date;

import org.september.core.exception.BusinessException;
import org.september.easyconf.entity.Config;
import org.september.easyconf.entity.ConfigUser;
import org.september.easyconf.entity.Project;
import org.september.easyconf.entity.ProjectMember;
import org.september.simpleweb.utils.SessionHelper;
import org.september.smartdao.CommonDao;
import org.september.smartdao.model.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

	@Autowired
	private CommonDao commonDao;
	
	@Transactional
	public void createProject(Project entity){
		entity.setUid(SessionHelper.getSessionUser(ConfigUser.class).getId());
		entity.setCreateTime(new Date());
		commonDao.get(ConfigUser.class, entity.getUid());
		commonDao.save(entity);
		// and selt to member list
		ProjectMember member = new ProjectMember();
		member.setProjectId(entity.getId());
		member.setUid(entity.getUid());
		member.setIsCreator(1);
		commonDao.save(member);
	}

	public void deleteProject(Project po) {
		// 如果由配置关联工程，需先删除配置
		Config cfg = new Config();
		cfg.setProjectId(po.getId());
		cfg.setDeleteFlag(0);
		int count = commonDao.countByExample(cfg);
		if(count>0) {
			throw new BusinessException("请先删除工程下的配置信息");
		}
		commonDao.delete(po);
		
		// 删除member信息
		ParamMap pm = new ParamMap();
		pm.put("projectId", po.getId());
		commonDao.execute("ProjectMember.deleteMembers", pm);
	}
}
