package org.september.easyconf.entity;

import java.util.Date;

import javax.validation.constraints.Size;

import org.september.smartdao.anno.AutoIncrease;
import org.september.smartdao.anno.Column;
import org.september.smartdao.anno.Entity;
import org.september.smartdao.anno.Id;
import org.september.smartdao.anno.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="config")
public class Config{

	@Id
    @AutoIncrease
    private Long id;

	@Column(name="creator_uid")
	private Long creatorUid;
	
    @Column(name="project_id")
    private Long projectId;
    
    /**
     * 配置内容格式 properties , yaml等
     */
    private String format;
    
    @Size(min=1,max=31 , message="环境名称不能为空且小于32位")
    @Column(name="env_name")
    private String envName;
    
    @Size(min=1,max=31 , message="版本号不能为空且小于32位")
    private String version;
    
    private String content;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="last_modify_time")
    private Date lastModifyTime;
    
    @Column(name="last_modify_uid")
    private Long lastModifyUid;
    
    @Column(name="env_type_id")
    private Long envTypeId;
    
    /**1 删除, 0 未删除*/
    @Column(name= "delete_flag")
    private Integer deleteFlag;

    private transient String projectName;
    
    private transient String secret;
    
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Long getLastModifyUid() {
		return lastModifyUid;
	}

	public void setLastModifyUid(Long lastModifyUid) {
		this.lastModifyUid = lastModifyUid;
	}

	public Long getEnvTypeId() {
		return envTypeId;
	}

	public void setEnvTypeId(Long envTypeId) {
		this.envTypeId = envTypeId;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public Long getCreatorUid() {
		return creatorUid;
	}

	public void setCreatorUid(Long creatorUid) {
		this.creatorUid = creatorUid;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
