package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Itemhouse.eity.buycar;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行购物车类的数据库操作
 */
public class buycarDao {
	/** 进行item_consumer表的非查找数据库操作 */
	private PreparedStatement ps;
	/** 查询item_consumer表返回的结果集 */
	private ResultSet rs;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 添加购物车
	 * 
	 * @param car
	 *            封装好的buycar对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws SQLException
	 *             状态码1001
	 */
	public int buycarAdd(buycar car, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_buycar (Identification,store_name,article,money,num) values ('%s','%s','%s','%f','%d')",
				car.getIdentification(), car.getStore_name(), car.getArticle(), car.getMoney(), car.getNum());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 删除购物车
	 * 
	 * @param buycar_id
	 *            购物车id
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws SQLException
	 *             状态码1002
	 */
	public int buycarDelete(int buycar_id, Connection con) throws SQLException {
		String sql = String.format("delete from item_buycar where id='%d' ", buycar_id);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 物品二次加入购物车不添加纪录,直接更改
	 * 
	 * @param Identification
	 *            用户标识
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param num
	 *            物品数量
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws SQLException
	 *             状态码1003
	 */
	public int buycarUpdateNumAdd(String Identification, String store_name, String article_name, int num,
			Connection con) throws SQLException {
		String sql = String.format(
				"update item_buycar set num=num+'%d' where Identification='%s' and store_name='%s' and article='%s' ",
				num, Identification, store_name, article_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 进行购物车物品的减少
	 * 
	 * @param car_id
	 *            购物车纪录标识
	 * @param num
	 *            减少的数量
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws SQLException
	 *             状态码1004
	 */
	public int buycarUpdateNumCutBack(int car_id, int num, Connection con) throws SQLException {
		String sql = String.format("update item_buycar set num=num-'%d' where id='%d' ", num, car_id);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 客户购物车查询
	 * 
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.buycar &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.buycar &gt;的容器中
	 * @throws SQLException
	 *             状态码1005
	 */
	public List<buycar> buycarSelectConsumer(String Identification, Connection con) throws SQLException {
		List<buycar> list = new ArrayList<buycar>();
		String sql = String.format("select * from item_buycar where Identification='%s' ", Identification);
		ps = con.prepareCall(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			buycar car = new buycar(rs.getInt("id"), rs.getString("Identification"), rs.getString("store_name"),
					rs.getString("article"), rs.getDouble("money"), rs.getInt("num"));
			list.add(car);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 判断物品在加入购物车的时候是否已经超过物品在线数量
	 * 
	 * @param Identification
	 *            用户标识
	 * @param store_name
	 *            店铺名称
	 * @param article_name
	 *            物品名称
	 * @param buy_num
	 *            购买数量
	 * @param article_num
	 *            物品在线数量
	 * @param con
	 *            数据库Connection对象
	 * @return true代表可以加入购物车,false代表不可以加入购物车
	 * @throws SQLException
	 *             状态码1006
	 */
	public boolean buycarSelectArticleNum(String Identification, String store_name, String article_name, int buy_num,
			int article_num, Connection con) throws SQLException {
		String sql = String.format(
				"select * from item_buycar where store_name='%s' and article='%s' and Identification='%s' ", store_name,
				article_name, Identification);
		ps = con.prepareCall(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			int num = rs.getInt("num");
			if (num + buy_num <= article_num) {
				return true;
			}
		}
		rs.close();
		ps.close();
		return false;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询物品是否已经被客户加入过购物车
	 * 
	 * @param Identification
	 *            客户标识
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return true代表物品已经被客户加入过购物车,false代表没有被加入过购物车
	 * @throws SQLException
	 *             状态码1007
	 */
	public boolean buycarSelectArticle(String Identification, String store_name, String article_name, Connection con)
			throws SQLException {
		String sql = String.format(
				"select * from item_buycar where store_name='%s' and article='%s' and Identification='%s' ", store_name,
				article_name, Identification);
		ps = con.prepareCall(sql);
		rs = ps.executeQuery();
		boolean flag = rs.next();
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询指定的购物车纪录
	 * 
	 * @param Identification
	 *            客户标识
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.buycar 返回封装好的buycar对象
	 * @throws SQLException
	 *             状态码1008
	 */
	public buycar buycarSelectOne(String Identification, String store_name, String article_name, Connection con)
			throws SQLException {
		String sql = String.format(
				"select * from item_buycar where store_name='%s' and article='%s' and Identification='%s' ", store_name,
				article_name, Identification);
		ps = con.prepareCall(sql);
		rs = ps.executeQuery();
		buycar car = null;
		while (rs.next()) {
			car = new buycar(rs.getInt("id"), rs.getString("identification"), rs.getString("store_name"),
					rs.getString("article"), rs.getDouble("money"), rs.getInt("num"));
		}
		rs.close();
		ps.close();
		return car;
	}
}
