package com.Itemhouse.Linten;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import com.Itemhouse.Aid.ItemExeption;
import com.Itemhouse.Aid.classinitialization;
import com.Itemhouse.Aid.converter;
import com.Itemhouse.Aid.randomNumber;
import com.Itemhouse.Aid.resolveUtil;
import com.Itemhouse.DaoImp.consumerDaoImp;
import com.Itemhouse.DaoImp.storeDaoImp;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;监听request对象创建,销毁
 */
public class houseServletRequestListen implements ServletRequestListener {

	/**
	 * <strong>方法说明:</strong> <br>
	 * 本次http服务的ServletRequest对象被销毁
	 * 
	 * @param arg0
	 *            销毁ServletRequest对象
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("houseServletRequestListen  requestDestroyed()销毁开始 " + new Date());
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建ServletRequest对象并进行查询操作
	 * 
	 * @param arg0
	 *            创建ServletRequest对象
	 * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("houseServletRequestListen  requestInitialized()初始化开始  " + new Date());
		ServletRequest request = arg0.getServletRequest();
		ServletContext sContext = request.getServletContext();
		classinitialization csin = (classinitialization) sContext.getAttribute("in");
		converter cs = null;
		randomNumber rs = null;
		resolveUtil res = null;
		storeDaoImp in = null;
		consumerDaoImp condao = null;
		Connection con = (Connection) sContext.getAttribute("connection");
		cs = (converter) csin.getObject("converter", "com.Itemhouse.Aid.converter");
		rs = (randomNumber) csin.getObject("randomNumber", "com.Itemhouse.Aid.randomNumber");
		res = (resolveUtil) csin.getObject("resolveUtil", "com.Itemhouse.Aid.resolveUtil");
		in = (storeDaoImp) csin.getObject("storeDaoImp", "com.Itemhouse.DaoImp.storeDaoImp");
		condao = (consumerDaoImp) csin.getObject("consumerDaoImp", "com.Itemhouse.DaoImp.consumerDaoImp");
		in.init(sContext);
		condao.init(sContext);
		try {
			cs.initDES();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			ItemExeption.getInfo(e, "初始化converter对象异常");
		}
		res.init(sContext);

	}

}
