package com.estafet.pretoria.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.stereotype.Component;

import com.estafet.pretoria.models.DeviceModel;
import com.estafet.pretoria.models.IndicationModel;

@Component
public class DeviceManager implements IDeviceManager {

	@Autowired
	private HazelcastService hazelcastService;

	@Override
	public boolean saveDeviceIndication(String deviceId, long date, String value) {
		if(deviceId == null){
			throw new NullPointerException("Invalid deviceId value!");
		}
		if(value == null){
			throw new NullPointerException("Invalid indication value!");
		}
		return hazelcastService.saveDeviceIndication(deviceId, new IndicationModel(date, value));
	}

	@Override
	public boolean addDevice(String deviceId) {
		return hazelcastService.addDevice(deviceId);
	}

	@Override
	public boolean removeDevice(String deviceId) {
		return hazelcastService.removeDevice(deviceId);
	}

	@Override
	public DeviceModel getDevice(String deviceId) {
		return hazelcastService.getDevice(deviceId);
	}
}
