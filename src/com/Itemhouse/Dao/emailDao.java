package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Itemhouse.eity.email;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行邮件类的数据库操作
 */
public class emailDao {
	/** 进行item_consumer表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_consumer表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于邮件类的添加操作
	 * 
	 * @param em
	 *            封装好的email对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码1701
	 */
	public int emailAdd(email em, Connection con) throws SQLException {
		String sql = String.format("insert into item_email (type,username,mark) values ('%s','%s','%s')", em.getType(),
				em.getUsername(), em.getMark());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 通过邮箱验证更改过期mark
	 * 
	 * @param em
	 *            email对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码1702
	 */
	public int emailUpdateMark(email em, Connection con) throws SQLException {
		String sql = String.format("update item_email set mark='%s' where username='%s' and type='%s' ", em.getMark(),
				em.getUsername(), em.getType());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于邮件类的查询操作
	 * 
	 * @param mark
	 *            注册的邮件标识
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.email 返回数据库查询到的纪录,并封装到email类实例的属性中
	 * @throws java.sql.SQLException
	 *             状态码1703
	 */
	public email emailSelect(String mark, Connection con) throws SQLException {
		String sql = String.format("select * from item_email where mark='%s' ", mark);
		email em = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			em = new email(rs.getString("type"), rs.getString("username"), rs.getString("mark"));
		}
		rs.close();
		ps.close();
		return em;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询mark返回的对应名字
	 * 
	 * @param mark
	 *            注册标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.String 返回数据库查询到的名字
	 * @throws java.sql.SQLException
	 *             状态码1704
	 */
	public String emailSelect_name(String mark, Connection con) throws SQLException {
		String sql = String.format("select * from item_email where mark='%s' ", mark);
		String name = "";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			name = rs.getString("username");
		}
		rs.close();
		ps.close();
		return name;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询是否有相同的mark
	 * 
	 * @param mark
	 *            要查询的mark
	 * @param con
	 *            数据库Connection对象
	 * @return boolean true代表有,false代表没有
	 * @throws java.sql.SQLException
	 *             状态码1705
	 */
	public boolean emailSelcet_mark(String mark, Connection con) throws SQLException {
		String sql = String.format("select * from item_email where mark='%s' ", mark);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		boolean flag = rs.next();
		rs.close();
		ps.close();
		return flag;
	}

}
