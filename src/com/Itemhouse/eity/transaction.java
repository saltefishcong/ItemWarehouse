package com.Itemhouse.eity;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          交易类
 */
public class transaction {
	/** 交易Id,自增 */
	private int id;
	/** 订单号标识 */
	private int order_id;
	/** 订单最终状态: 失败0,成功1 */
	private int flag;
	/** 订单失败原因,如果成功则为'交易成功' */
	private String note;

	/**
	 * <strong>方法说明:</strong> <br>
	 * get()目的是为了获取变量,因为变量是私有的,只能在本类才能访问。 其他get()目的一样。
	 * 
	 * @return java.lang.Integer 获取订单号id
	 */
	public int getOrder_id() {
		return order_id;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * set()目的是为了设置变量的值,因为变量是私有的,只有利用本类的实例设置。 其他set()目的一样。
	 * 
	 * @param order_id
	 *            订单号
	 */
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getFlag() {
		return flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器
	 */
	public transaction() {

	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建一个全参数构造器,这样用来做存取数据库操作方便
	 * 
	 * @param order_id
	 *            ......
	 * @param flag
	 *            ......
	 * @param note
	 *            ......
	 */
	public transaction(int order_id, int flag, String note) {
		super();
		this.order_id = order_id;
		this.flag = flag;
		this.note = note;
	}

	public transaction(int id, int order_id, int flag, String note) {
		super();
		this.id = id;
		this.order_id = order_id;
		this.flag = flag;
		this.note = note;
	}
}
