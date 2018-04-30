package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Itemhouse.eity.comment;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行评论类的数据库操作
 */
public class commentDao {
	/** 进行item_comment表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_comment表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于添加评论的操作
	 * 
	 * @param com
	 *            封装好的comment对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码1301
	 */
	public int commentAdd(comment com, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_comment (order_id,consumer_Identification,satisfaction,comment_text,date) values ('%d','%s','%d','%s','%d') ",
				com.getOrder_id(), com.getConsumer_Identification(), com.getSatisfaction(), com.getComment_text(),
				com.getDate().getTime());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询评论的操作,查询一个客户的所有评论
	 * 
	 * @param consumer_Identification
	 *            客户的唯一标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.comment&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.comment &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码1302
	 */
	public List<comment> commentSelectConsumer(String consumer_Identification, Connection con) throws SQLException {
		List<comment> list = new ArrayList<comment>();
		String sql = String.format(
				"select item_orders.consumer_Identification, item_comment.*  from item_orders,item_comment where item_orders.id=item_comment.order_id and item_orders.consumer_Identification='%s' ",
				consumer_Identification);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			comment com = new comment(rs.getInt("order_id"), consumer_Identification, rs.getInt("satisfaction"),
					rs.getString("comment_text"), data);
			list.add(com);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询评论的操作,查询一个店铺的所有评论
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.comment&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.comment &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码1303
	 */
	public List<comment> commentSelectStore(String store_name, Connection con) throws SQLException {
		List<comment> list = new ArrayList<comment>();
		String sql = String.format(
				"select item_orders.consumer_Identification, item_comment.*  from item_orders,item_comment where item_orders.id=item_comment.order_id and item_orders.store_name='%s' ",
				store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			comment com = new comment(rs.getInt("order_id"), rs.getString("consumer_Identification"),
					rs.getInt("satisfaction"), rs.getString("comment_text"), data);
			list.add(com);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查看店铺某个物品的所有评论
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.comment&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.comment &gt;的容器中
	 * @throws SQLException
	 *             状态码1304
	 */
	public List<comment> commentSelectStore(String store_name, String article, Connection con) throws SQLException {
		List<comment> list = new ArrayList<comment>();
		String sql = String.format(
				"select item_orders.consumer_Identification, item_comment.*  from item_orders,item_comment where item_orders.id=item_comment.order_id and item_orders.store_name='%s' and item_orders.store_article_name='%s' ",
				store_name, article);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			comment com = new comment(rs.getInt("order_id"), rs.getString("consumer_Identification"),
					rs.getInt("satisfaction"), rs.getString("comment_text"), data);
			list.add(com);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询评论的操作,查询一个评论
	 * 
	 * @param order_id
	 *            订单标识
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.comment 返回封装好的comment对象
	 * @throws java.sql.SQLException
	 *             状态码1305
	 */
	public comment commentSelectOne(int order_id, Connection con) throws SQLException {
		String sql = String.format("select * from item_comment where order_id='%d' ", order_id);
		comment com = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			com = new comment(order_id, rs.getString("consumer_Identification"), rs.getInt("satisfaction"),
					rs.getString("comment_text"), data);
		}
		rs.close();
		ps.close();
		return com;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询客户某个物品的订单纪录
	 * 
	 * @param consumer_Identification
	 *            客户标识
	 * @param article
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.comment&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.comment &gt;的容器中
	 * @throws SQLException
	 *             状态码1306
	 */
	public List<comment> commentSelectConsumer(String consumer_Identification, String article, Connection con)
			throws SQLException {
		List<comment> list = new ArrayList<comment>();
		String sql = String.format(
				"select item_orders.consumer_Identification,item_orders.store_article_name, item_comment.*  from item_orders,item_comment where item_orders.id=item_comment.order_id and item_orders.consumer_Identification='%s' and item_orders.store_article_name='%s' ",
				consumer_Identification, article);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			long date = rs.getLong("date");
			Date data = new Date(date);
			comment com = new comment(rs.getInt("order_id"), consumer_Identification, rs.getInt("satisfaction"),
					rs.getString("comment_text"), data);
			list.add(com);
		}
		rs.close();
		ps.close();
		return list;
	}
}
