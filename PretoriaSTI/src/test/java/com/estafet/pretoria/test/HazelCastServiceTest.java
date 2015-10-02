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
import com.estafet.pretoria.storage.HazelcastService;
import com.hazelcast.core.HazelcastInstance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class HazelCastServiceTest {



	@Autowired
	@Qualifier(value="instance")
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private HazelcastService hazelcastService;
	
	private static final String deviceId = "device1";
	
	@Before
	public void clearDeviceData(){
		hazelcastInstance.getMap("map").clear();
	}
	
	/*  Add device function tests  */
	@Test(expected=NullPointerException.class)
	public void testAddNewDeviceWithNullParameter(){
		hazelcastService.addDevice(null);
	}
	
	@Test()
	public void testAddNewDeviceWithStrangeName(){
		assertTrue("Error while adding device.", hazelcastService.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb"));
	}
	
	@Test()
	public void testAddNewDevice(){
		assertTrue("Error while adding device.", hazelcastService.addDevice(deviceId));
	}
	
	@Test
	public void testAddNewDeviceSecondTime(){
		assertTrue("Error while adding device.", hazelcastService.addDevice(deviceId));
		assertFalse("Error while adding device second time.", hazelcastService.addDevice(deviceId));
	}
	
	/*  Remove device function tests  */
	@Test(expected=NullPointerException.class)
	public void testRemoveDeviceWithNullParameter(){
		hazelcastService.removeDevice(null);
	}
	
	@Test()
	public void testRemoveDeviceWithStrangeName(){
		hazelcastService.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb");
		assertTrue("Error while removing device with strange name.", hazelcastService.removeDevice("#$DSWDNKASD*S asd as d8asbd 8asb"));
	}
	
	@Test()
	public void testRemoveDevice(){
		hazelcastService.addDevice(deviceId);
		assertTrue("Error while removing device.", hazelcastService.removeDevice(deviceId));
	}
	
	@Test
	public void testRemoveDeviceSecondTime(){
		hazelcastService.addDevice(deviceId);
		assertTrue("Error while removing device.", hazelcastService.removeDevice(deviceId));
		assertFalse("Error while removing device second time.", hazelcastService.removeDevice(deviceId));
	}
	
	/*  Save device indication function tests  */
	@Test(expected=NullPointerException.class)
	public void testSaveDeviceIndicationWithNullParameter(){
		hazelcastService.saveDeviceIndication(deviceId, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testSaveDeviceIndicationWithNullParameter2(){
		hazelcastService.saveDeviceIndication(null, new IndicationModel(new Date().getTime(), "22,5"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testSaveDeviceIndicationWithTwoNullParameter(){
		hazelcastService.saveDeviceIndication(null, null);
	}

	@Test()
	public void testSaveDeviceIndication(){
		hazelcastService.addDevice(deviceId);
		assertTrue("Error while saving device indication.", hazelcastService.saveDeviceIndication(deviceId, new IndicationModel(new Date().getTime(), "22,5")));
	}
	
	@Test
	public void testSaveDeviceIndicationWithUnknowDeviceName(){
		hazelcastService.addDevice(deviceId);
		assertFalse("Error while saving device indication for unknow device name.", hazelcastService.saveDeviceIndication("unknownId", new IndicationModel(new Date().getTime(), "22,5")));
	}
	
	@Test()
	public void testSavedDeviceIndicationCount(){
		hazelcastService.addDevice(deviceId);
		assertTrue("Error while saving device indication.", hazelcastService.saveDeviceIndication(deviceId, new IndicationModel(new Date().getTime(), "22,5")));
		assertTrue("Error while saving device indication.", hazelcastService.saveDeviceIndication(deviceId, new IndicationModel(new Date().getTime(), "23,5")));
		assertEquals("Error in saved indications count.",2 , hazelcastService.getDevice(deviceId).getIndications().size() );
	}
	
	/*  Get device indication function tests  */
	@Test(expected=NullPointerException.class)
	public void testGetDeviceWithNullParameter(){
		hazelcastService.getDevice(null);
	}
	
	@Test()
	public void testGetDeviceWithStrangeName(){
		hazelcastService.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb");
		assertNotNull("Error while gettting device with strange name.", hazelcastService.getDevice("#$DSWDNKASD*S asd as d8asbd 8asb"));
	}
	
	@Test()
	public void testGetDevice(){
		hazelcastService.addDevice(deviceId);
		assertNotNull("Error while getting device.", hazelcastService.getDevice(deviceId));
	}
	
	@Test()
	public void testGetDeviceWithUnknowId(){
		assertNull("Error while getting device with unknown Id.", hazelcastService.getDevice("UnknownId"));
	}
	
	/*  Set device inactive function tests  */
	@Test(expected=NullPointerException.class)
	public void testSetDeviceInactiveWithNullParameter(){
		hazelcastService.setDeviceInactive(null);
	}
	
	@Test()
	public void testSetDeviceInactiveWithStrangeName(){
		hazelcastService.addDevice("#$DSWDNKASD*S asd as d8asbd 8asb");
		assertTrue("Error while setting device inactive with strange name.", hazelcastService.setDeviceInactive("#$DSWDNKASD*S asd as d8asbd 8asb"));
		assertFalse("Error while setting device inactive with strange name.", hazelcastService.getDevice("#$DSWDNKASD*S asd as d8asbd 8asb").isActive());
	}
	
	@Test()
	public void testSetDeviceInactive(){
		hazelcastService.addDevice(deviceId);
		assertTrue("Error while setting device inactive with strange name.", hazelcastService.setDeviceInactive(deviceId));
		assertFalse("Error while setting device inactive with strange name.", hazelcastService.getDevice(deviceId).isActive());
	}
	
	@Test()
	public void testSetDeviceInactiveWithUnknowId(){
		assertFalse("Error while setting device inactive  with unknown Id.", hazelcastService.setDeviceInactive("UnknownId"));
	}
	
	@Test()
	public void testGetDevicesMap(){
		assertNotNull("Error retrieving devices map.", hazelcastService.getDevicesMap());
	}
	
}
