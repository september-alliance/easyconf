package org.september.easyconf.entity;

import javax.validation.constraints.Size;

import org.september.smartdao.anno.AutoIncrease;
import org.september.smartdao.anno.Column;
import org.september.smartdao.anno.Entity;
import org.september.smartdao.anno.Id;
import org.september.smartdao.anno.Table;

@Entity
@Table(name="env_type")
public class EnvType{

    @Id
    @AutoIncrease
    private Long id;

    @Size(min=1,max=31 , message="环境类型名称不能为空且小于32位")
    private String name;
    
    /**
     * 1开放，项目创建者及成员可以修改，0不开放，管理员才能操作
     */
    @Column(name="public_flag")
    private Integer publicFlag;
    
    /**
     * 获取对应环境类型下配置需要的密码
     */
    @Size(max=32 , message="安全密码必须小于等于32位")
    private String secret;

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

	public Integer getPublicFlag() {
		return publicFlag;
	}

	public void setPublicFlag(Integer publicFlag) {
		this.publicFlag = publicFlag;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
    
}
