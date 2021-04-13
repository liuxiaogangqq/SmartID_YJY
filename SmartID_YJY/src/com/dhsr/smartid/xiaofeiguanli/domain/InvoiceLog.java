package com.dhsr.smartid.xiaofeiguanli.domain;

public class InvoiceLog {
    private Integer InvoiceLogInnerId;
    /**
     * 操作人
     */
	private Integer OperInnerId;
	/**
	 * 开票金额
	 */
	private Integer Money;
	/**
	 * 操作日期
	 */
	private String OperDT;
	/**
	 * 消费表主键集合
	 */
	private String ConLog;
	public InvoiceLog() {
		super();
	}
	public InvoiceLog(Integer invoiceLogInnerId, Integer operInnerId, Integer money, String operDT, String conLog) {
		super();
		InvoiceLogInnerId = invoiceLogInnerId;
		OperInnerId = operInnerId;
		Money = money;
		OperDT = operDT;
		ConLog = conLog;
	}
	public Integer getInvoiceLogInnerId() {
		return InvoiceLogInnerId;
	}
	public void setInvoiceLogInnerId(Integer invoiceLogInnerId) {
		InvoiceLogInnerId = invoiceLogInnerId;
	}
	public Integer getOperInnerId() {
		return OperInnerId;
	}
	public void setOperInnerId(Integer operInnerId) {
		OperInnerId = operInnerId;
	}
	public Integer getMoney() {
		return Money;
	}
	public void setMoney(Integer money) {
		Money = money;
	}
	public String getOperDT() {
		return OperDT;
	}
	public void setOperDT(String operDT) {
		OperDT = operDT;
	}
	public String getConLog() {
		return ConLog;
	}
	public void setConLog(String conLog) {
		ConLog = conLog;
	}
	@Override
	public String toString() {
		return "InvoiceLog [InvoiceLogInnerId=" + InvoiceLogInnerId + ", OperInnerId=" + OperInnerId + ", Money="
				+ Money + ", OperDT=" + OperDT + ", ConLog=" + ConLog + "]";
	}
	
	
}
