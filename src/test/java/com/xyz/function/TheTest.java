package com.xyz.function;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.xyz.util.SendMassMail;
import com.xyz.util.Utils;

public class TheTest {

	@Test
	public void testMail() {
		// ,"2130974839@qq.com"
		// , "1204864523@qq.com"
		String[] receivers = { "2130974839@qq.com" };

		try {
			for (String receiver : receivers) {
				String[] str = { receiver };
				boolean flag = Utils.sendMail("smtp.qq.com", "981378964@qq.com", "小叶子", "emzbcblhyghmbfga", str, "你好",
						"很高兴认识你");
				System.out.println(flag);
				if (!flag) {
					System.out.println(receiver);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
