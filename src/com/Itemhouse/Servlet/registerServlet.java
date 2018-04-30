package com.Itemhouse.Servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行注册处理servlet
 */
public class registerServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
		System.out.println("进入registerServlet  destroy()  " + new Date());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("registerServlet  doPost()  " + new Date());
		String type = request.getParameter("type"); // 获取动作类型
		if (type.equals("store")) { // 判断是否为商家的动作
			String action = request.getParameter("action"); // 获取动作的对象
			request.setAttribute("action", action); // 设置action
			request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转到商家servlet进行处理
		} else {
			String action = request.getParameter("action"); // 获取动作的对象
			request.setAttribute("action", action); // 设置action
			request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转到客户servlet进行处理
		}
	}

	public void init() throws ServletException {
		super.init();
		System.out.println("进入registerServlet init()  " + new Date());
	}

}
