package com.xyz.util;

import java.net.InetAddress;

import org.apache.log4j.Logger;

/**
 * 测试本机网络情况
 * 
 * @author 叶灬黎
 *
 */
public final class Ping {
	private static Logger log = Logger.getLogger(Ping.class);

	/**
	 * 测试本地能否ping ip
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isReachIp(String ip) {
		boolean isReach = false;
		try {
			InetAddress address = InetAddress.getByName(ip);// ping this IP

			if (address instanceof java.net.Inet4Address) {
				log.info(ip + " is ipv4 address");
			} else if (address instanceof java.net.Inet6Address) {
				log.info(ip + " is ipv6 address");
			} else {
				log.info(ip + " is unrecongized");
			}
			if (address.isReachable(5000)) {
				isReach = true;
				log.info("SUCCESS - ping " + ip + " with no interface specified");
			} else {
				isReach = false;
				log.info("FAILURE - ping " + ip + " with no interface specified");
			}
		} catch (Exception e) {
			log.error("error occurs:" + e.getMessage());
		}
		return isReach;
	}

	public static void main(String[] args) {
		Ping ping = new Ping();
		System.out.println(ping.isReachIp("www.baidu.com"));
	}
}
