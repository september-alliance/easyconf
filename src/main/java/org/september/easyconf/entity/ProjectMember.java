package org.september.easyconf.entity;

import org.september.smartdao.anno.AutoIncrease;
import org.september.smartdao.anno.Column;
import org.september.smartdao.anno.Entity;
import org.september.smartdao.anno.Id;
import org.september.smartdao.anno.Table;

@Entity
@Table(name="project_member")
public class ProjectMember{

    @Id
    @AutoIncrease
    private Long id;

    private Long uid;
    
    @Column(name="project_id")
    private Long projectId;
    
    /**
     * 1 创建者，0 非创建者
     */
    @Column(name="is_creator")
    private Integer isCreator;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getIsCreator() {
		return isCreator;
	}

	public void setIsCreator(Integer isCreator) {
		this.isCreator = isCreator;
	}
    
}
