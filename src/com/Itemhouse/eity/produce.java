package com.Itemhouse.eity;

import java.util.Date;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;生产类
 */
public class produce {
	/** 生产标识,主键 */
	private int id;
	/** 要生产物品的店铺名字 */
	private String store_name;
	/** 要生产的物品名字 */
	private String article_name;
	/** 要生产的物品数量 */
	private int num;
	/** 生产总价格,生产单价默认是店铺销售价格的一半 */
	private double price;
	/** 生产时间 */
	private Date date;

	/**
	 * <strong>方法说明:</strong> <br>
	 * get()目的是为了获取变量,因为变量是私有的,只能在本类才能访问。 其他get()目的一样。
	 * 
	 * @return java.lang.Integer 获取生产标识
	 */
	public int getId() {
		return id;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * set()目的是为了设置变量的值,因为变量是私有的,只有利用本类的实例设置。 其他set()目的一样。
	 * 
	 * @param id
	 *            设置生产标识
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 默认构造器
	 */
	public produce() {

	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建一个全参数构造器,这样用来做存取数据库操作方便
	 * 
	 * @param id
	 *            ......
	 * @param store_name
	 *            ......
	 * @param article_name
	 *            ......
	 * @param num
	 *            ......
	 * @param price
	 *            ......
	 * @param date
	 *            ......
	 */
	public produce(int id, String store_name, String article_name, int num, double price, Date date) {
		super();
		this.id = id;
		this.store_name = store_name;
		this.article_name = article_name;
		this.num = num;
		this.price = price;
		this.date = date;
	}

	public produce(String store_name, String article_name, int num, double price, Date date) {
		super();
		this.store_name = store_name;
		this.article_name = article_name;
		this.num = num;
		this.price = price;
		this.date = date;
	}

}
