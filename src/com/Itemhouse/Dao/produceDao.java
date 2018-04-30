package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Itemhouse.eity.produce;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行生产类的数据库操作
 */
public class produceDao {
	/** 进行item_produce表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_produce表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于添加生产的操作
	 * 
	 * @param pro
	 *            封装好的produce对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码2301
	 */
	public int produceAdd(produce pro, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_produce (store_name,article,num,price,date) values ('%s','%s','%d','%f','%d')",
				pro.getStore_name(), pro.getArticle_name(), pro.getNum(), pro.getPrice(), pro.getDate().getTime());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询生产的操作,查询所有生产纪录,只有系统才能调用
	 * 
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.produce &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.produce &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2302
	 */
	public List<produce> produceSelectAll(Connection con) throws SQLException {
		List<produce> list = new ArrayList<produce>();
		String sql = String.format("select * from item_produce");
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			produce pro = new produce(rs.getInt("id"), rs.getString("store_name"), rs.getString("article"),
					rs.getInt("num"), rs.getDouble("price"), data);
			list.add(pro);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询生产的操作,查询一个店铺的所有生产纪录
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.produce &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.produce &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2303
	 */
	public List<produce> produceSelectStore(String store_name, Connection con) throws SQLException {
		List<produce> list = new ArrayList<produce>();
		String sql = String.format("select * from item_produce where store_name='%s' ", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			produce pro = new produce(rs.getInt("id"), rs.getString("store_name"), rs.getString("article"),
					rs.getInt("num"), rs.getDouble("price"), data);
			list.add(pro);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询生产的操作,查询一个店铺某个物品的生产纪录
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.produce &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.produce &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2304
	 */
	public List<produce> produceSelectStore_Article(String store_name, String article_name, Connection con)
			throws SQLException {
		List<produce> list = new ArrayList<produce>();
		String sql = String.format("select * from item_produce where store_name='%s' and article='%s' ", store_name,
				article_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			produce pro = new produce(rs.getInt("id"), rs.getString("store_name"), rs.getString("article"),
					rs.getInt("num"), rs.getDouble("price"), data);
			list.add(pro);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询生产的操作,查询某个物品的总生产纪录
	 * 
	 * @param article_name
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.produce &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.produce &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2305
	 */
	public List<produce> produceSelectArticleAll(String article_name, Connection con) throws SQLException {
		List<produce> list = new ArrayList<produce>();
		String sql = String.format("select * from item_produce where article='%s' ", article_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			produce pro = new produce(rs.getInt("id"), rs.getString("store_name"), rs.getString("article"),
					rs.getInt("num"), rs.getDouble("price"), data);
			list.add(pro);
		}
		rs.close();
		ps.close();
		return list;
	}
}
