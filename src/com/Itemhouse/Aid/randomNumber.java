package com.Itemhouse.Aid;

import java.util.Date;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;为其它类提供随机的唯一标识
 */
public class randomNumber {

	/** 随机产生的字符 */
	private String random = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!@#$.";

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生成用户的唯一标识
	 * 
	 * @return String 返回用户的唯一标识
	 */
	public String getConsumerIdentification() {
		String Identification = "";
		for (int o = 0; o < 15; o++) {
			int i = (int) (Math.random() * 66);
			Identification += random.charAt(i);
		}
		System.out.println(Identification);
		return Identification;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生成注册的mark标识
	 * 
	 * @return 返回注册的mark标识
	 */
	public String getMark() {
		String mark = "";
		for (int o = 0; o < 15; o++) {
			int i = (int) (Math.random() * 66);
			mark += random.charAt(i);
		}
		return mark;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生成店铺长期登陆状态的cookie
	 * 
	 * @param store_cookie
	 *            传进去被转换好的cookie
	 * @return 返回商家的cookie
	 */
	public String getStoreCookie(String store_cookie) {
		long time = new Date().getTime();
		String cookie = "";
		cookie += time + "^";
		cookie += store_cookie;
		return cookie;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生成客户长期登陆状态的cookie
	 * 
	 * @param consumer_cookie
	 *            传进去被转换好的cookie
	 * @return 返回客户的cookie
	 */
	public String getConsumerCookie(String consumer_cookie) {
		long time = new Date().getTime();
		String cookie = "";
		cookie += time + "^";
		cookie += consumer_cookie;
		return cookie;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生成cookie
	 * 
	 * @return 返回生成的cookie
	 */
	public String getCookie() {
		String cookie = "";
		for (int o = 0; o < 10; o++) {
			int i = (int) (Math.random() * 66);
			cookie += random.charAt(i);
		}
		return cookie;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生成邮箱注册地址
	 * 
	 * @param mark
	 *            传进来的邮箱标识
	 * @param type
	 *            表示是店铺的邮箱注册还是客户的有邮箱注册
	 * @return 返回发送的邮箱注册地址
	 */
	public String getEmailMark(String mark, String type) {
		String markinfo = "http://10.0.19.12:8080/ItemWarehouse/servlet/registerServlet?action=youxiang&&type=";
		markinfo += type;
		markinfo += "&&mark=";
		markinfo += mark;
		return markinfo;
	}
}
