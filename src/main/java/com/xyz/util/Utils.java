package com.xyz.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 主要的工具类
 * 
 * @author 叶灬黎
 *
 */
public final class Utils {

	private static Logger logger = Logger.getLogger(Utils.class);

	/**
	 * 判断字符串是否符合手机号码格式
	 * 
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
	 * 
	 * @author 叶灬黎
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
		return phone.matches(regex);
	}

	/**
	 * 判断字符串是否符合邮箱格式
	 * 
	 * @author 叶灬黎
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return email.matches(regex);
	}

	/**
	 * 判断一个字符串是否在一个字符串数组中
	 * 
	 * @author 叶灬黎
	 * @param str
	 * @param s
	 * @return
	 */
	public static boolean isThisStringGroup(String[] str, String s) {
		List<String> list = Arrays.asList(str);
		if (list.contains(s)) {
			return true;
		}
		return false;
	}

	private static String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * show 方法简介 生成6位验证码
	 * 
	 * @author 叶灬黎
	 * @return 6位验证码
	 */
	public static String getVerCode() {
		StringBuffer code = new StringBuffer();
		Random random = new Random();
		int temp;
		for (int i = 0; i < 6; i++) {
			if (random.nextInt(2) == 0) {
				temp = random.nextInt(10);
				code.append(temp);
			} else {
				temp = random.nextInt(26);
				code.append(alphabet[temp]);
			}
		}
		return code.toString();
	}

	/**
	 * show 方法简介 第三方调用邮箱（这里主要是qq邮箱）发送邮件给一方或多方，注意如果是qq邮箱必须开ssl加密
	 * 
	 * @author 叶灬黎
	 * @param host
	 *            主机名，例：qq邮箱的host，"smtp.qq.com"
	 * @param sender
	 *            发送人的邮箱
	 * @param nickname
	 *            发送人的昵称（随便取，别人收到邮件时看到的昵称）
	 * @param password
	 *            密码，除qq邮箱是要去生成的随机码，暂时其他邮箱都是邮箱登录密码
	 * @param receivers
	 *            接收人的邮箱，可以一个，可以多个，存放在一个字符串数组中传递
	 * @param subject
	 *            邮件主题（标题）
	 * @param content
	 *            邮件内容
	 */
	public static boolean sendMail(String host, String sender, String nickname, String password, String[] receivers,
			String subject, String content) throws Exception {
		Transport transport = null;
		try {
			Properties props = new Properties();

			// 开启debug调试
			// props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名
			props.setProperty("mail.host", host);
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");

			// 开启ssl加密，目前qq是要开的
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);

			Session session = Session.getInstance(props);

			// 定义邮件主题,内容,发件人
			Message msg = new MimeMessage(session);
			msg.setSubject(subject);
			StringBuilder builder = new StringBuilder();
			builder.append(content);
			msg.setText(builder.toString());
			msg.setFrom(new InternetAddress(sender, nickname, "UTF-8"));

			// 传输连接，并发送
			transport = session.getTransport();
			transport.connect(host, sender, password);
			Address[] address = new Address[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				address[i] = new InternetAddress(receivers[i]);
			}
			transport.sendMessage(msg, address);
		} catch (Exception e) {
			return false;
		} finally {
			transport.close();
		}
		return true;
	}

	/**
	 * 有当前时间戳加上1000000以内随机数构建基本不会重复的Long型变量，主要是用作复杂的主键生成
	 * 
	 * @author 叶灬黎
	 * @return
	 */
	public static Long createComplexId() {
		return Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "" + new Random().nextInt(1000000));
	}

}
