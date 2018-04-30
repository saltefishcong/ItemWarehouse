package com.Itemhouse.Linten;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;用于监听Session的属性变化
 */
public class houseHttpSessionAttrListen implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		String name = arg0.getName();
		Object object = arg0.getValue();
		System.out.println(session + "  增加了属性 " + name + "  属性值为 " + object + "  " + new Date());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		String name = arg0.getName();
		Object object = arg0.getValue();
		System.out.println(session + "  删除了属性 " + name + "  属性值为 " + object + "  " + new Date());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		String name = arg0.getName();
		Object object = arg0.getValue();
		System.out.println(session + "  替换了属性 " + name + "  属性值为 " + object + "  " + new Date());
	}

}
