package com.estafet.pretoria.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatter {

	private static final SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public static String format(long date) {
		return dateParser.format(new Date(date));
	}
}
