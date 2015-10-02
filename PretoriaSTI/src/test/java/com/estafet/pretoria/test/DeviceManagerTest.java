package com.estafet.pretoria.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import java.util.Date;

import com.estafet.pretoria.models.IndicationModel;
import com.estafet.pretoria.storage.DeviceManager;
import com.estafet.pretoria.storage.HazelcastService;
import com.hazelcast.core.HazelcastInstance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class DeviceManagerTest {

	@Autowired
	@Qualifier(value="instance")
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private DeviceManager deviceManager;
	
	private static final String deviceId = "device1";
	
	@Before
	public void clearDeviceData(){
		hazelcastInstance.getMap("map").clear();
	}
	
	/*  Add device function tests  */
	@Test(expected=NullPointerException.class)
	public void testAddNewDeviceWithNullParameter(){
		deviceManager.addDevice(null);
	}
	
	@Test()
	public void testAddNewDeviceWithStrangeName(){
		assertTrue("Error while adding device.", deviceManager.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb"));
	}
	
	@Test()
	public void testAddNewDevice(){
		assertTrue("Error while adding device.", deviceManager.addDevice(deviceId));
	}
	
	@Test
	public void testAddNewDeviceSecondTime(){
		assertTrue("Error while adding device.", deviceManager.addDevice(deviceId));
		assertFalse("Error while adding device second time.", deviceManager.addDevice(deviceId));
	}
	
	/*  Remove device function tests  */
	@Test(expected=NullPointerException.class)
	public void testRemoveDeviceWithNullParameter(){
		deviceManager.removeDevice(null);
	}
	
	@Test()
	public void testRemoveDeviceWithStrangeName(){
		deviceManager.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb");
		assertTrue("Error while removing device with strange name.", deviceManager.removeDevice("#$DSWDNKASD*S asd as d8asbd 8asb"));
	}
	
	@Test()
	public void testRemoveDevice(){
		deviceManager.addDevice(deviceId);
		assertTrue("Error while removing device.", deviceManager.removeDevice(deviceId));
	}
	
	@Test
	public void testRemoveDeviceSecondTime(){
		deviceManager.addDevice(deviceId);
		assertTrue("Error while removing device.", deviceManager.removeDevice(deviceId));
		assertFalse("Error while removing device second time.", deviceManager.removeDevice(deviceId));
	}
	
	/*  Save device indication function tests  */
	@Test(expected=NullPointerException.class)
	public void testSaveDeviceIndicationWithNullParameter(){
		deviceManager.saveDeviceIndication(null, new Date().getTime(), "25");
	}
	
	@Test(expected=NullPointerException.class)
	public void testSaveDeviceIndicationWithNullParameter2(){
		deviceManager.saveDeviceIndication(deviceId, new Date().getTime(), null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testSaveDeviceIndicationWithTwoNullParameter(){
		deviceManager.saveDeviceIndication(null, new Date().getTime(), null);
	}

	@Test()
	public void testSaveDeviceIndication(){
		deviceManager.addDevice(deviceId);
		assertTrue("Error while saving device indication.", deviceManager.saveDeviceIndication(deviceId, new Date().getTime(), "25"));
	}
	
	@Test
	public void testSaveDeviceIndicationWithUnknowDeviceName(){
		deviceManager.addDevice(deviceId);
		assertFalse("Error while saving device indication for unknow device name.", deviceManager.saveDeviceIndication("unknownId", new Date().getTime(), "25"));
	}
	
	@Test()
	public void testSavedDeviceIndicationCount(){
		deviceManager.addDevice(deviceId);
		assertTrue("Error while saving device indication.", deviceManager.saveDeviceIndication(deviceId, new Date().getTime(), "25"));
		Date date = new Date();
		String value = "23,5";
		assertTrue("Error while saving device indication.", deviceManager.saveDeviceIndication(deviceId, date.getTime(), value));
		assertEquals("Error in saved indications count.", 2 , deviceManager.getDevice(deviceId).getIndications().size());
		assertEquals("Error in saved indications value.", value , deviceManager.getDevice(deviceId).getIndications().get(1).getValue());
		assertEquals("Error in saved indications timestamp.", date.getTime() , deviceManager.getDevice(deviceId).getIndications().get(1).getDate());
	}
	
	/*  Get device indication function tests  */
	@Test(expected=NullPointerException.class)
	public void testGetDeviceWithNullParameter(){
		deviceManager.getDevice(null);
	}
	
	@Test()
	public void testGetDeviceWithStrangeName(){
		deviceManager.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb");
		assertNotNull("Error while gettting device with strange name.", deviceManager.getDevice("#$DSWDNKASD*S asd as d8asbd 8asb"));
	}
	
	@Test()
	public void testGetDevice(){
		deviceManager.addDevice(deviceId);
		assertNotNull("Error while getting device.", deviceManager.getDevice(deviceId));
	}
	
	@Test()
	public void testGetDeviceWithUnknowId(){
		assertNull("Error while getting device with unknown Id.", deviceManager.getDevice("UnknownId"));
	}
	
	/*  Set device inactive function tests  */
	@Test(expected=NullPointerException.class)
	public void testSetDeviceInactiveWithNullParameter(){
		deviceManager.setDeviceInactive(null);
	}
	
	@Test()
	public void testSetDeviceInactiveWithStrangeName(){
		deviceManager.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb");
		assertTrue("Error while setting device inactive with strange name.", deviceManager.setDeviceInactive("#$DSWDNKASD*S asd as d8asbd 8asb"));
		assertFalse("Error while setting device inactive with strange name.", deviceManager.getDevice("#$DSWDNKASD*S asd as d8asbd 8asb").isActive());
	}
	
	@Test()
	public void testSetDeviceInactive(){
		deviceManager.addDevice(deviceId);
		assertTrue("Error while setting device inactive with strange name.", deviceManager.setDeviceInactive(deviceId));
		assertFalse("Error while setting device inactive with strange name.", deviceManager.getDevice(deviceId).isActive());
	}
	
	@Test()
	public void testSetDeviceInactiveWithUnknowId(){
		assertFalse("Error while setting device inactive  with unknown Id.", deviceManager.setDeviceInactive("UnknownId"));
	}
	
	@Test()
	public void testGetDevicesMap(){
		assertNotNull("Error retrieving devices map.", deviceManager.getDevicesMap());
	}
}
