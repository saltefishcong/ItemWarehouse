package com.Itemhouse.eity;

import java.util.Date;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;店家类
 */
public class store {
	/** 店铺名称,唯一,不能重复 */
	private String name;
	/** 店铺绑定的email */
	private String email;
	/** 店铺登陆的密码 */
	private String password;
	/** 店铺绑定的手机号码 */
	private int phone;
	/** 店铺的总资产,体现了店铺的营业状态 */
	private double money;
	/** 店铺的等级,等级越高能接单的机会越大 */
	private int grade;
	/** 将店铺的信息写进cookie,保持登陆状态 */
	private String cookie;
	/** 店铺的状态:0没激活,1激活 */
	private int status;
	/** 店铺名字的占注册时间,邮件就只有两分钟的注册时间,超过两分钟本次注册失效,重新释放用户名 */
	private Date date;

	/**
	 * <strong>方法说明:</strong> <br>
	 * get()目的是为了获取变量,因为变量是私有的,只能在本类才能访问。 其他get()目的一样。
	 * 
	 * @return java.lang.String 获取店家名字属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * set()目的是为了设置变量的值,因为变量是私有的,只有利用本类的实例设置。 其他set()目的一样。
	 * 
	 * @param name
	 *            店铺的唯一标识
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器
	 */
	public store() {

	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建一个全参数构造器,这样用来做存取数据库操作方便
	 * 
	 * @param name
	 *            ......
	 * @param email
	 *            ......
	 * @param password
	 *            ......
	 * @param phone
	 *            ......
	 * @param money
	 *            ......
	 * @param grade
	 *            ......
	 * @param cookie
	 *            ......
	 */
	public store(String name, String email, String password, int phone, double money, int grade, String cookie) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.money = money;
		this.grade = grade;
		this.cookie = cookie;
		this.password = password;
	}

	public store(String name, String email, int phone, double money, int grade) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.money = money;
		this.grade = grade;
	}

	public store(String name, String email, String password, int phone, double money, int grade, String cookie,
			int status, Date date) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.money = money;
		this.grade = grade;
		this.cookie = cookie;
		this.status = status;
		this.date = date;
	}

	public store(String name, String email, int phone, double money, int grade, Date date) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.money = money;
		this.grade = grade;
		this.date = date;
	}

	public store(String name, String email, String password, int phone, int status, Date date) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.status = status;
		this.date = date;
	}

}
