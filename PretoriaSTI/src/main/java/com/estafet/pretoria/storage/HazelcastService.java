package com.estafet.pretoria.storage;

import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.estafet.pretoria.models.DeviceModel;
import com.estafet.pretoria.models.IndicationModel;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class HazelcastService {

	private static final Logger LOG = LoggerFactory.getLogger(HazelcastService.class);
	private static final String mapName = "map";

	@Autowired
	@Qualifier(value = "instance")
	private HazelcastInstance hazelcastInstance;

	@Autowired
	@Qualifier(value = "client")
	private HazelcastInstance hazelcastClient;

	@PreDestroy
	private void destroy() {
		LOG.info("Performing hazelcast shutdown...");
		hazelcastClient.shutdown();
		hazelcastInstance.shutdown();
		LOG.info("Hazelcast resources freed!");
	}

	public boolean saveDeviceIndication(String deviceId, IndicationModel indication) {
		if(deviceId == null){
			throw new NullPointerException("Invalid deviceId value!");
		}
		if(indication ==  null){
			throw new NullPointerException("Invalid indication value!");
		}
		
		IMap<String, DeviceModel> map = getMap();

//		while (true) {
//			if (map.tryLock(deviceId)) {
//				try {
//					DeviceModel device = map.get(deviceId);
//					if (device != null) {
//						device.getIndications().add(indication);
//			
//						// hazelcast return copy of the DeviceModel object
//						// thus we need to replace old object with new modified one
//						map.put(deviceId, device);
//						return true;
//					} else {
//						LOG.warn("Device with id: " + deviceId + " not found!");
//						return false;
//						//map.put(deviceId, new DeviceModel(deviceId, indication));
//					}
//				} finally {
//					map.unlock(deviceId);
//				}
//			}
//		}
		DeviceModel device = map.get(deviceId);
		if (device != null) {
			device.getIndications().add(indication);

			// hazelcast return copy of the DeviceModel object
			// thus we need to replace old object with new modified one
			map.put(deviceId, device);
			return true;
		} else {
			LOG.warn("Device with id: " + deviceId + " not found!");
			return false;
			//map.put(deviceId, new DeviceModel(deviceId, indication));
		}
	}

	public boolean addDevice(String deviceId) {
		if(deviceId == null){
			throw new NullPointerException("Invalid deviceId value!");
		}
		IMap<String, DeviceModel> map = getMap();
		DeviceModel device = map.get(deviceId);
		if (device != null) {
			LOG.warn("Device with id: " + deviceId + " already exists!");
			return false;
		} else {
			map.put(deviceId, new DeviceModel(deviceId));
			return true;
		}
	}

	public boolean removeDevice(String deviceId) {
		if(deviceId == null){
			throw new NullPointerException("Invalid deviceId value!");
		}
		IMap<String, DeviceModel> map = getMap();
		DeviceModel device = map.get(deviceId);
		if (device != null) {
			map.remove(deviceId);
			return true;
		} else {
			LOG.warn("Device with id: " + deviceId + " not found!");
			return false;
		}
	}

	public DeviceModel getDevice(String deviceId) {
		if(deviceId == null){
			throw new NullPointerException("Invalid deviceId value!");
		}
		IMap<String, DeviceModel> map = getMap();
		DeviceModel device = map.get(deviceId);
		if (device != null) {
			return device;
		} else {
			LOG.warn("Device with id: " + deviceId + " not found!");
			return null;
		}
	}

	private IMap<String, DeviceModel> getMap() {
		return hazelcastClient.getMap(mapName);
	}
}
