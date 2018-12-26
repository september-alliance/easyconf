package org.september.easyconf.model;

import org.september.easyconf.entity.ConfigHistory;

public class ConfigHistoryVo extends ConfigHistory{

    private String envName;
    
    private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}
    
}