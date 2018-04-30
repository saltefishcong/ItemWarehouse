package com.Itemhouse.Aid;

import java.sql.Connection;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          测试类
 */
public class test {

	static Connection con;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		randomNumber rondom = new randomNumber();
		String a = rondom.getConsumerIdentification();
		System.out.println(a);
	}

}
