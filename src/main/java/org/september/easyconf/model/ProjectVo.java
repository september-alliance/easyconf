package org.september.easyconf.model;

import org.september.easyconf.entity.Project;

public class ProjectVo extends Project{

    private String createUser;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
    
}