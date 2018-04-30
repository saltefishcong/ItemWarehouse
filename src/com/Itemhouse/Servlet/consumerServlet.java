package com.Itemhouse.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Itemhouse.Aid.ItemExeption;
import com.Itemhouse.Aid.Mail;
import com.Itemhouse.Aid.classinitialization;
import com.Itemhouse.Aid.converter;
import com.Itemhouse.Aid.randomNumber;
import com.Itemhouse.Aid.resolveUtil;
import com.Itemhouse.Dao.emailDao;
import com.Itemhouse.DaoImp.consumerDaoImp;
import com.Itemhouse.eity.article;
import com.Itemhouse.eity.buycar;
import com.Itemhouse.eity.comment;
import com.Itemhouse.eity.consumer;
import com.Itemhouse.eity.order;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;处理所有comsumer的请求
 */
public class consumerServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public consumerServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		System.out.println("consumerServlet  destroy()  " + new Date());
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
		System.out.println("consumerServlet  doPost()  " + new Date());
		String action = (String) request.getAttribute("action"); // 获取动作
		ServletContext sContext = getServletContext();
		classinitialization in = (classinitialization) sContext.getAttribute("in"); // 获取对象构造器
		randomNumber rand = (randomNumber) in.getObject("randomNumber", "com.Itemhouse.Aid.randomNumber");
		converter cover = (converter) in.getObject("converter", "com.Itemhouse.Aid.converter");
		Mail mail = (Mail) in.getObject("Mail", "com.Itemhouse.Aid.Mail");
		emailDao emdao = (emailDao) in.getObject("emailDao", "com.Itemhouse.Dao.emailDao");
		Connection con = (Connection) sContext.getAttribute("connection");
		consumerDaoImp condao = (consumerDaoImp) in.getObject("consumerDaoImp", "com.Itemhouse.DaoImp.consumerDaoImp");
		resolveUtil res = (resolveUtil) in.getObject("resolveUtil", "com.Itemhouse.Aid.resolveUtil");
		HttpSession session = request.getSession();
		if (action.equals("register")) { // 客户注册
			String name = request.getParameter("name"); // 获取注册的昵称
			String password = request.getParameter("password"); // 密码
			String email = request.getParameter("email"); // 邮箱地址
			try {
				String Identification = condao.getConsumerIdentification(con); // 生成一个随机的用户标识,用于区分昵称一样的用户
				String mark = condao.getConsumerMark(con); // 生成邮箱验证码mark
				int x = condao.consumerRegister(new consumer(name, password, Identification, email), mark, con,
						request); // 进行用户的注册数据操作
				mark = cover.getEncryption(mark); // 进行mark的二次双向加密
				System.out.println(mark);
				mark = rand.getEmailMark(mark, "consumer"); // 将注册类型封装到注册链接一起发送
				mail.send(email, "", mark); // 发送邮箱
				con.commit(); // 事务回滚
				request.getRequestDispatcher("/consumer/login.jsp").forward(request, response); // 注册成功跳转到登陆页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("用户注册异常,进行回滚");
					ItemExeption.getInfo(e, "用户注册异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("用户注册异常,回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("youxiang")) { // 客户邮箱验证
			String Identification = "";
			try {
				String mark = request.getParameter("mark"); // 获取到注册时候发送的mark标识
				mark = mark.replace(" ", "+"); // 将mark标识里面的空格变成+号,因为传递的时候获取的Mark里面的+号会变成空格,这是协议标准,所以要转换
				mark = cover.getDecryption(mark); // 进行mark解密
				Identification = emdao.emailSelect_name(mark, con); // 利用mark查询用户的唯一标识
				if (Identification.equals("")) {
					request.setAttribute("exption", "找不到注册的账号");
					throw new ItemExeption("找不到注册的账号", "20501");
				}
				int x = condao.UpdateConsumerInfo(Identification, con, request); // 验证成功,进行用户初始化的数据操作
				if (x <= 0) {
					request.setAttribute("exption", "初始化用户状态码异常");
					throw new ItemExeption("初始化用户状态码异常", "20502");
				}
				con.commit(); // 提交事务
				request.getRequestDispatcher("/consumer/login.jsp").forward(request, response); // 跳转登陆页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("初始化用户信息异常,进行回滚");
					ItemExeption.getInfo(e, "初始化用户信息异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("初始化用户信息异常,回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("cookielogin")) { // 客户cookie登陆
			Cookie[] cookies = request.getCookies(); // 获取前端传递的cookie
			for (Cookie e : cookies) {
				System.out.println(e.getName() + "  " + e.getValue());
			}
			String consumer_cookie = "";
			try {
				consumer_cookie = res.getCookieResolve(cookies, request); // 进行cookie解密
				System.out.println(consumer_cookie);
				consumer coumer = condao.consumerSelectCookieLogin(consumer_cookie, con, request); // 进行cookie的判断数据操作
				session.setAttribute("consumer", coumer); // 将返回的coumer对象设置到session属性中
				con.commit(); // 提交事务
				request.getRequestDispatcher("/consumer/index.jsp").forward(request, response); // 跳转登陆页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("cookie异常,进行回滚");
					ItemExeption.getInfo(e, "cookie异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					System.out.println("cookie异常,进行回滚失败"); // 回滚失败,某些数据已经被操作
				}
			}
		} else if (action.equals("login")) { // 登陆
			String Identification = request.getParameter("name"); // 获取用户标识
			String password = request.getParameter("password"); // 用户密码
			Cookie[] cookies = request.getCookies(); // 获取一起传递的用户cookie
			for (Cookie e : cookies) {
				System.out.println(e.getName() + "  " + e.getValue());
			}
			try {
				consumer coumer = condao.consumerSelectLogin(Identification, password, con, request); // 进行登陆的数据操作
				String cookie = condao.getCookie(); // 获取更新并单向加密的cookie
				cookie = cover.getEncryption(cookie); // 再次双向加密cookie
				System.out.println(cookie);
				cookie = rand.getConsumerCookie(cookie); // 设置cookie时间
				res.remvoeCookies(cookies, response); // 删除之前的cookie
				Cookie cook = new Cookie("consumer", cookie); // 封装cookie信息
				cook.setMaxAge(60 * 5); // 设置cookie生命周期
				cook.setPath("/ItemWarehouse/servlet/loginServlet"); // 设置获取cookie的请求路径
				response.addCookie(cook); // 将cookie写进客户端
				session.setAttribute("consumer", coumer); // 将返回的coumer对象设置到session属性中
				con.commit(); // 提交事务
				request.getRequestDispatcher("/consumer/index.jsp").forward(request, response); // 跳转登陆页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("用户登陆异常,进行回滚");
					ItemExeption.getInfo(e, "用户登陆异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("用户登陆异常,进行回滚失败"); // 回滚失败,某些数据已经被操作
				}
			}
		} else if (action.equals("updateinfo")) { // 更改用户信息
			consumer coumer = (consumer) session.getAttribute("consumer"); // 从session容器中获取存在的consumer对象
			String name = request.getParameter("name"); // 获取更改的昵称
			String oldpassword = request.getParameter("oldpassword"); // 获取更改的旧密码
			String newpassword = request.getParameter("newpassword"); // 获取更改的新密码
			String querenpassword = request.getParameter("querenpassword"); // 获取确认的新密码
			try {
				if (!newpassword.equals(querenpassword)) { // 判断两次密码是否一样
					request.setAttribute("exption", "两次密码不一致");
					throw new ItemExeption("两次密码不一致", "20503");
				}
				condao.consumerUpdateInfo(name, oldpassword, newpassword, coumer.getIdentification(), con, request);
				// 进行更改信息的数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "removecookie"); // 设置下一个动作
				session.removeAttribute("consumer"); // 删除存在与session容器的consumer对象
				request.getRequestDispatcher("/consumer/removecookie.jsp").forward(request, response); // 跳转removecookie页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("用户更改信息异常,进行回滚");
					ItemExeption.getInfo(e, "用户更改信息异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("用户更改信息异常,进行回滚失败");
				}
			}
		} else if (action.equals("removecookie")) { // 删除痕迹
			Cookie[] cookies = request.getCookies(); // 获取前台传递的cookie
			for (Cookie e : cookies) {
				System.out.println(e.getName() + "   " + e.getValue());
			}
			res.remvoeCookies(cookies, response); // 删除cookie
			session.removeAttribute("consumer"); // 删除存在与session容器的consumer对象
			request.getRequestDispatcher("/consumer/login.jsp").forward(request, response); // 跳转登陆页面
		} else if (action.equals("selectorder")) { // 查询客户所有订单
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取coumer对象
			try {
				List<order> orderlist = condao.consumerSelectorder(coumer.getIdentification(), con, request);
				// 进行查询订单的数据操作
				List<comment> comdaolist = condao.consumerSelectCommentText(coumer.getIdentification(), con, request);
				// 进行查询订单评论的数据操作
				if (orderlist.isEmpty()) { // 判断是否有订单
					request.setAttribute("order", "没有订单纪录");
				} else {
					request.setAttribute("order", orderlist);
					request.setAttribute("comment", comdaolist);
				}
				con.commit(); // 提交事务
				request.getRequestDispatcher("/consumer/order.jsp").forward(request, response); // 跳转订单页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询用户订单异常,进行回滚");
					ItemExeption.getInfo(e, "查询用户订单异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询用户订单异常,进行回滚失败");
				}
			}
		} else if (action.equals("addcomment")) { // 客户评论
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取coumer对象
			String comment_text = request.getParameter("commenttext"); // 获取评论内容
			String statu = request.getParameter("satisfaction"); // 获取订单的满意度
			int satisfaction = Integer.parseInt(statu); // 转换类型
			int order_id = Integer.parseInt(request.getParameter("orderid")); // 获取订单id
			String store_name = request.getParameter("store"); // 获取店铺名字
			String name = new String(store_name.getBytes("ISO-8859-1"), "utf-8"); // 转换编码
			try {
				if (comment_text == null) {
					request.setAttribute("exption", "请输入评价内容");
					throw new ItemExeption("请输入评价内容", "20504");
				}
				if (statu == null) {
					request.setAttribute("exption", "请选择订单满意度");
					throw new ItemExeption("请选择订单满意度", "20505");
				}
				condao.commentAdd(
						new comment(order_id, coumer.getIdentification(), satisfaction, comment_text, new Date()), con,
						request);
				// 进行添加评论的数据操作
				condao.setStoreGrade(satisfaction, name, con, request); // 根据满意度设置店铺等级
				con.commit(); // 事务提交
				request.setAttribute("action", "selectorder"); // 设置下一个动作
				request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("添加用户评价异常,进行回滚");
					ItemExeption.getInfo(e, "添加用户评价异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("添加用户评价异常,进行回滚失败");
				}
			}
		} else if (action.equals("selectordername")) { // 查询某个物品的订单纪录
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取coumer对象
			String article_name = request.getParameter("seriesorder"); // 获取查询的物品名称
			try {
				if (article_name.equals("")) {
					request.setAttribute("exption", "请输入物品名称");
					throw new ItemExeption("请输入物品名称", "20506");
				}
				List<order> orders = condao.consumerSelectorderList(coumer.getIdentification(), article_name, con,
						request);
				// 进行查询订单的数据操作
				if (orders.isEmpty()) {
					request.setAttribute("order", "没有该物品的订单纪录");
				} else {
					List<comment> list = condao.consumerSelectCommentList(coumer.getIdentification(), article_name, con,
							request);
					// 进行查询订单平路的数据操作
					request.setAttribute("order", orders);
					request.setAttribute("comment", list);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/consumer/seriesorder.jsp").forward(request, response); // 跳转物品订单页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询物品订单异常,进行回滚");
					ItemExeption.getInfo(e, "查询物品订单异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询物品订单异常,进行回滚失败");
				}
			}
		} else if (action.equals("addbuycar")) { // 添加物品到购物车
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取coumer对象
			String[] store_name = request.getParameterValues("store_name"); // 获取店铺名称
			String[] article = request.getParameterValues("article"); // 获取物品名称
			String[] money = request.getParameterValues("money"); // 获取物品金额
			String[] onlinenum = request.getParameterValues("onlinenum"); // 获取物品库存
			String[] num = request.getParameterValues("num"); // 获取加入购物车的数量
			try {
				if (num == null || num.length == 0) {
					request.setAttribute("exption", "请输入物品的数量");
					throw new ItemExeption("请输入物品的数量", "20507");
				}
				buycar[] car = new buycar[store_name.length]; // buycar类对象数组
				double[] article_money = new double[money.length];
				int[] online_num = new int[onlinenum.length];
				int[] buy_num = new int[num.length];
				for (int i = 0; i < store_name.length; i++) { // 循环类型转换
					if (num[i].equals("")) {
						request.setAttribute("exption", "请输入物品的数量");
						throw new ItemExeption("请输入物品的数量", "20508");
					}
					online_num[i] = Integer.parseInt(onlinenum[i]);
					buy_num[i] = Integer.parseInt(num[i]);
					if (buy_num[i] > online_num[i]) {
						request.setAttribute("exption", "购买的物品数量已经超过库存数量,如需超量购买请到购物车通知商家生产对应的物品!");
						throw new ItemExeption("购买的物品数量已经超过库存数量,如需超量购买请到购物车通知商家生产对应的物品!", "20509");
					}
					article_money[i] = Double.parseDouble(money[i]);
					car[i] = new buycar(coumer.getIdentification(), store_name[i], article[i], article_money[i],
							buy_num[i]);
					// 进行对象的数据封装
				}
				condao.buycarAdd(car, online_num, con, request); // 进行加入购物车的数据操作
				con.commit();
				request.setAttribute("action", "mybuycar"); // 设置下一个动作
				request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("添加购物车异常,进行回滚");
					ItemExeption.getInfo(e, "添加购物车异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("添加购物车异常,进行回滚失败");
				}
			}
		} else if (action.equals("selectarticle")) { // 查询所有店铺的所有物品
			try {
				List<article> list = condao.articleSelectList(con, request); // 查询物品操作
				if (list.isEmpty()) {
					request.setAttribute("article", "系统正在维护!");
				} else {
					request.setAttribute("article", list);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/consumer/articlelist.jsp").forward(request, response); // 跳转物品页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询物品列表异常,进行回滚");
					ItemExeption.getInfo(e, "查询物品列表异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询物品列表异常,进行回滚失败");
				}
			}
		} else if (action.equals("selectarticlename")) { // 查询所有店铺的同一个物品
			String article_name = request.getParameter("article_name"); // 获取查询的物品名称
			try {
				if (article_name.equals("")) {
					request.setAttribute("exption", "请输入搜索的物品");
					throw new ItemExeption("请输入搜索的物品", "20510");
				}
				List<article> list = condao.articleSelecseriestList(article_name, con, request); // 进行物品的查询操作
				if (list.isEmpty()) {
					request.setAttribute("article", "暂时没有该物品上架!");
				} else {
					request.setAttribute("article", list);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/consumer/seriesarticle.jsp").forward(request, response); // 跳转到物品页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务提交
					System.out.println("查询某个物品列表异常,进行回滚");
					ItemExeption.getInfo(e, "查询某个物品列表异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询某个物品列表异常,进行回滚失败");
				}
			}
		} else if (action.equals("mybuycar")) { // 查看客户的购物车
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取coumer对象
			try {
				List<buycar> list = condao.buycarSelectConsumer(coumer.getIdentification(), con, request); // 进行购物车查询的数据操作
				if (list.isEmpty()) {
					request.setAttribute("buycar", "购物车没有商品");
				} else {
					request.setAttribute("buycar", list);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/consumer/mybuycar.jsp").forward(request, response); // 跳转我的购物车页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询购物车列表异常,进行回滚");
					ItemExeption.getInfo(e, "查询购物车列表异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询购物车列表异常,进行回滚失败");
				}
			}
		} else if (action.equals("buy")) { // 购买物品
			consumer coumer = (consumer) session.getAttribute("consumer"); // 获取coumer对象
			String[] store_name = request.getParameterValues("store_name"); // 获取店家名称数组
			String[] car_id = request.getParameterValues("carid"); // 获取购物车id数组
			String[] article_name = request.getParameterValues("article"); // 获取物品名称数组
			String[] money = request.getParameterValues("money"); // 获取物品金额
			String[] place = request.getParameterValues("place"); // 获取订单地点
			String[] num = request.getParameterValues("num"); // 获取购买的数量
			try {
				if (car_id == null || car_id.length == 0) {
					request.setAttribute("exption", "请选择购买的物品");
					throw new ItemExeption("请选择购买的物品", "20511");
				}
				if (place == null || place.length == 0) {
					request.setAttribute("exption", "请输入订单地点");
					throw new ItemExeption("请输入订单地点", "20512");
				}
				buycar[] car = new buycar[store_name.length];
				int[] carid = new int[car_id.length];
				double[] article_money = new double[money.length];
				int[] buy_num = new int[num.length];
				for (int i = 0; i < store_name.length; i++) { // 循环类型转换
					if (place[i].equals("")) {
						request.setAttribute("exption", "请输入订单地点");
						throw new ItemExeption("请输入订单地点", "20513");
					}
					carid[i] = Integer.parseInt(car_id[i]);
					article_money[i] = Double.parseDouble(money[i]);
					buy_num[i] = Integer.parseInt(num[i]);
					car[i] = new buycar(coumer.getIdentification(), store_name[i], article_name[i], article_money[i],
							buy_num[i]);
					// 封装对象的属性
				}
				condao.consumerBuyArticle(car, place, carid, con, request); // 进行购买的数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "selectorder"); // 设置下一个动作
				request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO: handle exception
				try {
					con.rollback(); // 事务回滚
					System.out.println("购物异常,进行回滚");
					ItemExeption.getInfo(e, "购物异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("购物异常,进行回滚失败");
				}
			}
		} else if (action.equals("deletebuycar")) { // 删除购物车
			String[] car_id = request.getParameterValues("carid"); // 获取删除的购物车id
			try {
				if (car_id == null) {
					request.setAttribute("exption", "请选择删除的物品");
					throw new ItemExeption("请选择删除的物品", "20514");
				}
				int[] carid = new int[car_id.length];
				for (int i = 0; i < car_id.length; i++) { // 循环类型转换
					carid[i] = Integer.parseInt(car_id[i]);
				}
				condao.buycarDeleteList(carid, con, request); // 进行删除的数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "mybuycar"); // 设置下一个动作
				request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO: handle exception
				try {
					con.rollback(); // 事务回滚
					System.out.println("删除购物车物品异常,进行回滚");
					ItemExeption.getInfo(e, "删除购物车物品异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("删除购物车物品异常,进行回滚失败");
				}
			}
		} else if (action.equals("updatebuycar")) { // 更改购物车信息
			consumer coumer = (consumer) session.getAttribute("consumer");
			String[] car_id = request.getParameterValues("carid"); // 获取更改的购物车id数组
			String[] cutback_num = request.getParameterValues("cutbacknum"); // 获取更改的物品数量数组
			try {
				if (car_id == null || car_id.length == 0) {
					request.setAttribute("exption", "请选择更改的物品");
					throw new ItemExeption("请选择更改的物品", "20515");
				}
				if (cutback_num == null || cutback_num.length == 0) {
					request.setAttribute("exption", "请输入更改的物品数量");
					throw new ItemExeption("请输入更改的物品数量", "20516");
				}
				int[] carid = new int[car_id.length];
				int[] cutbacknum = new int[cutback_num.length];
				for (int i = 0; i < car_id.length; i++) { // 循环类型转换
					carid[i] = Integer.parseInt(car_id[i]);
					if (cutback_num[i].equals("")) {
						request.setAttribute("exption", "请输入更改的物品数量");
						throw new ItemExeption("请输入更改的物品数量", "20517");
					}
					cutbacknum[i] = Integer.parseInt(cutback_num[i]);
				}
				condao.buycarUpdateList(carid, cutbacknum, con, request); // 进行更改的数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "mybuycar"); // 设置下一个动作
				request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO: handle exception
				try {
					con.rollback(); // 事务回滚
					System.out.println("更改购物车物品异常,进行回滚");
					ItemExeption.getInfo(e, "更改购物车物品异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("更改购物车物品异常,进行回滚失败");
				}
			}
		} else if (action.equals("producebuy")) { // 通知店铺生产并购买
			consumer coumer = (consumer) session.getAttribute("consumer");
			String[] store_name = request.getParameterValues("store_name"); // 获取店铺名称数组
			String[] article = request.getParameterValues("article"); // 获取物品名称数组
			String[] num = request.getParameterValues("num"); // 获取购买的物品数量数组
			String[] place = request.getParameterValues("place"); // 获取订单地点数组
			try {
				if (store_name == null) {
					request.setAttribute("exption", "请输入店铺");
					throw new ItemExeption("请输入店铺", "20518");
				}
				if (article == null) {
					request.setAttribute("exption", "请输入购买的物品");
					throw new ItemExeption("请输入购买的物品", "20519");
				}
				if (num == null) {
					request.setAttribute("exption", "请输入购买的数量");
					throw new ItemExeption("请输入购买的数量", "20520");
				}
				if (place == null) {
					request.setAttribute("exption", "请输入订单的地点");
					throw new ItemExeption("请输入订单的地点", "20521");
				}
				int[] buy_num = new int[num.length];
				for (int i = 0; i < store_name.length; i++) { // 循环类型转换
					if (num[i].equals("")) {
						request.setAttribute("exption", "请输入购买的数量");
						throw new ItemExeption("请输入购买的数量", "20522");
					}
					if (place[i].equals("")) {
						request.setAttribute("exption", "请输入订单地点");
						throw new ItemExeption("请输入订单地点", "20523");
					}
					if (article[i].equals("")) {
						request.setAttribute("exption", "请输入购买的物品");
						throw new ItemExeption("请输入购买的物品", "20524");
					}
					if (store_name[i].equals("")) {
						request.setAttribute("exption", "请输入店铺名称");
						throw new ItemExeption("请输入店铺名称", "20525");
					}
					buy_num[i] = Integer.parseInt(num[i]);
				}
				condao.producebuy(coumer.getIdentification(), store_name, article, buy_num, place, con, request);
				// 进行生产购买的数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "selectorder"); // 设置下一个动作
				request.getRequestDispatcher("consumerServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO: handle exception
				try {
					con.rollback(); // 事务回滚
					System.out.println("通知商家生产异常,进行回滚");
					ItemExeption.getInfo(e, "通知商家生产异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("通知商家生产异常,进行回滚失败");
				}
			}
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
		System.out.println("consumerServlet  init()  " + new Date());
	}

}
