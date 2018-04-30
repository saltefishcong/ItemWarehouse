package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.Itemhouse.eity.store;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行店铺类的数据库操作
 */
public class storeDao {
	/** 进行item_stores表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_stores表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的添加操作
	 * 
	 * @param str
	 *            封装好的store对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码101
	 */
	public int storeAdd(store str, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_stores (name,email,password,phone,status,date) values ('%s','%s','%s','%d','%d','%d')",
				str.getName(), str.getEmail(), str.getPassword(), str.getPhone(), str.getStatus(),
				str.getDate().getTime());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的删除操作,这个方法只能由系统调用。
	 * 
	 * @param store_name
	 *            要删除的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码102
	 */
	public int storeDelete(String store_name, Connection con) throws SQLException {
		String sql = String.format("delete from item_stores where name='%s' ", store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的更改操作,只能通过邮箱进行密码更改操作。因为没有电话接口发送电话信息,所以不能更改手机号,也不能利用手机号去更改邮箱。money,
	 * grade, cookie是由系统更改的,而店铺名字是标识,不能随意更改。
	 * 
	 * @param password
	 *            要更改的密码
	 * @param store_name
	 *            要更改的账号
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码103
	 */
	public int storeUpdated(String password, String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set password='%s' where name='%s' ", password, store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的更改操作,增加money。由系统在每次交易完成或者失败的时候更改
	 * 
	 * @param money
	 *            要更改的金钱
	 * @param store_name
	 *            要更改的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码104
	 */
	public int setStoreMoneyAdd(double money, String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set money=money+'%f' where name='%s' ", money, store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的更改操作,减少money。由系统在每次交易完成或者失败的时候更改
	 * 
	 * @param money
	 *            要更改的金钱
	 * @param store_name
	 *            要更改的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码105
	 */
	public int setStoreMoneyCut_Back(double money, String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set money=money-'%f' where name='%s' ", money, store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的更改操作,增加grade。由系统在每次交易完成或者失败的时候更改
	 * 
	 * @param store_name
	 *            要更改的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码106
	 */
	public int setStoreGradeAdd(String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set grade=grade+1 where name='%s' ", store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的更改操作,减少grade。由系统在每次交易完成或者失败的时候更改
	 * 
	 * @param store_name
	 *            要更改的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码107
	 */
	public int setStoreGradeCut_Back(String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set grade=grade-1 where name='%s' ", store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的更改操作,更改cookie。用于实现登陆长链接,这个方法只能由系统调用,用户无法知道自己的cookie。
	 * 
	 * @param store_cookie
	 *            要更改的cookie
	 * @param store_name
	 *            要更改的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码108
	 */
	public int setStoreCooike(String store_cookie, String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set cookie='%s' where name='%s' ", store_cookie, store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 注册成功设置金额和等级
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码109
	 */
	public int setStoreGradeAndMoney(String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set money=700,grade=1 where name='%s' ", store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 设置店铺的激活状态:0不激活,1激活
	 * 
	 * @param status
	 *            激活状态
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码110
	 */
	public int setStoreStatus(int status, String store_name, Connection con) throws SQLException {
		String sql = String.format("update item_stores set status='%d' where name='%s' ", status, store_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 设置店铺的消息,使用相同的用户名的话,在第一个用户注册时间过期后,第二个用户注册成功后直接覆盖第一个的资料
	 * 
	 * @param str
	 *            封装好的store对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码111
	 */
	public int setStore(store str, Connection con) throws SQLException {
		String sql = String.format(
				"update item_stores set email='%s',password='%s',phone='%d',date='%d' where name='%s' ", str.getEmail(),
				str.getPassword(), str.getPhone(), str.getDate().getTime(), str.getName());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的查询操作,查询数据库是否有相同的cookie存在。用于获取店铺的资料
	 * 
	 * @param store_cookie
	 *            要查询的cookie
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.store 返回数据库查询到的纪录,并封装到store类的实例属性中
	 * @exception java.sql.SQLException
	 *                状态码112
	 */
	public store storeSelectCookie(String store_cookie, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores where cookie='%s' ", store_cookie);
		store str = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			Date date = new Date(rs.getLong("date"));
			str = new store(rs.getString("name"), rs.getString("email"), rs.getInt("phone"), rs.getDouble("money"),
					rs.getInt("grade"), date);
		}
		rs.close();
		ps.close();
		return str;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询是否有相同的cookie,用于判断新产生的cookie是否可用
	 * 
	 * @param store_cookie
	 *            查询的cookie
	 * @param con
	 *            数据库Connection对象
	 * @return boolean true代表有相同的cookie,false代表没有
	 * @throws java.sql.SQLException
	 *             状态码113
	 */
	public boolean storeSelect_Cookie(String store_cookie, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores where cookie='%s' ", store_cookie);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		boolean flag = rs.next();
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的查询操作,判断登陆。每一次登陆成功都要更改数据库的cookie,用系统调用。
	 * 
	 * @param store_name
	 *            要查询的店铺名字
	 * @param password
	 *            要查询的密码
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.store 返回数据库查询到的纪录,并封装到store类的实例属性中
	 * @exception java.sql.SQLException
	 *                状态码114
	 */
	public store storeSelect(String store_name, String password, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores where name='%s' and password='%s' ", store_name,
				password);
		store str = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			Date date = new Date(rs.getLong("date"));
			str = new store(rs.getString("name"), rs.getString("email"), rs.getInt("phone"), rs.getDouble("money"),
					rs.getInt("grade"), date);
		}
		rs.close();
		ps.close();
		return str;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的查询操作,判断登陆。查询status状态码是否为激活状态。
	 * 
	 * @param store_name
	 *            要查询的店铺名字
	 * @param password
	 *            要查询的密码
	 * @param con
	 *            数据库Connection对象
	 * @return int -1代表没查询到纪录,0代表没激活,1代表激活
	 * @exception java.sql.SQLException
	 *                状态码115
	 */
	public int storeSelectFlag(String store_name, String password, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores where name='%s' and password='%s' ", store_name,
				password);
		int x = -1;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			x = rs.getInt("status");
		}
		rs.close();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺类的查询操作,查询数据库是否有相同的店铺名字存在。用于注册店铺
	 * 
	 * @param store_name
	 *            要查询的店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return boolean 返回数据库是否查询到纪录
	 * @exception java.sql.SQLException
	 *                状态码116
	 */
	public boolean storeSelectStore_name(String store_name, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores where name='%s'", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		boolean flag = rs.next();
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的激活状态
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return boolean 返回true代表激活,false代表没激活
	 * @throws java.sql.SQLException
	 *             状态码117
	 */
	public boolean storeSelectStore_status(String store_name, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores  where name='%s'", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			int x = rs.getInt("status");
			if (x == 1) {
				return true;
			}
		}
		rs.close();
		ps.close();
		return false;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 更改密码的时候查询输入的旧密码是否相等,防止有人上别人号
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param password
	 *            店铺密码
	 * @param con
	 *            数据库Connection对象
	 * @return boolean true代表正确,false代表不正确
	 * @throws SQLException
	 *             状态码118
	 */
	public boolean storeSelectPassword(String store_name, String password, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores  where name='%s'", store_name);
		String pass = "";
		boolean flag = false;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			pass = rs.getString("password");
			if (pass.equals(password)) {
				flag = true;
			}
		}
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的注册时间
	 * 
	 * @param date
	 *            传进去的时间
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return boolean
	 *         返回true代表第二个用户可以注册(邮件注册时间失效),返回false代表第二个用户不可以注册(邮件注册时间没失效)
	 * @throws java.sql.SQLException
	 *             状态码119
	 */
	public boolean storeSelectStore_date(Date date, String store_name, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores  where name='%s'", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long time = date.getTime();
			long email_time = rs.getLong("date");
			if (time - email_time > (1000 * 60 * 30)) {
				return true; // 时间超过2分钟,释放被占用的用户名
			}
		}
		return false; // 时间不超过2分钟,不能释放被占用的用户名
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的等级 ,用于设置店铺的等级,最大8级
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return int 返回店铺等级大小,返回-1代表异常,最大8级,最小0级
	 * @throws java.sql.SQLException
	 *             状态码120
	 */
	public int storeSelectStore_grade(String store_name, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores  where name='%s'", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		int grade = -1;
		while (rs.next()) {
			grade = rs.getInt("grade");
		}
		return grade;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的金额是否满足于生产的要求
	 * 
	 * @param store_name
	 *            店铺
	 * @param money
	 *            生产要用的金钱
	 * @param con
	 *            数据库Connection对象
	 * @return boolean true代表可以生产,false代表不能生产
	 * @throws SQLException
	 *             状态码121
	 */
	public boolean storeSelectStore_moey(String store_name, double money, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores  where name='%s'", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
			double store_money = rs.getDouble("money");
			if (store_money > money) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回商家的邮箱
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.String 返回店铺的邮箱
	 * @throws SQLException
	 *             状态码122
	 */
	public String storeSelectEmail(String store_name, Connection con) throws SQLException {
		String sql = String.format("select * from item_stores  where name='%s'", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		String email = "";
		while (rs.next()) {
			email = rs.getString("email");
		}
		rs.close();
		ps.close();
		return email;
	}
}
