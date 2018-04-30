package com.Itemhouse.eity;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;客户类
 */
public class consumer {
	/** 客户名字 */
	private String name;
	/** 客户密码 */
	private String password;
	/** 客户唯一标识,随机生成 */
	private String Identification;
	/** 客户绑定的email */
	private String email;
	/** 客户的cookie,保持长链接 */
	private String cookie;
	/** 用户激活状态 */
	private int status;

	/**
	 * <strong>方法说明:</strong> <br>
	 * get()目的是为了获取变量,因为变量是私有的,只能在本类才能访问。 其他get()目的一样。
	 * 
	 * @return java.lang.String 获取用户名字属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * set()目的是为了设置变量的值,因为变量是私有的,只有利用本类的实例设置。 其他set()目的一样。
	 * 
	 * @param name
	 *            用户名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIdentification() {
		return Identification;
	}

	public void setIdentification(String identification) {
		Identification = identification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器
	 */
	public consumer() {

	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建一个全参数构造器,这样用来做存取数据库操作方便
	 * 
	 * @param name
	 *            ......
	 * @param password
	 *            ......
	 * @param identification
	 *            ......
	 * @param email
	 *            ......
	 * @param cookie
	 *            ......
	 */
	public consumer(String name, String password, String identification, String email, String cookie) {
		super();
		this.name = name;
		Identification = identification;
		this.email = email;
		this.cookie = cookie;
		this.password = password;
	}

	public consumer(String name, String identification) {
		super();
		this.name = name;
		Identification = identification;
	}

	public consumer(String name, String password, String identification) {
		super();
		this.name = name;
		this.password = password;
		Identification = identification;
	}

	public consumer(String name, String password, String identification, String email) {
		super();
		this.name = name;
		this.password = password;
		Identification = identification;
		this.email = email;
	}

}
