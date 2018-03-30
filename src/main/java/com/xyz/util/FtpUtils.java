package com.xyz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.xyz.common.CommonUtils;

public final class FtpUtils {

	private static Logger logger = Logger.getLogger(FtpUtils.class);

	private static String errinf = "";

	/**
	 * 将文件上传到ftp无服务器的指定位置
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void uploadOneFile(File file, String uploadPath) throws Exception {
		logger.info("开始文件上传至ftp");
		// 创建客户端对象
		FTPClient ftp = new FTPClient();
		InputStream local = null;

		// 获取ftp连接属性
		String prop = FtpUtils.class.getClassLoader().getResource("/").getPath() + "properties/ftp-connect.properties";
		prop = URLDecoder.decode(prop);
		Properties properties = CommonUtils.getProperties(prop);

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
			logger.info("文件成功上传至ftp");
		} catch (Exception e) {
			logger.error("ftp上传异常：" + e.getMessage());
		} finally {
			try {
				// 关闭文件流
				local.close();
				// 退出
				ftp.logout();
				// 断开连接
				ftp.disconnect();
			} catch (IOException e) {
				logger.error("ftp连接断开异常：" + e.getMessage());
			}
		}
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param ftpPath
	 *            FTP服务器中文件所在路径 格式： ftptest/aa
	 * 
	 * @param localPath
	 *            下载到本地的位置 格式：H:/download
	 * 
	 * @param fileName
	 *            文件名称
	 * @throws Exception
	 */
	public static void downloadOneFile(String ftpPath, String localPath, String fileName) throws Exception {
		FTPClient ftpClient = null;

		// 获取ftp连接属性
		String prop = FtpUtils.class.getClassLoader().getResource("/").getPath() + "properties/ftp-connect.properties";
		prop = URLDecoder.decode(prop);
		Properties properties = CommonUtils.getProperties(prop);

		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String pass = properties.getProperty("pass");

		try {
			ftpClient = getFTPClient(url, user, pass, 21);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);

			File localFile = new File(localPath + File.separatorChar + fileName);
			OutputStream os = new FileOutputStream(localFile);
			ftpClient.retrieveFile(fileName, os);
			os.close();
			ftpClient.logout();

		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			e.printStackTrace();
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
			e.printStackTrace();
		}

	}

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}

}
