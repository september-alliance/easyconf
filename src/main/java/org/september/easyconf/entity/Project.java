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
@Table(name="project")
public class Project{

    @Id
    @AutoIncrease
    private Long id;

    /** 创建者id*/
    private Long uid;
    
    public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Size(min=1,max=31 , message="工程名称不能为空且小于32位")
    private String name;
    
    /** 负责人姓名*/
    private String owner;
    
    /** 负责人联系方式*/
    private String contact;
    
    private String remark;
    
    /** 标签，分组空格隔开*/
    private String label;
    
    @Column(name="create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
