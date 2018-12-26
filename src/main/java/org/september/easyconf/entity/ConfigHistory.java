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
@Table(name="config_history")
public class ConfigHistory {

	@Id
    @AutoIncrease
    private Long id;

	@Column(name="config_id")
	private Long configId;
	
    private String content;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="create_time")
    private Date createTime;
    
    @Size(min=1,max=31 , message="版本号不能为空且小于32位")
    private String version;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
