package org.september.easyconf.model;

import org.september.easyconf.entity.Config;

public class ConfigVo extends Config{

    private String envType;
    
    private String projectName;

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    
}