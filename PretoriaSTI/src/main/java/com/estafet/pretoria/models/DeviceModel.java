package com.estafet.pretoria.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DeviceModel implements Serializable {

	private static final long serialVersionUID = -2119229608746829210L;
	private String deviceId;
	private List<IndicationModel> indications;
	
	public DeviceModel(String deviceId) {
		super();
		this.deviceId = deviceId;
		List<IndicationModel> indications = new LinkedList<IndicationModel>();
		this.indications = indications;
	}
	
	public DeviceModel(String deviceId, List<IndicationModel> indications) {
		super();
		this.deviceId = deviceId;
		this.indications = indications;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public List<IndicationModel> getIndications() {
		return indications;
	}
	public void setIndications(List<IndicationModel> indications) {
		this.indications = indications;
	}

	
}
