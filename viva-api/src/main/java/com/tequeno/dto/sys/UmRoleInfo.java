package com.tequeno.dto.sys;

import com.tequeno.dto.BaseEntity;

import javax.persistence.Table;

@Table(name = "um_role_info")
public class UmRoleInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8411804101743032789L;
	
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}