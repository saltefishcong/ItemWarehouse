package com.Itemhouse.eity;

import java.util.Date;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;订单类
 */
public class order {
	/** 订单标识,自增 */
	private int id;
	/** 店铺名字 */
	private String store_name;
	/** 客户标识 */
	private String consumer_Identification;
	/** 物品名字 */
	private String store_article_name;
	/** 购买的数量 */
	private int num;
	/** 购买的价钱 */
	private double price;
	/** 客户的收获地点 */
	private String place;
	/** 订单的产生时间 */
	private Date date;
	/** 订单的状态:缺货0,正在生产1,交易完成2 */
	private int status;

	/**
	 * <strong>方法说明:</strong> <br>
	 * get()目的是为了获取变量,因为变量是私有的,只能在本类才能访问。 其他get()目的一样。
	 * 
	 * @return java.lang.String 获取店铺名字属性
	 */
	public String getStore_name() {
		return store_name;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * set()目的是为了设置变量的值,因为变量是私有的,只有利用本类的实例设置。 其他set()目的一样。
	 * 
	 * @param store_name
	 *            店铺名字
	 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getConsumer_Identification() {
		return consumer_Identification;
	}

	public void setConsumer_Identification(String consumer_Identification) {
		this.consumer_Identification = consumer_Identification;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器
	 */
	public order() {

	}

	public order(String store_name, String consumer_Identification, String store_article_name, int num, double price,
			String place, Date date, int status) {
		super();
		this.store_name = store_name;
		this.consumer_Identification = consumer_Identification;
		this.store_article_name = store_article_name;
		this.num = num;
		this.price = price;
		this.place = place;
		this.date = date;
		this.status = status;
	}

	public order(int id, String store_name, String consumer_Identification, String store_article_name, int num,
			double price, String place, Date date, int status) {
		super();
		this.id = id;
		this.store_name = store_name;
		this.consumer_Identification = consumer_Identification;
		this.store_article_name = store_article_name;
		this.num = num;
		this.price = price;
		this.place = place;
		this.date = date;
		this.status = status;
	}

	public String getStore_article_name() {
		return store_article_name;
	}

	public void setStore_article_name(String store_article_name) {
		this.store_article_name = store_article_name;
	}

}
