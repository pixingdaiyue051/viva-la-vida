package org.util.xml;

import java.math.BigDecimal;

@XmlBeanElement(name = "entry")
public class OutBodyBean {
	@XmlFieldElement(name = "seq")
	private Integer seq;// 分录序号
	@XmlFieldElement(name = "fiMaterialType")
	private String fiMaterialType;// 财务核算类别
	@XmlFieldElement(name = "department")
	private String department;// 部门
	@XmlFieldElement(name = "equipmentType")
	private String equipmentType;// 设备类型
	@XmlFieldElement(name = "issueType")
	private String issueType;// 领用类别
	@XmlFieldElement(name = "price")
	private BigDecimal price;// 单价
	@XmlFieldElement(name = "taxRate")
	private BigDecimal taxRate;// 税率
	@XmlFieldElement(name = "priceInTax")
	private BigDecimal priceInTax;// 含税单价
	@XmlFieldElement(name = "qty")
	private BigDecimal qty;// 数量
	@XmlFieldElement(name = "tax")
	private BigDecimal tax;// 税额
	@XmlFieldElement(name = "amount")
	private BigDecimal amount;// 含税金额
	@XmlFieldElement(name = "noTaxAmount")
	private BigDecimal noTaxAmount;// 不含税金额
	@XmlFieldElement(name = "desc")
	private String desc;// 备注

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getFiMaterialType() {
		return fiMaterialType;
	}

	public void setFiMaterialType(String fiMaterialType) {
		this.fiMaterialType = fiMaterialType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getPriceInTax() {
		return priceInTax;
	}

	public void setPriceInTax(BigDecimal priceInTax) {
		this.priceInTax = priceInTax;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getNoTaxAmount() {
		return noTaxAmount;
	}

	public void setNoTaxAmount(BigDecimal noTaxAmount) {
		this.noTaxAmount = noTaxAmount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}