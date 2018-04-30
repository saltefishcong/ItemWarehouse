package com.Itemhouse.eity;

import java.util.Date;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          评价类
 */
public class comment {
	/** 订单表标识 */
	private int order_id;
	/** 客户唯一标识,随机生成 */
	private String consumer_Identification;
	/** 客户满意度: 差0,一般1,好2 */
	private int satisfaction;
	/** 评价内容 */
	private String comment_text;
	/** 评价时间 */
	private Date date;

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

	public String getConsumer_Identification() {
		return consumer_Identification;
	}

	public void setConsumer_Identification(String consumer_Identification) {
		this.consumer_Identification = consumer_Identification;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getComment_text() {
		return comment_text;
	}

	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器
	 */
	public comment() {

	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建一个全参数构造器,这样用来做存取数据库操作方便
	 * 
	 * @param order_id
	 *            ......
	 * @param consumer_Identification
	 *            ......
	 * @param satisfaction
	 *            ......
	 * @param comment_text
	 *            ......
	 * @param date
	 *            ......
	 */
	public comment(int order_id, String consumer_Identification, int satisfaction, String comment_text, Date date) {
		super();
		this.order_id = order_id;
		this.consumer_Identification = consumer_Identification;
		this.satisfaction = satisfaction;
		this.comment_text = comment_text;
		this.date = date;
	}

	public comment(int order_id, String comment_text) {
		super();
		this.order_id = order_id;
		this.comment_text = comment_text;
	}

}
