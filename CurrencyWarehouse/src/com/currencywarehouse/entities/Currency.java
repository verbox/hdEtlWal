package com.currencywarehouse.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Currency implements Serializable{
	private static final long serialVersionUID = -1500338594457436294L;
	private String currencyCode;
	private Date date;
	private BigDecimal value = new BigDecimal(0);
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
