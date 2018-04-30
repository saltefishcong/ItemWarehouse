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
import com.Itemhouse.DaoImp.storeDaoImp;
import com.Itemhouse.eity.article;
import com.Itemhouse.eity.comment;
import com.Itemhouse.eity.order;
import com.Itemhouse.eity.produce;
import com.Itemhouse.eity.store;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;处理所有store的请求
 */
public class storeServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
		System.out.println("storeServlet  destroy()  " + new Date());
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
		System.out.println("storeServlet  doPost()  " + new Date());
		ServletContext sContext = getServletContext();
		classinitialization in = (classinitialization) sContext.getAttribute("in"); // 获取对象构造器
		storeDaoImp strdao = (storeDaoImp) in.getObject("storeDaoImp", "com.Itemhouse.DaoImp.storeDaoImp");
		randomNumber rand = (randomNumber) in.getObject("randomNumber", "com.Itemhouse.Aid.randomNumber");
		converter cover = (converter) in.getObject("converter", "com.Itemhouse.Aid.converter");
		Mail mail = (Mail) in.getObject("Mail", "com.Itemhouse.Aid.Mail");
		emailDao emdao = (emailDao) in.getObject("emailDao", "com.Itemhouse.Dao.emailDao");
		resolveUtil res = (resolveUtil) in.getObject("resolveUtil", "com.Itemhouse.Aid.resolveUtil");
		Connection con = (Connection) sContext.getAttribute("connection");
		HttpSession session = request.getSession();
		String action = (String) request.getAttribute("action"); // 获取本次请求的动作
		if (action.equals("youxiang")) { // 邮箱验证
			String type = request.getParameter("type"); // 是否注册的是商家
			String mark = request.getParameter("mark"); // 获取到注册时候发送的mark标识
			String name = "";
			try {
				mark = mark.replace(" ", "+"); // 将mark标识里面的空格变成+号,因为传递的时候获取的Mark里面的+号会变成空格,这是协议标准,所以要转换
				mark = cover.getDecryption(mark); // 进行mark解密
				name = emdao.emailSelect_name(mark, con); // 查询item_email是否有传递过来的mark
				if (name.equals("")) { // 如果没有传递过来的mark就代表这个用户名已经被别人使用了
					request.setAttribute("exption", "该注册链接已经失效!");
					throw new ItemExeption("该注册链接已经失效!", "10501");
				}
				int x = strdao.storePerfectInfo(name, con, request); // 进行完善资料,初始化cookie,monry,grade,status更改
				if (x <= 0) { // 是否更改成功
					request.setAttribute("exption", "初始化店铺信息异常!");
					throw new ItemExeption("初始化店铺信息异常!", "10502");
				}
				String cookie = strdao.getCookie(); // 更改成功后获取cookie并进行单向加密
				System.out.println(cookie);
				cookie = cover.getEncryption(cookie); // 进行可逆加密,加密两次防止非法人员盗取cookie
				System.out.println(cookie);
				cookie = rand.getStoreCookie(cookie); // 添加cookie时间
				Cookie cook = new Cookie("stroe", cookie); // 封装信息
				cook.setMaxAge(60 * 5); // 设置生命周期
				cook.setPath("/ItemWarehouse/servlet/loginServlet"); // 设置cookie获取路径,不是这请求路径不能获取该cookie
				response.addCookie(cook); // 写进客户端
				con.commit(); // 提交事务
				request.getRequestDispatcher("/welcome.jsp").forward(request, response); // 跳转页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 数据操作异常,进行回滚
					System.out.println("完善店铺信息异常,进行回滚");
					ItemExeption.getInfo(e, "完善店铺信息异常");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 把异常信息带到异常页面打印
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("完善店铺信息异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("register")) { // 注册
			String name = request.getParameter("name"); // 获取店铺名称
			String email = request.getParameter("email"); // 获取注册邮箱,已便注册成功发送邮件验证
			String password = request.getParameter("password"); // 获取店铺密码
			int phone = Integer.parseInt(request.getParameter("phone")); // 获取店铺电话
			Date date = new Date(); // 获取注册时间
			store str = new store(name, email, password, phone, 0, date); // 进行封装
			int x = 0;
			try {
				String mark = strdao.getStoreMark(con);
				x = strdao.storeRegister(str, mark, con, request); // 进行注册的数据操作
				mark = cover.getEncryption(mark); // 进行mark的二次双向加密
				System.out.println(mark);
				mark = rand.getEmailMark(mark, "store"); // 将注册类型封装到注册链接一起发送
				mail.send(email, "", mark); // 发送邮箱,第二个为发送邮件的邮箱地址
				con.commit(); // 事务回滚
				request.getRequestDispatcher("/store/login.jsp").forward(request, response); // 注册成功跳转到登陆页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("店铺注册异常,进行回滚");
					ItemExeption.getInfo(e, "店铺注册异常"); // 打印异常信息
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("店铺注册异常,回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("updatepassword")) { // 更改密码
			String oldpassword = request.getParameter("oldpassword"); // 获取店铺原来的密码
			String newpassword = request.getParameter("newpassword"); // 获取店铺新密码
			String confirmpassword = request.getParameter("confirmpassword"); // 获取店铺再次确认的新密码
			store str = (store) session.getAttribute("store"); // 获取登陆成功存在session里面的用户
			String store_name = str.getName(); // 获取用户名
			Cookie[] cookies = request.getCookies(); // 获取前端发过来的Cookie
			for (Cookie e : cookies) {
				System.out.println(e.getName() + " " + e.getValue());
			}
			try {
				if (!newpassword.equals(confirmpassword)) { // 判断两次密码是否一致
					request.setAttribute("exption", "新密码不一致,请重新输入!");
					throw new ItemExeption("新密码不一致,请重新输入!", "10503");
				}
				strdao.storeUpdateInfo(oldpassword, newpassword, store_name, con, request); // 进行数据操作
				res.removeStoreCookie(cookies, response); // 修改密码后把之前的cookie删除,防止非法成员获取
				session.removeAttribute("store"); // 删除存在session用户的容器
				con.commit(); // 提交事务
				request.getRequestDispatcher("/store/removecookie.jsp").forward(request, response);
				// 跳到删除cookie页面,因为cookie的路径是servlet/loginServlet,所以直接设置action获取不到cookie,要用一个页面过渡才能删除
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("更改密码异常,进行回滚");
					ItemExeption.getInfo(e, "更改密码异常");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("更改密码异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("login")) { // 登陆
			String names = request.getParameter("name"); // 获取用户名
			// String name = new String(names.getBytes("ISO-8859-1"), "utf-8");
			// // 进行转码的原因是因为有时候拦截器不一定能转换所有类型,所以进行转码
			String password = request.getParameter("password"); // 获取密码
			Cookie[] cookies = request.getCookies(); // 获取前端传递的cookie
			try {
				store str = strdao.stroeLogin(password, names, con, request); // 进行数据操作
				String store_cookie = strdao.getCookie(); // 获取新的cookie并单向加密
				store_cookie = cover.getEncryption(store_cookie); // 再次双向加密cookie
				System.out.println(store_cookie);
				store_cookie = rand.getStoreCookie(store_cookie); // 设置cookie时间
				res.removeStoreCookie(cookies, response); // 删除之前的cookie
				Cookie cook = new Cookie("stroe", store_cookie); // 封装cookie信息
				cook.setMaxAge(60 * 5); // 设置cookie生命周期
				cook.setPath("/ItemWarehouse/servlet/loginServlet"); // 设置获取cookie的请求路径
				response.addCookie(cook); // 将cookie写进客户端
				if (session.getAttribute("store") == null) { // 判断用户是否为空
					session.setAttribute("store", str); // 将用户添加到session
				} else {
					session.removeAttribute("store"); // 删除用户
					session.setAttribute("store", str); // 将用户添加到session
				}
				con.commit(); // 提交事务
				request.getRequestDispatcher("/store/index.jsp").forward(request, response); // 跳转到店铺操作页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("用户登陆异常,进行回滚");
					ItemExeption.getInfo(e, "用户登陆异常");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("用户登陆异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("cookielogin")) { // cookie登陆
			Cookie[] cookies = request.getCookies(); // 获取前端传递的cookie
			for (Cookie e : cookies) {
				System.out.println(e.getName() + "  " + e.getValue());
			}
			String store_cookie = "";
			try {
				store_cookie = res.getCookieResolve(cookies, request); // 解密cookie
				if (store_cookie.equals("")) { // 是否有cookie
					request.setAttribute("exption", "cookie异常,请重新登陆");
					throw new ItemExeption("cookie异常,请重新登陆", "10504");
				}
				store str = strdao.stroeCooikeLogin(store_cookie, con, request); // 进行数据操作
				System.out.println(str.getName()); // 打印获取到的店铺名字
				session.setAttribute("store", str); // 将用户添加到session
				con.commit(); // 提交事务
				request.getRequestDispatcher("/store/index.jsp").forward(request, response); // 跳转到店铺操作页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("cookie失效,进行回滚");
					ItemExeption.getInfo(e, "cookie失效");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("cookie失效,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("removecookie")) { // 删除cookie
			Cookie[] cookies = request.getCookies(); // 获取前端传递的cookie
			res.removeStoreCookie(cookies, response); // 删除所有传递的cookie
			session.removeAttribute("store"); // 删除存在session的店铺
			request.getRequestDispatcher("/store/login.jsp").forward(request, response); // 跳转到登陆页面
		} else if (action.equals("selectarticle")) {
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			try {
				List<article> list = strdao.getSelectArticleList(str.getName(), con, request); // 进行数据操作
				if (list.isEmpty()) {
					request.setAttribute("article", "暂时没有上架商品");
				} else {
					request.setAttribute("article", list);
				}
				con.commit(); // 提交事务
				request.getRequestDispatcher("/store/article.jsp").forward(request, response); // 跳转到物品页面打印当前店铺的物品
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询商品列表出错,进行回滚");
					ItemExeption.getInfo(e, "查询商品列表出错!");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询商品列表出错,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("deletearticle")) { // 删除物品
			String[] articles = request.getParameterValues("articleid[]"); // 获取到物品id数组
			try {
				if (articles == null) { // 判断数组是否为null
					request.setAttribute("exption", "请选择要删除的商品");
					throw new ItemExeption("请选择要删除的商品", "10505");
				}
				int articleid[] = new int[articles.length]; // 进行类型转换
				for (int i = 0; i < articles.length; i++) {
					articleid[i] = Integer.parseInt(articles[i]);
				}
				int x = strdao.deleteArticleList(articleid, con, request); // 进行数据操作
				con.commit(); // 提交事务
				request.setAttribute("action", "selectarticle"); // 设置下一个动作
				request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("删除物品,进行回滚");
					ItemExeption.getInfo(e, "删除物品列表出错!");
					request.setAttribute("action", "selectarticle"); // 设置下一个动作
					request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("删除物品列表出错,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("updatearticle")) { // 更改物品信息
			String[] articles = request.getParameterValues("articleid[]"); // 获取物品id数组
			String[] articlenames = request.getParameterValues("articlename"); // 获取物品名字
			String[] articlemoneys = request.getParameterValues("articlemoney"); // 获取物品金额
			try {
				if (articles == null) { // 判断物品id是否为空
					request.setAttribute("exption", "请选择要更改的商品");
					throw new ItemExeption("请选择要更改的商品", "10506");
				}
				if (articlenames == null) { // 判断物品名字是否为空
					request.setAttribute("exption", "请选择要更改的商品名字");
					throw new ItemExeption("请选择要更改的商品", "10507");
				}
				if (articlemoneys == null) { // 判断物品金额是否为空
					request.setAttribute("exption", "请选择要更改的商品金额");
					throw new ItemExeption("请选择要更改的商品", "10508");
				}
				int[] articleid = new int[articles.length]; // 进行类型转换
				double[] articlemoney = new double[articlemoneys.length];
				for (int i = 0; i < articles.length; i++) {
					if (articlemoneys[i].equals("")) { // 判断每个要更改金额的物品金额是否为空
						request.setAttribute("exption", "请选择要更改的商品金额");
						throw new ItemExeption("请选择要更改的商品", "10509");
					}
					articleid[i] = Integer.parseInt(articles[i]);
					articlemoney[i] = Double.parseDouble(articlemoneys[i]);
				}
				int x = strdao.UpdateArticleList(articleid, articlenames, articlemoney, con, request); // 进行数据操作
				con.commit(); // 事务回滚
				request.setAttribute("action", "selectarticle"); // 设置下一个动作
				request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("更改物品异常,进行回滚");
					ItemExeption.getInfo(e, "更改物品列表出错!");
					request.setAttribute("action", "selectarticle"); // 设置下一个动作
					request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("更改物品异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("selectorder")) { // 查询店铺所有订单
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			try {
				List<order> list = strdao.getSelectOrderList(str.getName(), con, request); // 进行数据操作
				if (list.isEmpty()) { // 判断list是否为空
					request.setAttribute("order", "没有相关的订单");
				} else {
					List<comment> comlist = strdao.getStoreCommentList(str.getName(), con, request); // 查询店铺所有的订单评论
					request.setAttribute("order", list);
					request.setAttribute("comment", comlist);
				}
				con.commit(); // 进行事务提交
				request.getRequestDispatcher("/store/order.jsp").forward(request, response); // 跳转到订单纪录页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询订单列表出错,进行回滚");
					ItemExeption.getInfo(e, "查询订单列表出错!");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询订单列表出错,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("selectordername")) { // 查询店铺一个物品的所有订单纪录
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			String article_name = request.getParameter("seriesorder"); // 获取查询物品的名称
			try {
				List<order> list = strdao.getSelectSeriesList(str.getName(), article_name, con, request); // 进行数据操作
				if (list.isEmpty()) { // 判断List是否为空
					request.setAttribute("order", "没有该物品的订单纪录!");
				} else {
					List<comment> comlist = strdao.getStoreSeriesCommentList(str.getName(), article_name, con, request);
					// 获取某个物品的订单评论
					request.setAttribute("order", list);
					request.setAttribute("comment", comlist);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/store/seriesorder.jsp").forward(request, response); // 跳转到物品的订单纪录页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询物品的订单列表出错,进行回滚");
					ItemExeption.getInfo(e, "查询物品的订单列表出错!");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询物品的订单列表出错,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("addarticle")) { // 添加物品
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			String[] articlename = request.getParameterValues("articlename"); // 获取物品的名字数组
			String[] articlemoney = request.getParameterValues("articlemoney"); // 获取物品的金额数组
			try {
				if (articlename == null) { // 判断物品的名字是否为空
					request.setAttribute("exption", "请选择要添加的商品");
					throw new ItemExeption("请选择要添加的商品", "10510");
				}
				if (articlemoney == null) { // 判断物品的金额是否为空
					request.setAttribute("exption", "请设置商品的价格");
					throw new ItemExeption("请设置商品的价格", "10511");
				}
				double[] money = new double[articlemoney.length]; // 进行类型转换
				for (int i = 0; i < articlemoney.length; i++) {
					if (articlename[i].equals("")) { // 判断添加的每个物品名称是否为空
						request.setAttribute("exption", "请选择要添加的商品");
						throw new ItemExeption("请选择要添加的商品", "10512");
					}
					if (articlemoney[i].equals("")) { // 判断添加的每个物品金额是否为空
						request.setAttribute("exption", "请设置商品的价格");
						throw new ItemExeption("请设置商品的价格", "10513");
					}
					money[i] = Double.parseDouble(articlemoney[i]);
				}
				strdao.addArticleList(articlename, money, str.getName(), con, request); // 进行数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "selectarticle"); // 设置下一个动作
				request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("添加物品异常,进行回滚");
					ItemExeption.getInfo(e, "添加物品列表出错!");
					request.setAttribute("action", "selectarticle"); // 设置下一个动作
					request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("添加物品异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("produce")) { // 生产物品
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			String[] articlename = request.getParameterValues("articlename"); // 获取生产物品的名字数组
			String[] articlenum = request.getParameterValues("articlenum"); // 获取生产物品的金额数组
			try {
				if (articlename == null) { // 判断articlename是否为空
					request.setAttribute("exption", "请选择要添加的商品");
					throw new ItemExeption("请选择要添加的商品", "10514");
				}
				if (articlenum == null) { // 判断articlenum是否为空
					request.setAttribute("exption", "请选择要添加商品的数量");
					throw new ItemExeption("请选择要添加商品的数量", "10515");
				}
				int[] num = new int[articlenum.length]; // 进行类型转换
				for (int i = 0; i < articlenum.length; i++) {
					if (articlename[i].equals("")) { // 判断每个要生产的物品名字是否为空
						request.setAttribute("exption", "请选择要添加的商品");
						throw new ItemExeption("请选择要添加的商品", "10516");
					}
					if (articlenum[i].equals("")) { // 判断判断每个要生产的物品数量是否为空
						request.setAttribute("exption", "请选择要添加商品的数量");
						throw new ItemExeption("请选择要添加商品的数量", "10517");
					}
					num[i] = Integer.parseInt(articlenum[i]);
				}
				strdao.addProduceList(str.getName(), articlename, num, con, request); // 进行数据操作
				con.commit(); // 事务提交
				request.setAttribute("action", "selectarticle"); // 设置下一个动作
				request.getRequestDispatcher("storeServlet").forward(request, response); // 跳转并执行设置的动作
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("生产物品异常,进行回滚");
					ItemExeption.getInfo(e, "生产物品列表出错!");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转并执行设置的动作
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("生产物品异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("selectproduce")) { // 查询店铺生产纪录
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			try {
				List<produce> list = strdao.selectProdceList(str.getName(), con, request); // 进行数据操作
				if (list.isEmpty()) { // 判断list是否为空
					request.setAttribute("produce", "没有生产纪录");
				} else {
					request.setAttribute("produce", list);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/store/selectproduce.jsp").forward(request, response); // 跳转到生产纪录页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询店铺生产纪录异常,进行回滚");
					ItemExeption.getInfo(e, "查询店铺生产纪录出错!");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询店铺生产纪录异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
				}
			}
		} else if (action.equals("selectproducename")) { // 查询店铺某个物品的生产纪录
			store str = (store) session.getAttribute("store"); // 获取当前店铺
			String article_name = request.getParameter("articlename"); // 获取查询物品的名字
			try {
				List<produce> list = strdao.getSelectSeriesProduceList(str.getName(), article_name, con, request); // 进行数据操作
				if (list.isEmpty()) { // 判断list是否为空
					request.setAttribute("produce", "没有该物品的生产纪录!");
				} else {
					request.setAttribute("produce", list);
				}
				con.commit(); // 事务提交
				request.getRequestDispatcher("/store/seriesproduce.jsp").forward(request, response); // 跳转到物品的生产纪录页面
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback(); // 事务回滚
					System.out.println("查询物品生产纪录异常,进行回滚");
					ItemExeption.getInfo(e, "查询物品生产纪录出错!");
					request.getRequestDispatcher("/exption/Exption.jsp").forward(request, response); // 跳转到异常页面打印信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("查询物品生产纪录异常,进行回滚失败"); // 代表回滚失败,部分信息已经被插入数据库
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
		super.init();
		System.out.println("storeServlet  init()  " + new Date());
	}

}
