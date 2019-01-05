package org.september.easyconf.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.september.smartdao.anno.AutoIncrease;
import org.september.smartdao.anno.Column;
import org.september.smartdao.anno.Entity;
import org.september.smartdao.anno.Id;
import org.september.smartdao.anno.Table;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="config_user")
public class ConfigUser  implements UserDetails {
	/**
	 * 
	 */
	private static final transient long serialVersionUID = 9031528552445081773L;

	@Id
    @AutoIncrease
	private Long id;
	
	@Size(min=6,max=15 , message="账号必须大于6位小于16位")
	private String username;
	
	private String password;
	
	@Size(min=1 ,max=32 , message="用户姓名不能为空，且必须小于16汉字或32位英文字母")
    private String fullname;
    
    /**
     * 是否管理员
     * 1 是
     * 0 否
     */
    @Column(name="is_admin")
    private Integer isAdmin;
    
    /**  邮箱，接收验证码等*/
    @Email(regexp="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message="邮件地址格式不正确")
    private String email;
    
    /**  */
    @Column(name="last_login_time")
    private Date lastLoginTime;

    /**  */
    @Column(name="create_time")
    private Date createTime;

    /**  0: 初始化密码   1: 修改后密码*/
    @Column(name="is_revise")
    private Integer isRevise;
    
    private Boolean enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private transient List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	
	@Override
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsRevise() {
		return isRevise;
	}

	public void setIsRevise(Integer isRevise) {
		this.isRevise = isRevise;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
