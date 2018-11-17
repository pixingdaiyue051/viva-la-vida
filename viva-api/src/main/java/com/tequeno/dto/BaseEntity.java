package com.tequeno.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8415182066150496366L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select replace(uuid(), '-', '')")
	protected String id;

	protected String createById;

	protected String modifyById;

	protected Date createTime;

	protected Date modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getModifyById() {
		return modifyById;
	}

	public void setModifyById(String modifyById) {
		this.modifyById = modifyById;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
