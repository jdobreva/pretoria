package com.estafet.pretoria.storage;

import com.estafet.pretoria.models.DeviceModel;
import com.hazelcast.core.IMap;

public interface IDeviceManager {

	public boolean saveDeviceIndication(String deviceId, long date, String value);
	public boolean addDevice(String deviceId);
	public boolean setDeviceInactive(String deviceId);
	public boolean removeDevice(String deviceId);
	public DeviceModel getDevice(String deviceId);
	public IMap<String, DeviceModel> getDevicesMap();
	
}
