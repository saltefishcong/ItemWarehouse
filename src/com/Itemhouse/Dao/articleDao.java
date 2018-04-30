package com.Itemhouse.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Itemhouse.eity.article;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行店铺物品类的数据库操作
 */
public class articleDao {
	/** 查询item_article表返回的结果集 */
	private ResultSet rs;
	/** 进行item_article表的非查找数据库操作 */
	private PreparedStatement ps;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于添加店铺的物品操作
	 * 
	 * @param alt
	 *            封装好的article对象
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码701
	 */
	public int articleAdd(article alt, Connection con) throws SQLException {
		String sql = String.format(
				"insert into item_article (store_name,article,money,num) values ('%s','%s','%f','%d')",
				alt.getStore_name(), alt.getArticle(), alt.getMoney(), alt.getNum());
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于删除店铺的物品操作
	 * 
	 * @param article_id
	 *            店铺物品的主键,自增类型
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码702
	 */
	public int articleDelete(int article_id, Connection con) throws SQLException {
		String sql = "delete from item_article where id=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, article_id);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于更改店铺的物品操作
	 * 
	 * @param article_id
	 *            店铺物品的主键,自增类型
	 * @param article
	 *            更改的物品名字
	 * @param money
	 *            更改的物品价格
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @exception java.sql.SQLException
	 *                状态码703
	 */
	public int articleUpdate(int article_id, String article, double money, Connection con) throws SQLException {
		String sql = "update item_article set article=?,money=? where id=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, article);
		ps.setDouble(2, money);
		ps.setInt(3, article_id);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 添加店铺物品的数量,由系统调用
	 * 
	 * @param atricle_name
	 *            物品名称
	 * @param store_name
	 *            店铺名称
	 * @param num
	 *            要添加的物品数量
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码704
	 */
	public int articleUpdateNumAdd(String atricle_name, String store_name, int num, Connection con)
			throws SQLException {
		String sql = String.format("update item_article set num=num+'%d' where store_name='%s' and article='%s' ", num,
				store_name, atricle_name);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 减少店铺物品的数量,由系统调用
	 * 
	 * @param article_id
	 *            article标识
	 * @param num
	 *            要添加的物品数量
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws java.sql.SQLException
	 *             状态码705
	 */
	public int articleUpdateNumCutBack(int article_id, int num, Connection con) throws SQLException {
		String sql = String.format("update item_article set num=num-'%d' where id='%d' ", num, article_id);
		ps = con.prepareStatement(sql);
		int x = ps.executeUpdate();
		ps.close();
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询店铺的物品操作(查询店铺的某一个物品)
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return com.Itemhouse.eity.article 返回数据库查询到的纪录,并封装到article类实例的属性中
	 * @exception java.sql.SQLException
	 *                状态码706
	 */
	public article articleSelectOne(String store_name, String article_name, Connection con) throws SQLException {
		String sql = String.format("select * from item_article where store_name='%s' and article='%s' ", store_name,
				article_name);
		article art = null;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			art = new article(rs.getInt("id"), store_name, article_name, rs.getDouble("money"), rs.getInt("num"));
			// 一定要在while里面将对象初始化,不然会报一个ResultSet结果集被关闭的错误
		}
		rs.close();
		ps.close();
		return art;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询店铺的物品操作(查询某个店铺的所有物品)
	 * 
	 * @param store_name
	 *            店铺的名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.article &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.article &gt;
	 *         的容器中
	 * @exception java.sql.SQLException
	 *                状态码707
	 */
	public List<article> articleSelecStore_nametAll(String store_name, Connection con) throws SQLException {
		List<article> list = new ArrayList<article>();
		article art;
		String sql = "select * from item_article where store_name=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, store_name);
		rs = ps.executeQuery();
		while (rs.next()) {
			art = new article(rs.getInt("id"), store_name, rs.getString("article"), rs.getDouble("money"),
					rs.getInt("num")); // 不拿store_name的原因是,因为已经知道store_name才能去查询
			list.add(art);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询店铺的物品操作(查询所有店铺的同一个物品)
	 * 
	 * @param article
	 *            物品的名字
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.article &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.article &gt;的容器中
	 * @exception java.sql.SQLException
	 *                状态码708
	 */
	public List<article> articleSelectAll(String article, Connection con) throws SQLException {
		List<article> list = new ArrayList<article>();
		article art;
		String sql = "select * from item_article where article=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, article);
		rs = ps.executeQuery();
		while (rs.next()) {
			art = new article(rs.getInt("id"), rs.getString("store_name"), article, rs.getDouble("money"),
					rs.getInt("num")); // 不拿article的原因是,因为已经知道article才能去查询
			list.add(art);
		}
		rs.close();
		ps.close();
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询订单的时候返回物品名称
	 * 
	 * @param articleid
	 *            articleid标识
	 * @param con
	 *            数据库Connection对象
	 * @return java.lang.String 物品名字
	 * @throws Exception
	 *             状态码709
	 */
	public String articleSelectarticle_name(int articleid, Connection con) throws Exception {
		String sql = String.format("select * from item_article  where id='%d'", articleid);
		String article_name = "";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			article_name = rs.getString("article");
		}
		rs.close();
		ps.close();
		return article_name;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询购买数量和库存之间相差的数量
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param buy_num
	 *            购买数量
	 * @param con
	 *            数据库Connection对象
	 * @return 相差的数量,默认为-1,-1代表没有查询到结果
	 * @throws SQLException
	 *             状态码710
	 */
	public int articleSelectDifferenceNum(String store_name, String article_name, int buy_num, Connection con)
			throws SQLException {
		String sql = String.format("select * from item_article  where store_name='%s' and article='%s' ", store_name,
				article_name);
		int difference_num = -1;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			int num = rs.getInt("num");
			if (buy_num > num) {
				difference_num = buy_num - num;
			} else {
				difference_num = num - buy_num;
			}
		}
		rs.close();
		ps.close();
		return difference_num;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 判断购买的数量是否超过库存
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param buy_num
	 *            购买的数量
	 * @param con
	 *            数据库Connection对象
	 * @return true代表购买数量小于或者等于库存,false代表大于库存
	 * @throws SQLException
	 *             状态码711
	 */
	public boolean articleSelectNumFlag(String store_name, String article_name, int buy_num, Connection con)
			throws SQLException {
		String sql = String.format("select * from item_article  where store_name='%s' and article='%s' ", store_name,
				article_name);
		boolean flag = false;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			int num = rs.getInt("num");
			if (num >= buy_num) {
				flag = true;
			} else {
				flag = false;
			}
		}
		rs.close();
		ps.close();
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回物品生产的价格
	 * 
	 * @param num
	 *            物品的数量
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return double 返回的生产价格
	 * @throws SQLException
	 *             状态码712
	 */
	public double articleSelectarticle_money(int num, String store_name, String article_name, Connection con)
			throws SQLException {
		String sql = String.format("select * from item_article  where store_name='%s' and article='%s' ", store_name,
				article_name);
		double produceMoney = 0;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			double money = rs.getDouble("money");
			produceMoney = (money / 2) * num;
		}
		rs.close();
		ps.close();
		return produceMoney;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回生产需要的价格数组
	 * 
	 * @param num
	 *            物品数量
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @return double[] 返回的生产价格
	 * @throws SQLException
	 *             状态码713
	 */
	public double[] articleSelectarticle_moneys(int num[], String store_name, String article_name[], Connection con)
			throws SQLException {
		double[] produceMoney = new double[article_name.length];
		for (int i = 0; i < article_name.length; i++) {
			produceMoney[i] = articleSelectarticle_money(num[i], store_name, article_name[i], con);
		}
		return produceMoney;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回所有店铺的所有物品
	 * 
	 * @param con
	 *            数据库Connection对象
	 * @return java.util.List &lt; com.Itemhouse.eity.article &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.article &gt;的容器中
	 * @throws SQLException
	 *             状态码714
	 */
	public List<article> articleSelect(Connection con) throws SQLException {
		List<article> list = new ArrayList<article>();
		String sql = String.format("select * from item_article");
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			article atr = new article(rs.getInt("id"), rs.getString("store_name"), rs.getString("article"),
					rs.getDouble("money"), rs.getInt("num"));
			list.add(atr);
		}
		rs.close();
		ps.close();
		return list;
	}
}
