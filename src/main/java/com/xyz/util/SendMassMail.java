package com.xyz.util;

import org.apache.log4j.Logger;

/**
 * 群发邮件
 * 
 * @author 叶灬黎
 *
 */
public final class SendMassMail implements Runnable {

	private Logger log = Logger.getLogger(SendMassMail.class);

	private String host;
	private String sender;
	private String nickname;
	private String sendpass;
	private String[] address;
	private String subject;
	private String content;

	private SendMassMail() {
		super();
	}

	public SendMassMail(String host, String sender, String nickname, String sendpass, String[] address, String subject,
			String content) {
		super();
		this.host = host;
		this.sender = sender;
		this.nickname = nickname;
		this.sendpass = sendpass;
		this.address = address;
		this.subject = subject;
		this.content = content;
	}

	@Override
	public void run() {
		boolean flag = false;
		int count = 0;
		try {
			while (!flag) {
				flag = Utils.sendMail(host, sender, nickname, sendpass, address, subject, content);
				// System.out.println(flag + " " + address[0]);
				if (!flag) {
					Thread.sleep(5000);
					count++;
					if (count == 3) {
						flag = true;
						if (!Ping.isReachIp("www.baidu.com")) {
							log.error("发送邮件给" + address[0] + "时本机网络出问题");
						} else {
							log.error("怀疑该邮箱：" + address[0] + "为无效邮箱");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// ,"2130974839@qq.com"
		// , "1204864523@qq.com"
		String[] receivers = { "2130974839@qq.com", "1204864523@qq.com" };

		String host = "smtp.qq.com";
		String sender = "981378964@qq.com";
		String nickname = "小叶子";
		String sendpass = "emzbcblhyghmbfga";
		String subject = "你好";
		String content = "很高兴认识你";

		for (String receiver : receivers) {
			String[] address = { receiver };
			Thread t = new Thread(new SendMassMail(host, sender, nickname, sendpass, address, subject, content));
			t.start();
		}
	}

}
