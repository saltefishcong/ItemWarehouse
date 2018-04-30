package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Itemhouse.eity.consumer;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行客户类的数据库操作
 */
public class consumerDao {
	/** 进行item_consumer表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_consumer表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户的添加操作
	 * 
	 * @param conser
	 *            封装好的consumer对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码401
	 */
	public int consumerAdd(consumer conser, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_consumer (name,password,Identification,email,status) values ('%s','%s','%s','%s','%d')",
				conser.getName(), conser.getPassword(), conser.getIdentification(), conser.getEmail(), 0);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户的删除操作,这个方法只能由系统调用。
	 * 
	 * @param Identification
	 *            客户的唯一标识,随机生成。
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码402
	 */
	public int consumerDelete(String Identification, Connection con) throws SQLException {
		String sql = String.format("delete from item_consumer where Identification='%s' ", Identification);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户的更改操作,客户只能更改账号密码
	 * 
	 * @param conser
	 *            封装好的consumer类对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码403
	 */
	public int consumerUpdate(consumer conser, Connection con) throws SQLException {
		String sql = String.format("update item_consumer set name='%s',password='%s' where Identification='%s' ",
				conser.getName(), conser.getPassword(), conser.getIdentification());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户cookie的更改操作,用于登陆长链接,这个方法只能由系统调用,用户无法知道自己的cookie。
	 * 
	 * @param consumer_cookie
	 *            客户的cookie
	 * @param Identification
	 *            客户唯一标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码404
	 */
	public int consumerUpdateCookie(String consumer_cookie, String Identification, Connection con) throws SQLException {
		String sql = String.format("update item_consumer set cookie='%s' where Identification='%s' ", consumer_cookie,
				Identification);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 注册成功设置status
	 * 
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws SQLException
	 *             状态码405
	 */
	public int consumerUpdateStatus(String Identification, Connection con) throws SQLException {
		String sql = String.format("update item_consumer set status=1 where Identification='%s' ", Identification);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户类的查询操作,查询数据库是否有相同的cookie存在。用于获取客户的资料
	 * 
	 * @param consumer_cookie
	 *            客户的cookie
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.consumer 返回数据库查询到的纪录,并封装到consumer类的实例属性中
	 * @throws java.sql.SQLException
	 *             状态码406
	 */
	public consumer consumerSelectCookie(String consumer_cookie, Connection con) throws SQLException {
		String sql = String.format("select * from item_consumer where cookie='%s' ", consumer_cookie);
		consumer conser = null;
		ps = con.prepareCall(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			conser = new consumer(rs.getString("name"), rs.getString("Identification"));
		}
		rs.close();
		ps.close();
		return conser;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 判断是否有相同的cookie
	 * 
	 * @param consumer_cookie
	 *            设置的cookie
	 * @param con
	 *            数据库Connection对象
	 * @return true代表有相同的cookie,false代表没有
	 * @throws SQLException
	 *             状态码407
	 */
	public boolean consumerSelectCookieFlag(String consumer_cookie, Connection con) throws SQLException {
		String sql = String.format("select * from item_consumer where cookie='%s' ", consumer_cookie);
		ps = con.prepareCall(sql);
		rs = ps.executeQuery();
		boolean flag = rs.next();
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户类的登陆查询操作,判断登陆。每一次登陆成功都要更改数据库的cookie。
	 * 
	 * @param Identification
	 *            客户标识
	 * @param password
	 *            客户密码
	 * @param con
	 *            数据库Connection对象
	 * @return consumer 返会封装好的consumer对象
	 * @throws java.sql.SQLException
	 *             状态码408
	 */
	public consumer consumerSelect(String Identification, String password, Connection con) throws SQLException {
		String sql = String.format("select * from item_consumer where Identification='%s' and password='%s' ",
				Identification, password);
		consumer coumer = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			coumer = new consumer(rs.getString("name"), rs.getString("Identification"));
		}
		rs.close();
		ps.close();
		return coumer;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询用户的状态码
	 * 
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @return 1代表激活,0代表没激活
	 * @throws SQLException
	 *             状态码409
	 */
	public int consumerSelect_status(String Identification, Connection con) throws SQLException {
		String sql = String.format("select * from item_consumer where Identification='%s'", Identification);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		int x = 0;
		while (rs.next()) {
			x = rs.getInt("status");
		}
		rs.close();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询是否有相同的 identification
	 * 
	 * @param Identification
	 *            产生的identification
	 * @param con
	 *            数据库Connection对象
	 * @return true代表有相同的,false代表没有
	 * @throws SQLException
	 *             状态码410
	 */
	public boolean consumerSelect_identification(String Identification, Connection con) throws SQLException {
		String sql = String.format("select * from item_consumer where Identification='%s'", Identification);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		boolean flag = rs.next();
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 更改信息时判断输入的密码是否和数据库的相等
	 * 
	 * @param Identification
	 *            用户标识
	 * @param password
	 *            原来的密码
	 * @param con
	 *            数据库Connection对象
	 * @return true代表密码相等,false代表密码不想等
	 * @throws SQLException
	 *             状态码411
	 */
	public boolean consumerSelect_password(String Identification, String password, Connection con) throws SQLException {
		String sql = String.format("select * from item_consumer where Identification='%s'", Identification);
		boolean flag = false;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String pass = rs.getString("password");
			if (password.equals(pass)) {
				flag = true;
			}
		}
		return flag;
	}
}
