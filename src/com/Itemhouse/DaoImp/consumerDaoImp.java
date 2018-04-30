package com.Itemhouse.DaoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.Itemhouse.Aid.ItemExeption;
import com.Itemhouse.Aid.Mail;
import com.Itemhouse.Aid.classinitialization;
import com.Itemhouse.Aid.converter;
import com.Itemhouse.Aid.randomNumber;
import com.Itemhouse.Dao.articleDao;
import com.Itemhouse.Dao.buycarDao;
import com.Itemhouse.Dao.commentDao;
import com.Itemhouse.Dao.consumerDao;
import com.Itemhouse.Dao.emailDao;
import com.Itemhouse.Dao.orderDao;
import com.Itemhouse.Dao.produceDao;
import com.Itemhouse.Dao.storeDao;
import com.Itemhouse.Dao.transactionDao;
import com.Itemhouse.eity.article;
import com.Itemhouse.eity.buycar;
import com.Itemhouse.eity.comment;
import com.Itemhouse.eity.consumer;
import com.Itemhouse.eity.email;
import com.Itemhouse.eity.order;
import com.Itemhouse.eity.produce;
import com.Itemhouse.eity.transaction;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;对consumerDao的功能进行链接
 */
public class consumerDaoImp {
	private classinitialization in;
	/** emailDao类实例 */
	private emailDao emdao;
	/** converter类实例 */
	private converter cover;
	/** consumerDao实例 */
	private consumerDao condao;
	/** orderDao实例 */
	private orderDao ordao;
	/** storeDao实例 */
	private storeDao strdao;
	/** commentDao实例 */
	private commentDao comdao;
	/** buycarDao实例 */
	private buycarDao buydao;
	/** articleDao实例 */
	private articleDao artdao;
	/** produceDao实例 */
	private produceDao prodao;
	/** transactionDao实例 */
	private transactionDao trandao;
	/** randomNumber类实例 */
	private randomNumber rondom;
	/** storeDaoImp类实例 */
	private storeDaoImp strdaoimp;
	/** Mail类实例 */
	private Mail mail;
	/** 保存获取或者更新的cookie */
	private String cookie;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于初始化依赖类对象
	 * 
	 * @param sContext
	 *            ServletContext容器实例
	 */
	public void init(ServletContext sContext) {
		in = (classinitialization) sContext.getAttribute("in");
		condao = (consumerDao) in.getObject("consumerDao", "com.Itemhouse.Dao.consumerDao");
		emdao = (emailDao) in.getObject("emailDao", "com.Itemhouse.Dao.emailDao");
		cover = (converter) in.getObject("converter", "com.Itemhouse.Aid.converter");
		rondom = (randomNumber) in.getObject("randomNumber", "com.Itemhouse.Aid.randomNumber");
		strdao = (storeDao) in.getObject("storeDao", "com.Itemhouse.Dao.storeDao");
		ordao = (orderDao) in.getObject("orderDao", "com.Itemhouse.Dao.orderDao");
		comdao = (commentDao) in.getObject("commentDao", "com.Itemhouse.Dao.commentDao");
		strdaoimp = (storeDaoImp) in.getObject("storeDaoImp", "com.Itemhouse.DaoImp.storeDaoImp");
		buydao = (buycarDao) in.getObject("buycarDao", "com.Itemhouse.Dao.buycarDao");
		artdao = (articleDao) in.getObject("articleDao", "com.Itemhouse.Dao.articleDao");
		prodao = (produceDao) in.getObject("produceDao", "com.Itemhouse.Dao.produceDao");
		trandao = (transactionDao) in.getObject("transactionDao", "com.Itemhouse.Dao.transactionDao");
		mail = (Mail) in.getObject("Mail", "com.Itemhouse.Aid.Mail");
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用户注册
	 * 
	 * @param coumer
	 *            封装好的consumer对象
	 * @param mark
	 *            邮箱标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码20001 - 20002
	 */
	public int consumerRegister(consumer coumer, String mark, Connection con, HttpServletRequest request)
			throws Exception {
		int x = 0;
		x = condao.consumerAdd(coumer, con); // 客户注册
		if (x <= 0) {
			request.setAttribute("exption", "用户注册异常");
			throw new ItemExeption("用户注册异常", "20001");
		}
		x = emdao.emailAdd(new email("consumer", coumer.getIdentification(), mark), con);
		if (x <= 0) {
			request.setAttribute("exption", "添加email异常");
			throw new ItemExeption("添加email异常", "20002");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 设置已经通过验证的用户资料
	 * 
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码20003 - 20004
	 */
	public int UpdateConsumerInfo(String Identification, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		boolean flag = true;
		x = condao.consumerUpdateStatus(Identification, con); // 邮箱验证过后更改用户状态码
		if (x <= 0) {
			request.setAttribute("exption", "设置用户状态码异常");
			throw new ItemExeption("设置用户状态码异常", "20003");
		}
		String cookie = "";
		if (flag == true) { // 存在cookie
			for (int i = 0; i < 10; i++) { // 开始循环
				cookie = consumerUpdateCookie(); // 生成新的cookie
				flag = strdao.storeSelect_Cookie(cookie, con); // 查询是否继续存在
				if (flag == false) {
					flag = condao.consumerSelectCookieFlag(cookie, con); // 判断生成的cookie是否已经存在与客户表
					if (flag == false) {
						break; // 不存在直接跳出来
					}
				}
			}
		}
		x = condao.consumerUpdateCookie(cookie, Identification, con); // 设置客户的cookie
		if (x <= 0) {
			request.setAttribute("exption", "设置用户cookie异常");
			throw new ItemExeption("设置用户cookie异常", "20004");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询是否有存在的cookie
	 * 
	 * @param consumer_cookie
	 *            前端传递的cookie
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return consumer 封装好的consumer对象
	 * @throws Exception
	 *             状态码 20005
	 */
	public consumer consumerSelectCookieLogin(String consumer_cookie, Connection con, HttpServletRequest request)
			throws Exception {
		consumer couser = null;
		couser = condao.consumerSelectCookie(consumer_cookie, con); // 判断是否有相同的cookie
		if (couser == null) {
			request.setAttribute("exption", "cookie异常");
			throw new ItemExeption("cookie异常", "20005");
		}
		return couser;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用户登陆判断,并且每次都更新cookie
	 * 
	 * @param Identification
	 *            用户标识
	 * @param password
	 *            用户密码
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return consumer 封装好的consumer对象
	 * @throws Exception
	 *             状态码20006 - 20008
	 */
	public consumer consumerSelectLogin(String Identification, String password, Connection con,
			HttpServletRequest request) throws Exception {
		consumer couser = null;
		int x = condao.consumerSelect_status(Identification, con); // 判断用户的状态码是否为1,防止没通过邮箱验证的用户随便登陆
		if (x == 0) {
			request.setAttribute("exption", "用户名没有注册,请到邮箱注册");
			throw new ItemExeption("用户名没有注册,请到邮箱注册", "20006");
		}
		couser = condao.consumerSelect(Identification, password, con); // 返回对应的用户
		if (couser == null) {
			request.setAttribute("exption", "用户名为空或者密码异常");
			throw new ItemExeption("用户名为空或者密码异常", "20007");
		}
		boolean flag = true;
		String cookie = "";
		if (flag == true) { // 存在cookie
			for (int i = 0; i < 10; i++) { // 开始循环
				cookie = consumerUpdateCookie(); // 生成新的cookie
				flag = strdao.storeSelect_Cookie(cookie, con); // 查询是否继续存在
				if (flag == false) {
					flag = condao.consumerSelectCookieFlag(cookie, con); // 判断生成的cookie是否已经存在与客户表
					if (flag == false) {
						break; // 不存在直接跳出来
					}
				}
			}
		}
		x = condao.consumerUpdateCookie(cookie, Identification, con); // 更新用户cookie
		if (x <= 0) {
			request.setAttribute("exption", "登陆设置cookie异常");
			throw new ItemExeption("登陆设置cookie异常", "20008");
		}
		return couser;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 更改用户信息,不更新cookie,登陆更新cookie
	 * 
	 * @param name
	 *            用户呢称
	 * @param oldpassword
	 *            旧密码
	 * @param newpassword
	 *            新密码
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20009 - 20010
	 */
	public int consumerUpdateInfo(String name, String oldpassword, String newpassword, String Identification,
			Connection con, HttpServletRequest request) throws Exception {
		boolean flag = false;
		flag = condao.consumerSelect_password(Identification, oldpassword, con); // 判断输入的旧密码是否正确
		if (flag == false) {
			request.setAttribute("exption", "密码异常");
			throw new ItemExeption("密码异常", "20009");
		}
		int x = condao.consumerUpdate(new consumer(name, newpassword, Identification), con); // 设置新密码
		if (x <= 0) {
			request.setAttribute("exption", "更改信息异常");
			throw new ItemExeption("更改信息异常", "20010");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回用户的订单纪录
	 * 
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.order &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<order> consumerSelectorder(String Identification, Connection con, HttpServletRequest request)
			throws Exception {
		List<order> list = ordao.orderSelectConsumer(Identification, con); // 查询用户的所有订单纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回用户的评价纪录
	 * 
	 * @param Identification
	 *            用户标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.order &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<comment> consumerSelectCommentText(String Identification, Connection con, HttpServletRequest request)
			throws Exception {
		List<comment> list = comdao.commentSelectConsumer(Identification, con); // 查询用户所有订单的评论纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用户评价
	 * 
	 * @param com
	 *            封装好的comment对象
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20011 - 20013
	 */
	public int commentAdd(comment com, Connection con, HttpServletRequest request) throws Exception {
		int x = comdao.commentAdd(com, con); // 添加用户评论
		if (x <= 0) {
			request.setAttribute("exption", "用户评价异常");
			throw new ItemExeption("用户评价异常", "20011");
		}
		x = ordao.orderUpdateStatus(com.getOrder_id(), con); // 当用户评论之后,更改订单的状态码,表示订单完成
		if (x <= 0) {
			request.setAttribute("exption", "更改订单号状态码异常");
			throw new ItemExeption("更改订单号状态码异常", "20012");
		}
		x = trandao.transactionAdd(new transaction(com.getOrder_id(), 1, "交易成功"), con); // 当订单完成就代表交易完成,讲订单添加到交易表
		if (x <= 0) {
			request.setAttribute("exption", "添加交易类异常");
			throw new ItemExeption("添加交易类异常", "20013");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询客户某个物品的所有订单纪录
	 * 
	 * @param Identification
	 *            用户标识
	 * @param article
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.order &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码
	 */
	public List<order> consumerSelectorderList(String Identification, String article, Connection con,
			HttpServletRequest request) throws Exception {
		List<order> list = ordao.orderSelectConsumerSeriesList(Identification, article, con); // 查询客户某个物品的所有订单纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询客户某个物品的评价纪录
	 * 
	 * @param Identification
	 *            用户标识
	 * @param article
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.order &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<comment> consumerSelectCommentList(String Identification, String article, Connection con,
			HttpServletRequest request) throws Exception {
		List<comment> list = comdao.commentSelectConsumer(Identification, article, con); // 查询客户某个物品的评价纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 交易完成设置店铺的等级
	 * 
	 * @param satisfaction
	 *            满意度
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 ~
	 */
	public int setStoreGrade(int satisfaction, String store_name, Connection con, HttpServletRequest request)
			throws Exception {
		int x = strdaoimp.storeSetGarde(store_name, satisfaction, con, request); // 用户评论之后根据满意度更改店铺的等级
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 添加物品到购物车
	 * 
	 * @param car
	 *            封装好的buycar对象数组
	 * @param article_num
	 *            物品在线数量数组
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.I nteger 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20014 - 20016
	 */
	public int buycarAdd(buycar[] car, int[] article_num, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		boolean flag = false;
		for (int i = 0; i < car.length; i++) {
			buycar car2 = car[i];
			// 判断物品是否之前已经在购物车
			flag = buydao.buycarSelectArticle(car2.getIdentification(), car2.getStore_name(), car2.getArticle(), con);
			if (flag == true) { // 已经在购物车
				// 判断加入购物车的物品数量和已经在购物车的物品数量已经超过物品的库存,true代表没超过,false代表超过不能添加
				flag = buydao.buycarSelectArticleNum(car2.getIdentification(), car2.getStore_name(), car2.getArticle(),
						car2.getNum(), article_num[i], con);
				if (flag == false) { // 超过库存
					request.setAttribute("exption", "亲!购物车已经有物品,购买的物品数量不能超过库存,如需超量购买请到购物车通知商家生产对应的物品");
					throw new ItemExeption("亲!购物车已经有物品,购买的物品数量不能超过库存,如需超量购买请到购物车通知商家生产对应的物品", "20014");
				} else { // 不超过库存
					x = buydao.buycarUpdateNumAdd(car2.getIdentification(), car2.getStore_name(), car2.getArticle(),
							car2.getNum(), con);
					// 直接在原有的购物车纪录上加上本次要添加的物品数量
					if (x <= 0) {
						request.setAttribute("exption", "更改购物车物品数量异常");
						throw new ItemExeption("更改购物车物品数量异常", "20015");
					}
				}
			} else {// 表示物品没有被加入过购物车
				x = buydao.buycarAdd(car[i], con);
				// 直接添加购物车
				if (x <= 0) {
					request.setAttribute("exption", "添加购物车异常");
					throw new ItemExeption("添加购物车异常", "20016");
				}
			}
			flag = false; // 参数初始化
			x = 0; // 参数初始化
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询客户的购物车
	 * 
	 * @param Identification
	 *            客户标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.buycar &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.buycar &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<buycar> buycarSelectConsumer(String Identification, Connection con, HttpServletRequest request)
			throws Exception {
		List<buycar> list = buydao.buycarSelectConsumer(Identification, con); // 查询客户的购物车
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回所有店铺的所有物品
	 * 
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.article &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.article &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<article> articleSelectList(Connection con, HttpServletRequest request) throws Exception {
		List<article> list = artdao.articleSelect(con); // 客户购物,所以返回所有店铺的所有物品
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询所有店铺的同一个物品
	 * 
	 * @param article_name
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.article &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.article &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<article> articleSelecseriestList(String article_name, Connection con, HttpServletRequest request)
			throws Exception {
		List<article> list = artdao.articleSelectAll(article_name, con); // 查询所有店铺的同一个物品
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 购买物品
	 * 
	 * @param car
	 *            封装好的buycar对象数组
	 * @param place
	 *            订单地点数组
	 * @param car_id
	 *            购物车id数组
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20017 - 20022
	 */
	public int consumerBuyArticle(buycar[] car, String place[], int[] car_id, Connection con,
			HttpServletRequest request) throws Exception {
		int x = 0;
		for (int i = 0; i < car.length; i++) { // 循环购买
			buycar car2 = car[i];
			double total_price = getBuyMoney(car2.getNum(), car2.getMoney()); // 计算出购买物品要的金额
			x = ordao.orderAdd(new order(car2.getStore_name(), car2.getIdentification(), car2.getArticle(),
					car2.getNum(), total_price, place[i], new Date(), 1), con);
			// 添加订单纪录
			if (x <= 0) {
				request.setAttribute("exption", "添加订单异常");
				throw new ItemExeption("添加订单异常", "20017");
			}
			x = 0; // 初始化参数
			article atr = artdao.articleSelectOne(car2.getStore_name(), car2.getArticle(), con); // 查询商品的详细信息
			if (atr == null) { // 查询不到商品,可能被店铺下架了
				request.setAttribute("exption", "物品出现异常,可能被店铺下架,请确认是否有该物品");
				throw new ItemExeption("物品出现异常,可能被店铺下架,请确认是否有该物品", "20018");
			}
			int online_num = atr.getNum(); // 获取现在的物品在线数量
			if (car2.getNum() > online_num) { // 判断直到现在库存是不是还比购买的数量多,因为可能就在这一秒就被别的用户买走了
				request.setAttribute("exption", "物品已经没有这么多数量,请减少数量购买或者通知商家生产!");
				throw new ItemExeption("物品已经没有这么多数量,请减少数量购买或者通知商家生产!", "20019");
			}
			x = artdao.articleUpdateNumCutBack(atr.getId(), car2.getNum(), con); // 更改物品数量
			if (x <= 0) {
				request.setAttribute("exption", "减少物品数量异常");
				throw new ItemExeption("减少物品数量异常", "20020");
			}
			x = 0;
			x = strdao.setStoreMoneyAdd(total_price, car2.getStore_name(), con); // 更改店铺金额
			if (x <= 0) {
				request.setAttribute("exption", "添加金额异常");
				throw new ItemExeption("添加金额异常", "20021");
			}
			x = 0;
			x = buydao.buycarDelete(car_id[i], con); // 删除物品在购物车的纪录
			if (x <= 0) {
				request.setAttribute("exption", "删除购物车异常");
				throw new ItemExeption("删除购物车异常", "20022");
			}
			x = 0;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 删除购物车的商品
	 * 
	 * @param car_id
	 *            购物车纪录标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20023
	 */
	public int buycarDeleteList(int[] car_id, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		for (int i = 0; i < car_id.length; i++) { // 循环删除
			x = buydao.buycarDelete(car_id[i], con); // 删除购物车里面的物品纪录
			if (x <= 0) {
				request.setAttribute("exption", "删除购物车物品异常");
				throw new ItemExeption("删除购物车物品异常", "20023");
			}
			x = 0;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 更改物品数量
	 * 
	 * @param car_id
	 *            购物车标识
	 * @param num
	 *            要减少的数量
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20024
	 */
	public int buycarUpdateList(int[] car_id, int[] num, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		for (int i = 0; i < car_id.length; i++) { // 循环开始
			x = buydao.buycarUpdateNumCutBack(car_id[i], num[i], con); // 减少购物车物品的购买数量
			if (x <= 0) {
				request.setAttribute("exption", "更改购物车物品异常");
				throw new ItemExeption("更改购物车物品异常", "20024");
			}
			x = 0;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 通知店铺生产物品并购买
	 * 
	 * @param Identification
	 *            客户标识
	 * @param store_name
	 *            店铺名字数组
	 * @param article_name
	 *            物品名字数组
	 * @param buy_num
	 *            购买物品数量的数组
	 * @param place
	 *            订单地点数组
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 20025 - 20032
	 */
	public int producebuy(String Identification, String[] store_name, String[] article_name, int[] buy_num,
			String[] place, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		article art = null;
		buycar car = null;
		String email = "";
		for (int i = 0; i < store_name.length; i++) { // 循环购买
			art = artdao.articleSelectOne(store_name[i], article_name[i], con); // 查询是否有相对应的物品
			if (art == null) { // 没有对应物品
				request.setAttribute("exption", "确认商家是否有该物品或者该物品已经被下架");
				throw new ItemExeption("确认商家是否有该物品或者该物品已经被下架", "20025");
			}
			int article_num = art.getNum(); // 获取物品的库存
			double money = art.getMoney(); // 获取物品的名字
			if (buy_num[i] - article_num > 0) { // 判断购买的数量是否比库存大
				int different_num = buy_num[i] - article_num; // 获取差值
				double price = (art.getMoney() / 2) * different_num; // 计算生产物品需要的金额
				x = prodao.produceAdd(new produce(store_name[i], article_name[i], different_num, price, new Date()),
						con);
				// 开始生产,添加生产纪录
				if (x <= 0) {
					request.setAttribute("exption", "添加物品生产纪录异常!");
					throw new ItemExeption("添加物品生产纪录异常!", "20026");
				}
				x = 0;
				x = artdao.articleUpdateNumAdd(article_name[i], store_name[i], different_num, con); // 更改物品数量
				if (x <= 0) {
					request.setAttribute("exption", "添加物品数量异常!");
					throw new ItemExeption("添加物品数量异常!", "20027");
				}
				x = 0;
				x = strdao.setStoreMoneyCut_Back(price, store_name[i], con); // 生产之后更改店铺的金额
				if (x <= 0) {
					request.setAttribute("exption", "减少店铺金额异常!");
					throw new ItemExeption("减少店铺金额异常!", "20028");
				}
				x = 0;
				email = strdao.storeSelectEmail(store_name[i], con);
				mail.sendProduce(store_name[i], "", article_name[i]); // 邮箱通知店铺及时生产物品,第二个为发送邮件的邮箱地址
			}
			x = ordao.orderAdd(new order(store_name[i], Identification, article_name[i], buy_num[i],
					(money * buy_num[i]), place[i], new Date(), 1), con);
			// 添加订单纪录,初始化状态码为1
			if (x <= 0) {
				request.setAttribute("exption", "添加订单异常!");
				throw new ItemExeption("添加订单异常!", "20029");
			}
			x = 0;
			x = artdao.articleUpdateNumCutBack(art.getId(), buy_num[i], con); // 更改物品数量
			if (x <= 0) {
				request.setAttribute("exption", "减少物品数量异常!");
				throw new ItemExeption("减少物品数量异常!", "20030");
			}
			x = 0;
			x = strdao.setStoreMoneyAdd((money * buy_num[i]), store_name[i], con); // 更改店铺金额
			if (x <= 0) {
				request.setAttribute("exption", "添加店铺金额异常!");
				throw new ItemExeption("添加店铺金额异常!", "20031");
			}
			x = 0;
			car = buydao.buycarSelectOne(Identification, store_name[i], article_name[i], con);
			if (car != null) { // 因为当有购物车纪录返回才能删除,用户有可能是在没添加物品到购物车的情况下直接通知店铺生产,所以有可能没有纪录
				x = buydao.buycarDelete(car.getId(), con);
				if (x <= 0) {
					request.setAttribute("exption", "删除购物车纪录异常!");
					throw new ItemExeption("删除购物车纪录异常!", "20032");
				}
			}
			x = 0;
			email = "";
			art = null; // 初始化参数
			car = null;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 计算购买物品的价格
	 * 
	 * @param buy_num
	 *            物品数量
	 * @param article_money
	 *            物品单价
	 * @return double 返回计算好的物品总价
	 */
	public double getBuyMoney(int buy_num, double article_money) {
		double money = buy_num * article_money;
		return money;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取新的cookie
	 * 
	 * @return 返回单向加密的cookie
	 */
	public String consumerUpdateCookie() {
		String cook = rondom.getCookie(); // 获取新的cookie
		System.out.println(cook);
		cook = cover.SHAEncode(cook); // 进行单向加密
		System.out.println(cook);
		cookie = cook; // 将cookie赋值给变量,以便其他类获取
		return cook;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取用户标识
	 * 
	 * @param con
	 *            数据库Connection对象
	 * @return 返回用户标识
	 * @throws SQLException
	 *             状态码
	 */
	public String getConsumerIdentification(Connection con) throws SQLException {
		String Identification = "";
		for (int i = 0; i < 10; i++) {
			Identification = rondom.getConsumerIdentification();
			System.out.println(Identification);
			boolean flag = condao.consumerSelect_identification(Identification, con);
			if (flag == false) {
				return Identification;
			}
		}
		return Identification;
	}

	public String getConsumerMark(Connection con) throws SQLException {
		String mark = "";
		boolean flag = true;
		for (int i = 0; i < 10; i++) {
			mark = rondom.getMark(); // 获取新的mark
			mark = cover.SHAEncode(mark); // 单向加密
			flag = emdao.emailSelcet_mark(mark, con); // 查询是否有相同的mark
			if (flag == false) { // 没有就跳出
				return mark;
			}
		}
		return mark;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
