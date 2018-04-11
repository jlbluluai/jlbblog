package com.xyz.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public static String getTheDiscrepantMonths(Date begin, Date end) {
		String discrepant = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String begin_str = sdf.format(begin);
		String end_str = sdf.format(end);

		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		try {
			bef.setTime(sdf.parse(begin_str));
			aft.setTime(sdf.parse(end_str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		int num = Math.abs(month + result);

		int years = num / 12;
		int months = num - 12 * years;

		discrepant = years + "年" + months + "月";

		return discrepant;
	}

	public static void main(String[] args) {
	}
}
