package com.Itemhouse.Linten;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8 <br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;用来监听ServletContext对象属性的操作:添加,删除,替换
 */
public class houseServletContextAttrListen implements ServletContextAttributeListener {

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取Servlet对象添加的属性
	 * 
	 * @param arg0
	 *            用来创建ServletContext对象
	 * @see javax.servlet.ServletContextAttributeListener#attributeAdded(javax.servlet.ServletContextAttributeEvent)
	 */
	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext sContext = arg0.getServletContext();
		String name = arg0.getName();
		Object value = arg0.getValue();
		System.out.println(sContext + " 进行了添加" + name + "属性操作,值为" + value + "  " + new Date());
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取Servlet对象删除的属性
	 * 
	 * @param arg0
	 *            用来创建ServletContext对象
	 * @see javax.servlet.ServletContextAttributeListener#attributeRemoved(javax.servlet.ServletContextAttributeEvent)
	 */
	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext sContext = arg0.getServletContext();
		String name = arg0.getName();
		Object value = arg0.getValue();
		System.out.println(sContext + " 进行了删除" + name + "属性操作,值为" + value + "  " + new Date());
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取Servlet对象替换的属性
	 * 
	 * @param arg0
	 *            用来创建ServletContext对象
	 * @see javax.servlet.ServletContextAttributeListener#attributeReplaced(javax.servlet.ServletContextAttributeEvent)
	 */
	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext sContext = arg0.getServletContext();
		String name = arg0.getName();
		Object value = arg0.getValue();
		System.out.println(sContext + " 进行了替换" + name + "属性操作,值为" + value + "  " + new Date());
	}

}
