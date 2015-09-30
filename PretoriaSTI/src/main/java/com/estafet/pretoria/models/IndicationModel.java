package com.estafet.pretoria.models;

import java.io.Serializable;

public class IndicationModel implements Serializable{
	
	private static final long serialVersionUID = 7900446158923698198L;
	private long date;
	private String value;
	
	public IndicationModel(long date, String value) {
		super();
		this.date = date;
		this.value = value;
	}
	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
}
