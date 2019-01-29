package com.teuqueno.entity.sys;

import com.teuqueno.entity.BaseEntity;

import javax.persistence.Table;

@Table(name = "um_resource_info")
public class UmResourceInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5394622621482086854L;

	private String pid;

	private String resZhName;

	private String resCnName;

	private Integer grade;

	private String resUrl;

	private Integer seq;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getResZhName() {
		return resZhName;
	}

	public void setResZhName(String resZhName) {
		this.resZhName = resZhName;
	}

	public String getResCnName() {
		return resCnName;
	}

	public void setResCnName(String resCnName) {
		this.resCnName = resCnName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}