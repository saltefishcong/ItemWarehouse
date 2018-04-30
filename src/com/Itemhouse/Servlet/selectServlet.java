package com.Itemhouse.Servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Itemhouse.eity.consumer;
import com.Itemhouse.eity.store;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;进行查询处理servlet
 */
public class selectServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public selectServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
		System.out.println("selectServlet  destroy()  " + new Date());
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("selectServlet  doPost()  " + new Date());
		HttpSession session = request.getSession(); // 获取session对象
		String type = request.getParameter("type"); // 获取动作类型
		if (type.equals("store")) { // 判断是否为商家的动作
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			if (str == null) { // 判断当前店铺是否为空
				request.setAttribute("exption", "请登陆!");
				request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response);
			}
			String action = request.getParameter("action"); // 获取动作的对象
			request.setAttribute("action", action); // 设置action
			request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转到商家servlet进行处理
		} else {
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取当前客户
			if (coumer == null) { // 判断当前店铺是否为空
				request.setAttribute("exption", "请登陆!");
				request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response);
			}
			String action = request.getParameter("action"); // 获取动作的对象
			request.setAttribute("action", action); // 设置action
			request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转到客户servlet进行处理
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		super.init();
		System.out.println("selectServlet  init()  " + new Date());
	}

}
