package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Itemhouse.eity.transaction;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行交易类的数据库操作,系统自动操作
 */
public class transactionDao {
	/** 进行item_transaction表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_orders表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于添加交易的操作
	 * 
	 * @param tran
	 *            封装好的transaction对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码2601
	 */
	public int transactionAdd(transaction tran, Connection con) throws SQLException {
		String sql = String.format("insert into item_transaction (order_id,flag,note) values ('%d','%d','%s')",
				tran.getOrder_id(), tran.getFlag(), tran.getNote());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询交易的操作,查询所有交易纪录,只有系统管理员才能调用
	 * 
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.transaction&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.transaction
	 *         &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2602
	 */
	public List<transaction> transactionSelectAll(Connection con) throws SQLException {
		List<transaction> list = new ArrayList<transaction>();
		String sql = String.format("select * from item_transaction");
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			transaction tran = new transaction(rs.getInt("id"), rs.getInt("order_id"), rs.getInt("flag"),
					rs.getString("note"));
			list.add(tran);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询交易的操作,查询一个客户所有交易纪录,只有系统管理员才能调用
	 * 
	 * @param consumer_Identification
	 *            客户的唯一标记
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.transaction&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.transaction
	 *         &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2603
	 */
	public List<transaction> transactionSelectConsumer(String consumer_Identification, Connection con)
			throws SQLException {
		List<transaction> list = new ArrayList<transaction>();
		String sql = String.format(
				"select item_orders.consumer_Identification, item_transaction.*  from item_orders,item_transaction where item_orders.id=item_transaction.order_id and item_orders.consumer_Identification='%s' ",
				consumer_Identification);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			transaction tran = new transaction(rs.getInt("id"), rs.getInt("order_id"), rs.getInt("flag"),
					rs.getString("note"));
			list.add(tran);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询交易的操作,查询一个店铺所有交易纪录,只有系统管理员才能调用
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List&lt;com.Itemhouse.eity.transaction&gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.transaction
	 *         &gt;的容器中
	 * @throws java.sql.SQLException
	 *             状态码2604
	 */
	public List<transaction> transactionSelectStore(String store_name, Connection con) throws SQLException {
		List<transaction> list = new ArrayList<transaction>();
		String sql = String.format(
				"select item_orders.store_name, item_transaction.*  from item_orders,item_transaction where item_orders.id=item_transaction.order_id and item_orders.store_name='%s' ",
				store_name);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			transaction tran = new transaction(rs.getInt("id"), rs.getInt("order_id"), rs.getInt("flag"),
					rs.getString("note"));
			list.add(tran);
		}
		rs.close();
		ps.close();
		return list;
	}
}
