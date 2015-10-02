package com.estafet.pretoria.storage;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estafet.pretoria.models.DeviceModel;
import com.estafet.pretoria.models.IndicationModel;
import com.hazelcast.core.IMap;

@Component
public class InactiveDeviceWatcher {

	private static final Logger LOG = LoggerFactory.getLogger(InactiveDeviceWatcher.class);
	
	@Autowired
	private DeviceManager deviceManager;
	
	@PostConstruct
	private void startWatching(){
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				IMap<String, DeviceModel> devicesMap = deviceManager.getDevicesMap();
				for(Map.Entry<String, DeviceModel> entry : devicesMap.entrySet()){
					DeviceModel device = entry.getValue();
					if(device.isActive() && device.getIndications().size() > 0){
						long dateValue = ((LinkedList<IndicationModel>)device.getIndications()).getLast().getDate();
						//Here we add 5minutes in miliseconds and compare this time with currentTime
						Date date = new Date(dateValue + 300000);
						if(date.before(new Date())){
							LOG.info("Device with id: " + device.getDeviceId() + " marked as inactive.");
							deviceManager.setDeviceInactive(device.getDeviceId());
						}
					}
				}
			}
		}, 0, 300000l);
	}
}