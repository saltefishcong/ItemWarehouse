package com.Itemhouse.eity;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;客户购物车
 */
public class buycar {
	/** 自增Id */
	private int id;
	/** 用户标识 */
	private String Identification;
	/** 商家名字 */
	private String store_name;
	/** 购买的物品 */
	private String article;
	/** 物品价格 */
	private double money;

	/** 购买的数量 */
	private int num;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentification() {
		return Identification;
	}

	public void setIdentification(String identification) {
		Identification = identification;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public buycar(String identification, String store_name, String article, int num) {
		super();
		Identification = identification;
		this.store_name = store_name;
		this.article = article;
		this.num = num;
	}

	public buycar(int id, String identification, String store_name, String article, double money, int num) {
		super();
		this.id = id;
		Identification = identification;
		this.store_name = store_name;
		this.article = article;
		this.money = money;
		this.num = num;
	}

	public buycar(String identification, String store_name, String article, double money, int num) {
		super();
		Identification = identification;
		this.store_name = store_name;
		this.article = article;
		this.money = money;
		this.num = num;
	}
}
