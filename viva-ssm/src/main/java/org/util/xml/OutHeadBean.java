package org.util.xml;

@XmlBeanElement(name = "billHead")
public class OutHeadBean {
	@XmlFieldElement(name = "CU")
	private String CU;// 控制单元
	@XmlFieldElement(name = "companyName")
	private String companyName;// 公司
	@XmlFieldElement(name = "number")
	private String number;// 单据编号
	@XmlFieldElement(name = "description")
	private String description;// 备注 (非必需)
	@XmlFieldElement(name = "operaterName")
	private String operaterName;// 上传人
	@XmlFieldElement(name = "bizDate")
	private String bizDate;// 业务日期(YYYY-MM-DD)

	public String getCU() {
		return CU;
	}

	public void setCU(String cU) {
		CU = cU;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperaterName() {
		return operaterName;
	}

	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}

	public String getBizDate() {
		return bizDate;
	}

	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}

}
