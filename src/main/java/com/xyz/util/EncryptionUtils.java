package com.xyz.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.xyz.common.CommonElement;

/**
 * 加密工具类
 * 
 * @author xiaoyezi
 *
 */
public class EncryptionUtils {

	private static Logger logger = Logger.getLogger(EncryptionUtils.class);

	/**
	 * 通过加盐的方式对密码进行md5加密
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String getMd5Password(String password) throws Exception {
		logger.info(CommonElement.separator + "对密码进行md5加密开始" + CommonElement.separator);
		logger.info("加密密码为：" + password);

		String md5Pass = "";

		String errinf = "";
		// 检查
		if ("".equals(password)) {
			errinf = "请确认输入密码";
			logger.error(errinf);
			throw new Exception(errinf);
		}

		MessageDigest digest = MessageDigest.getInstance("md5");// 得到一个信息摘要器
		byte[] result = digest.digest(password.getBytes());
		StringBuffer buffer = new StringBuffer();
		/* 把每一个byte 做一个与运算 0xff */
		for (byte b : result) {
			int number = b & 0xff;// 做与运算，加盐
			String str = Integer.toHexString(number);
			if (str.length() == 1) {
				buffer.append("0");
			}
			buffer.append(str);
		}
		md5Pass = buffer.toString();// 返回标准的md5加密后的结果

		logger.info("加密后的密码为：" + md5Pass);
		logger.info(CommonElement.separator + "对密码进行md5加密结束" + CommonElement.separator);

		return md5Pass;
	}
}
