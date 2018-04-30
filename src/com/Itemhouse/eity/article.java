package com.Itemhouse.eity;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;店铺物品类
 */
public class article {
	/** 物品主键,自增类型 */
	private int id;
	/** 店铺名字 */
	private String store_name;
	/** 店铺某个物品 */
	private String article;
	/** 物品的价格 */
	private double money;
	/** 物品数量 */
	private int num;

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

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器
	 */
	public article() {

	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建一个全参数构造器,这样用来做存取数据库操作方便
	 * 
	 * @param store_name
	 *            ......
	 * @param article
	 *            ......
	 * @param money
	 *            ......
	 */
	public article(String store_name, String article, double money) {
		super();
		this.store_name = store_name;
		this.article = article;
		this.money = money;
	}

	public article(int id, String store_name, String article, double money) {
		super();
		this.id = id;
		this.store_name = store_name;
		this.article = article;
		this.money = money;
	}

	public article(int id, String store_name, String article, double money, int num) {
		super();
		this.id = id;
		this.store_name = store_name;
		this.article = article;
		this.money = money;
		this.num = num;
	}

	public article(String store_name, String article, double money, int num) {
		super();
		this.store_name = store_name;
		this.article = article;
		this.money = money;
		this.num = num;
	}

}
