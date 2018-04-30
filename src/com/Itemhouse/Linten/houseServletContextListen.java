package com.Itemhouse.Linten;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.Itemhouse.Aid.ItemExeption;
import com.Itemhouse.Aid.classinitialization;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;在web启动关闭时进行数据库操作
 * 
 */
public class houseServletContextListen implements ServletContextListener {

	/**
	 * <strong>方法说明:</strong><br>
	 * 用于web应用停止时销毁数据库对象
	 * 
	 * @param arg0
	 *            用于创建ServletContext对象
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("进入houseServletContextListen  contextDestroyed()开始销毁  " + new Date());
		ServletContext sContext = arg0.getServletContext();
		Connection con = (Connection) sContext.getAttribute("connection");
		try {
			con.close();
			System.out.println("关闭完成!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ItemExeption.getInfo(e, "关闭数据库出错!");
		}
	}

	/**
	 * <strong>方法说明:</strong><br>
	 * 用于对web应用初始化时创建数据库对象
	 * 
	 * @param arg0
	 *            用于创建ServletContext对象
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("进入houseServletContextListen  contextInitialized()进行初始化  " + new Date());
		ServletContext sContext = arg0.getServletContext();
		String driver = sContext.getInitParameter("driver");
		String url = sContext.getInitParameter("url");
		String username = sContext.getInitParameter("username");
		String password = sContext.getInitParameter("password");
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, username, password);
			con.setAutoCommit(false); // 设置不自动提交事务
			sContext.setAttribute("connection", con);
			System.out.println("初始化完成 Connection !  " + new Date());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ItemExeption.getInfo(e, "创建数据库对象出错");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			ItemExeption.getInfo(e, "找不到链接数据库的类");
		}
		classinitialization in = new classinitialization();
		in.startclassinitialization();
		sContext.setAttribute("in", in);
		System.out.println("初始化对象完成  " + new Date());
	}

}
