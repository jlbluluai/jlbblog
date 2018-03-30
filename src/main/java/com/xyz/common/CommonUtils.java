package com.xyz.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class CommonUtils {

	private static Logger logger = Logger.getLogger(CommonUtils.class);

	private static String errinf = "";

	/* String系列 */
	/**
	 * 去首尾
	 * 
	 * @param str
	 * @return
	 */
	public static String cutForeArt(String str) {
		str = str.substring(1, str.length() - 1);
		return str;
	}

	/**
	 * 从字符串指定位置开始获取指定长度字符串，开始必须大于0，若超过最大长度，默认取最后一个字符，长度必须大于0
	 * 
	 * @param str
	 * @param start
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String getAppointedStr(String str, int start, int length) throws Exception {
		logger.info(CommonElement.separator + "获取指定字符串开始" + CommonElement.separator);
		logger.info(str);

		int size = str.length();
		// 检查
		if (start <= 0) {
			errinf = "开始位置必须大于0";
			logger.error(errinf);
			throw new Exception(errinf);
		}
		if (length <= 0) {
			errinf = "长度必须大于0";
			logger.error(errinf);
			throw new Exception(errinf);
		}
		if (start > size) {
			start = size;
		}
		int residueSize = size - start;// 剩余长度
		if (length > residueSize) {
			length = start + residueSize;
		} else {
			length = start + length - 1;
		}
		str = str.substring(start - 1, length);

		logger.info(str);
		logger.info(CommonElement.separator + "获取指定字符串结束" + CommonElement.separator);
		return str;
	}

	/* 读取系列 */
	/**
	 * 读取属性文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Properties getProperties(String file) throws Exception {
		logger.info(CommonElement.separator + "读取属性文件开始" + CommonElement.separator);

		if ("".equals(file)) {
			errinf = "请输入属性文件路径";
			logger.error(errinf);
			throw new Exception(errinf);
		}

		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(new File(file));
		properties.load(fis);
		fis.close();

		logger.info(CommonElement.separator + "读取属性文件结束" + CommonElement.separator);
		return properties;
	}

	public static void main(String[] args) {
		String str = "12345";
		try {
			str = CommonUtils.getAppointedStr(str, 1, 14);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
	}

}
