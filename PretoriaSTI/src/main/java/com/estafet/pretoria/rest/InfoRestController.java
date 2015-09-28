package com.estafet.pretoria.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoRestController {

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDeviceInfo", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
			"appication/json" })
	public @ResponseBody String getListDevices() {
		return "Hello!";
	}

}
