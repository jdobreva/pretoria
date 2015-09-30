package com.estafet.pretoria.test;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;
import com.estafet.pretoria.utils.DateTimeFormatter;

public class DateTimeFormatterTest {

	@Test
	public void testFormatter(){
		@SuppressWarnings("deprecation")
		Date date = new Date(2015-1900, 8 , 30, 15, 42, 55);
		
		String timeValue = "09/30/2015 15:42:55";
		assertEquals("Invalid dateFormatter return string", timeValue, DateTimeFormatter.format(date.getTime()));
	}
}
