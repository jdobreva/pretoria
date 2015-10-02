package com.estafet.pretoria.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.estafet.pretoria.models.DeviceModel;
import com.estafet.pretoria.models.IndicationModel;
import com.hazelcast.core.IMap;

@Component
public class DeviceManager implements IDeviceManager {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceManager.class);
	
	@Autowired
	private HazelcastService hazelcastService;

	@Override
	public boolean saveDeviceIndication(String deviceId, long date, String value) {
		if(deviceId == null){
			LOG.warn("Invalid deviceId value!");
			throw new NullPointerException("Invalid deviceId value!");
		}
		if(value == null){
			LOG.warn("Invalid indication value!");
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

	@Override
	public boolean setDeviceInactive(String deviceId) {
		return hazelcastService.setDeviceInactive(deviceId);
	}

	@Override
	public IMap<String, DeviceModel> getDevicesMap() {
		return hazelcastService.getDevicesMap();
	}
}
