package com.xyz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public final class FtpConnect {

	private static Logger log = Logger.getLogger(FtpConnect.class);

	/**
	 * 将文件上传到ftp无服务器的指定位置
	 * 
	 * @param file
	 */
	public static void uploadOneFile(File file, String uploadPath) {
		log.info("开始文件上传至ftp");
		// 创建客户端对象
		FTPClient ftp = new FTPClient();
		InputStream local = null;

		// 获取ftp连接属性
		String prop = FtpConnect.class.getClassLoader().getResource("/").getPath() + "properties/ftp-connect.properties";
		prop = URLDecoder.decode(prop);
		Properties properties = Utils.getProperties(prop);

		try {
			// 连接ftp服务器
			ftp.connect(properties.getProperty("url"), 21);
			// 登录
			ftp.login(properties.getProperty("user"), properties.getProperty("pass"));
			// 设置上传路径
			String path = uploadPath;
			// 设置编码
			ftp.setControlEncoding("UTF-8");
			// 检查上传路径是否存在 如果不存在返回false
			boolean flag = ftp.changeWorkingDirectory(path);
			if (!flag) {
				// 创建上传的路径 该方法只能创建一级目录
				ftp.makeDirectory(path);
			}
			// 指定上传路径
			ftp.changeWorkingDirectory(path);
			// ftp.enterLocalActiveMode(); //主动模式
			ftp.enterLocalPassiveMode(); // 被动模式
			// 指定上传文件的类型 二进制文件
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 将流给local
			local = new FileInputStream(file);
			// 第一个参数是文件名
			ftp.storeFile(file.getName(), local);
			log.info("文件成功上传至ftp");
		} catch (Exception e) {
			log.error("ftp上传异常：" + e.getMessage());
		} finally {
			try {
				// 关闭文件流
				local.close();
				// 退出
				ftp.logout();
				// 断开连接
				ftp.disconnect();
			} catch (IOException e) {
				log.error("ftp连接断开异常：" + e.getMessage());
			}
		}
	}

}
