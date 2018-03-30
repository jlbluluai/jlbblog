package com.xyz.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

public final class DateUtils {

	private static Logger logger = Logger.getLogger(DateUtils.class);

	private static String errinf = "";

	public static String dateFormatCST(String date) throws ParseException {
		String dateFormat = "";

		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		Date d = sdf.parse(date);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(d);

		return dateFormat;
	}

	public static void main(String[] args) {
		String date = "Thu Nov 23 11:33:15 CST 2017";
		try {
			date = DateUtils.dateFormatCST(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date);
	}
}
