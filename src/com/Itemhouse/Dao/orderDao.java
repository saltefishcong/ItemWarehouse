package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Itemhouse.eity.order;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行订单类的数据库操作
 */
public class orderDao {
	/** 进行item_orders表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_orders表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于添加订单的操作
	 * 
	 * @param od
	 *            封装好的order对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码2001
	 */
	public int orderAdd(order od, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_orders (store_name,consumer_Identification,store_article_name,num,price,place,date,status) values "
						+ "('%s','%s','%s','%d','%f','%s','%d','%d')",
				od.getStore_name(), od.getConsumer_Identification(), od.getStore_article_name(), od.getNum(),
				od.getPrice(), od.getPlace(), od.getDate().getTime(), od.getStatus()); // 将Date类型转换long类型
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于删除订单的操作
	 * 
	 * @param order_id
	 *            订单的主键 id
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码2002
	 */
	public int orderDelete(int order_id, Connection con) throws SQLException {
		String sql = String.format("delete from item_orders where id='%s' ", order_id);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用户评价后将订单号状态改成2,代表订单完成
	 * 
	 * @param order_id
	 *            订单标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws SQLException
	 *             状态码2003
	 */
	public int orderUpdateStatus(int order_id, Connection con) throws SQLException {
		String sql = String.format("update item_orders set status=2 where id='%d' ", order_id);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询订单的操作 ,查询一个客户的所有订单
	 * 
	 * @param consumer_Identification
	 *            客户唯一标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.order&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2004
	 */
	public List<order> orderSelectConsumer(String consumer_Identification, Connection con) throws SQLException {
		List<order> list = new ArrayList<order>();
		String sql = String.format("select * from item_orders where consumer_Identification='%s' ",
				consumer_Identification);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date); // 将long类型转换成时间类型
			order od = new order(rs.getInt("id"), rs.getString("store_name"), consumer_Identification,
					rs.getString("store_article_name"), rs.getInt("num"), rs.getDouble("price"), rs.getString("place"),
					data, rs.getInt("status"));
			list.add(od);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询订单的操作 ,查询一个店铺的所有订单
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.order&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2005
	 */
	public List<order> orderSelectStore(String store_name, Connection con) throws SQLException {
		List<order> list = new ArrayList<order>();
		String sql = String.format("select * from item_orders where store_name='%s' ", store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date); // 将long类型转换成时间类型
			order od = new order(rs.getInt("id"), store_name, rs.getString("consumer_Identification"),
					rs.getString("store_article_name"), rs.getInt("num"), rs.getDouble("price"), rs.getString("place"),
					data, rs.getInt("status"));
			list.add(od);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询订单的操作 ,查询一个订单
	 * 
	 * @param order_id
	 *            订单标识
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.order 返回封装好的order对象
	 * @throws java.sql.SQLException
	 *             状态码2006
	 */
	public order orderSelectOne(int order_id, Connection con) throws SQLException {
		String sql = String.format("select * from item_orders where id='%d' ", order_id);
		order od = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date); // 将long类型转换成时间类型
			od = new order(order_id, rs.getString("store_name"), rs.getString("consumer_Identification"),
					rs.getString("store_article_name"), rs.getInt("num"), rs.getDouble("price"), rs.getString("place"),
					data, rs.getInt("status"));
		}
		rs.close();
		ps.close();
		return od;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺一个物品的所有订单
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.order&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码2007
	 */
	public List<order> orderSelectSeriesOrder(String store_name, String article_name, Connection con) throws Exception {
		String sql = String.format("select * from item_orders where store_name='%s' and store_article_name='%s' ",
				store_name, article_name);
		List<order> list = new ArrayList<order>();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date); // 将long类型转换成时间类型
			order od = new order(rs.getInt("id"), store_name, rs.getString("consumer_Identification"),
					rs.getString("store_article_name"), rs.getInt("num"), rs.getDouble("price"), rs.getString("place"),
					data, rs.getInt("status"));
			list.add(od);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查看客户一个物品的所有订单
	 * 
	 * @param consumer_Identification
	 *            用户标识
	 * @param article
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.order&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws SQLException
	 *             状态码2008
	 */
	public List<order> orderSelectConsumerSeriesList(String consumer_Identification, String article, Connection con)
			throws SQLException {
		List<order> list = new ArrayList<order>();
		String sql = String.format(
				"select * from item_orders where consumer_Identification='%s' and store_article_name='%s' ",
				consumer_Identification, article);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date); // 将long类型转换成时间类型
			order od = new order(rs.getInt("id"), rs.getString("store_name"), consumer_Identification,
					rs.getString("store_article_name"), rs.getInt("num"), rs.getDouble("price"), rs.getString("place"),
					data, rs.getInt("status"));
			list.add(od);
		}
		rs.close();
		ps.close();
		return list;
	}
}
