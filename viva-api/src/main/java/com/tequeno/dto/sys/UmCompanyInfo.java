package com.tequeno.dto.sys;

import com.tequeno.dto.BaseEntity;

import javax.persistence.Table;

@Table(name = "um_company_info")
public class UmCompanyInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4299222702057438643L;

	private String pid;

	private String companyName;

	private Integer grade;

	private Integer seq;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}