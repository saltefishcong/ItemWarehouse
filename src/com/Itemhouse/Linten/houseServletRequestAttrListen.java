package com.Itemhouse.Linten;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;监听ruqest对象的属性变化
 */
public class houseServletRequestAttrListen implements ServletRequestAttributeListener {

	@Override
	public void attributeAdded(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		ServletRequest request = arg0.getServletRequest();
		String name = arg0.getName();
		Object object = arg0.getValue();
		System.out.println(request + " 增加了属性 " + name + " 属性值为 " + object + "  " + new Date());
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		ServletRequest request = arg0.getServletRequest();
		String name = arg0.getName();
		Object object = arg0.getValue();
		System.out.println(request + " 删除了属性 " + name + " 属性值为 " + object + "  " + new Date());
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		ServletRequest request = arg0.getServletRequest();
		String name = arg0.getName();
		Object object = arg0.getValue();
		System.out.println(request + " 替换了属性 " + name + " 属性值为 " + object + "  " + new Date());
	}

}
