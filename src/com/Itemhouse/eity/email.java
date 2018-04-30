package com.Itemhouse.eity;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;邮件类
 */
public class email {
	/** 注册的类型:客户或者商家 */
	private String type;
	/** 注册的店铺名字或者客户名字 */
	private String username;
	/** 注册标识 */
	private String mark;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public email() {

	}

	public email(String type, String username, String mark) {
		super();
		this.type = type;
		this.username = username;
		this.mark = mark;
	}

}
