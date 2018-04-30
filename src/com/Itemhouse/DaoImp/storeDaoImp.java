package com.Itemhouse.DaoImp;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.Itemhouse.Aid.ItemExeption;
import com.Itemhouse.Aid.classinitialization;
import com.Itemhouse.Aid.converter;
import com.Itemhouse.Aid.randomNumber;
import com.Itemhouse.Dao.articleDao;
import com.Itemhouse.Dao.commentDao;
import com.Itemhouse.Dao.consumerDao;
import com.Itemhouse.Dao.emailDao;
import com.Itemhouse.Dao.orderDao;
import com.Itemhouse.Dao.produceDao;
import com.Itemhouse.Dao.storeDao;
import com.Itemhouse.eity.article;
import com.Itemhouse.eity.comment;
import com.Itemhouse.eity.email;
import com.Itemhouse.eity.order;
import com.Itemhouse.eity.produce;
import com.Itemhouse.eity.store;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;对storeDao的功能进行链接
 */
public class storeDaoImp {
	/** storeDao类实例 */
	private storeDao strdao;
	/** classinitialization类实例 */
	private classinitialization in;
	/** emailDao类实例 */
	private emailDao emaildao;
	/** converter类实例 */
	private converter cover;
	/** articleDao类实例 */
	private articleDao artdao;
	/** orderDao类实例 */
	private orderDao ordao;
	/** produceDao类实例 */
	private produceDao prodao;
	/** consumerDao类实例 */
	private consumerDao condao;
	/** commentDao类实例 */
	private commentDao comdao;
	/** randomNumber类实例 */
	private randomNumber rondom;
	/** 保存获取或者更新的cookie */
	private String cookie;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于依赖类对象初始化
	 * 
	 * @param sContext
	 *            ServletContext容器实例
	 */
	public void init(ServletContext sContext) {
		in = (classinitialization) sContext.getAttribute("in");
		strdao = (storeDao) in.getObject("storeDao", "com.Itemhouse.Dao.storeDao");
		emaildao = (emailDao) in.getObject("emailDao", "com.Itemhouse.Dao.emailDao");
		cover = (converter) in.getObject("converter", "com.Itemhouse.Aid.converter");
		rondom = (randomNumber) in.getObject("randomNumber", "com.Itemhouse.Aid.randomNumber");
		artdao = (articleDao) in.getObject("articleDao", "com.Itemhouse.Dao.articleDao");
		ordao = (orderDao) in.getObject("orderDao", "com.Itemhouse.Dao.orderDao");
		prodao = (produceDao) in.getObject("produceDao", "com.Itemhouse.Dao.produceDao");
		condao = (consumerDao) in.getObject("consumerDao", "com.Itemhouse.Dao.consumerDao");
		comdao = (commentDao) in.getObject("commentDao", "com.Itemhouse.Dao.commentDao");
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于店铺注册
	 * 
	 * @param str
	 *            传进来的店铺对象
	 * @param mark
	 *            邮箱注册标识
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10001 - 10006
	 */
	public int storeRegister(store str, String mark, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		boolean flag = strdao.storeSelectStore_name(str.getName(), con); // 查询是否有相同的店铺的名字
		if (flag == true) { // 有相同的店铺名字
			flag = strdao.storeSelectStore_status(str.getName(), con); // 查看状态码是否为激活状态,1激活,0没激活
			if (flag == true) { // 状态码激活状态
				request.setAttribute("exption", "店铺已经被注册");
				throw new ItemExeption("店铺已经被注册!", "10001");
			} else { // 状态码没激活状态
				flag = strdao.storeSelectStore_date(str.getDate(), str.getName(), con);
				// 查看店铺名字上次被注册的时间,超过两分钟可以让其他人注册店铺名字,没超过其他人不能使用这个店铺名字注册
				if (flag == false) { // 时间没超过两分钟
					request.setAttribute("exption", "店铺名字已经被使用");
					throw new ItemExeption("店铺名字已经被使用", "10002");
				} else { // 时间超过两分钟,释放注册权限
					x = strdao.setStore(str, con); // 将通过注册的客户资料覆盖前一个注册的客户资料
					if (x <= 0) {
						request.setAttribute("exption", "注册覆盖店铺资料异常");
						throw new ItemExeption("注册覆盖店铺资料异常", "10003");
					}
					x = emaildao.emailUpdateMark(new email("store", str.getName(), mark), con); // 覆盖之前注册的mark
					if (x <= 0) {
						request.setAttribute("exption", "覆盖email_mark异常");
						throw new ItemExeption("覆盖email_mark异常", "10004");
					}
					return x;
				}
			}
		}
		// 没有相同的店铺名,直接进行注册
		x = emaildao.emailAdd(new email("store", str.getName(), mark), con); // 先添加email表的纪录
		if (x <= 0) {
			request.setAttribute("exption", "添加email操作异常");
			throw new ItemExeption("添加email操作异常", "10005");
		}
		x = strdao.storeAdd(str, con); // 添加店铺的资料
		if (x <= 0) {
			request.setAttribute("exption", "添加店铺操作异常");
			throw new ItemExeption("添加店铺操作异常", "10006");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于初始化邮箱验证店铺的金额,等级,cookie和更改状态码
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10007 - 10010
	 */
	public int storePerfectInfo(String store_name, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		boolean flag = strdao.storeSelectStore_status(store_name, con);
		if (flag == true) {
			request.setAttribute("exption", "店铺已经注册成功,请不要重复注册,导致没必要的错误!");
			throw new ItemExeption("店铺已经被初始化", "10007");
		}
		x = strdao.setStoreStatus(1, store_name, con); // 把验证成功的店铺状态码设置为激活状态
		if (x <= 0) {
			request.setAttribute("exption", "更改店铺的状态码异常!");
			throw new ItemExeption("更改店铺的状态码异常", "10008");
		}
		x = strdao.setStoreGradeAndMoney(store_name, con); // 初始化店铺的金额和等级
		if (x <= 0) {
			request.setAttribute("exption", "设置店铺金钱和等级异常!");
			throw new ItemExeption("设置店铺金钱和等级异常", "10009");
		}
		flag = true;
		String cookie = "";
		if (flag == true) { // 存在cookie
			for (int i = 0; i < 10; i++) { // 开始循环
				cookie = storeUpdateCookie(); // 生成新的cookie
				flag = strdao.storeSelect_Cookie(cookie, con); // 判断生成的cookie是否已经存在店铺表
				if (flag == false) {
					flag = condao.consumerSelectCookieFlag(cookie, con); // 判断生成的cookie是否已经存在与客户表
					if (flag == false) {
						break; // 不存在直接跳出来
					}

				}
			}
		}
		x = strdao.setStoreCooike(cookie, store_name, con); // 设置店铺的cookie
		if (x <= 0) {
			request.setAttribute("exption", "设置店铺cookie异常!");
			throw new ItemExeption("设置店铺cookie异常", "10010");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 根据前台传过来的cookie去找到相对应的店铺
	 * 
	 * @param store_cookie
	 *            获取的cookie
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return com.Itemhouse.eity.store 返回数据库查询到的纪录,并封装到store类的实例属性中
	 * @throws Exception
	 *             系统异常 10011
	 */
	public store stroeCooikeLogin(String store_cookie, Connection con, HttpServletRequest request) throws Exception {
		store str = null;
		str = strdao.storeSelectCookie(store_cookie, con); // 查询是否有相同的cookie,并返回查询到店铺对象
		if (str == null) {
			request.setAttribute("exption", "请登陆!");
			throw new ItemExeption("store_cookie失效,请重新登陆", "10011");
		}
		return str;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 根据店铺名字和密码登陆,每次登陆完都设置一个新的长登陆cookie
	 * 
	 * @param password
	 *            密码
	 * @param store_name
	 *            店铺名
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return com.Itemhouse.eity.store 返回数据库查询到的纪录,并封装到store类的实例属性中
	 * @throws Exception
	 *             系统异常 10012 - 10014
	 */
	public store stroeLogin(String password, String store_name, Connection con, HttpServletRequest request)
			throws Exception {
		store str = null;
		boolean flag = true;
		int x = strdao.storeSelectFlag(store_name, password, con); // 查询店铺的status码
		if (x == 0) { // 0代表没经过邮箱验证,不能登陆
			request.setAttribute("exption", "店铺没被激活,请前往邮箱激活!");
			throw new ItemExeption("店铺没被激活,请前往邮箱激活", "10012");
		}
		str = strdao.storeSelect(store_name, password, con); // 查询账号密码是否正确,并返回对应的店铺对象
		if (str == null) {
			request.setAttribute("exption", "店铺名字为空或者密码错误!");
			throw new ItemExeption("店铺名字或者密码错误,请重新登陆", "10013");
		}
		String cookie = "";
		if (flag == true) { // 存在cookie
			for (int i = 0; i < 10; i++) { // 开始循环
				cookie = storeUpdateCookie(); // 生成新的cookie
				flag = strdao.storeSelect_Cookie(cookie, con); // 判断生成的cookie是否已经存在店铺表
				if (flag == false) {
					flag = condao.consumerSelectCookieFlag(cookie, con); // 判断生成的cookie是否已经存在与客户表
					if (flag == false) {
						break; // 不存在直接跳出来
					}

				}
			}
		}
		x = strdao.setStoreCooike(cookie, store_name, con); // 设置新的cookie
		if (x <= 0) {
			request.setAttribute("exption", "登陆更新cookie失败!");
			throw new ItemExeption("更新store_cookie失败,系统异常", "10014");
		}
		return str;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于完成订单的时候更改金额
	 * 
	 * @param stroe_name
	 *            店铺名字
	 * @param money
	 *            增加的金额
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10015
	 */
	public int stroeOrderCompleted(String stroe_name, double money, Connection con, HttpServletRequest request)
			throws Exception {
		int x = 0;
		x = strdao.setStoreMoneyAdd(money, stroe_name, con); // 更改店铺的金额
		if (x <= 0) {
			request.setAttribute("exption", "更新store_money失败,系统异常");
			throw new ItemExeption("更新store_money失败,系统异常", "10015");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于客户每次评价后更改店铺等级
	 * 
	 * @param store_name
	 *            店铺名
	 * @param grade
	 *            更改等级的标识,1代表好评,0代表差评
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10016 - 10018
	 */
	public int storeSetGarde(String store_name, int grade, Connection con, HttpServletRequest request)
			throws Exception {
		int x = 0;
		x = strdao.storeSelectStore_grade(store_name, con); // 查询店铺的等级大小
		if (x == -1) { // -1代表查询异常,没有返回数据
			request.setAttribute("exption", "查询店铺等级异常,系统异常");
			throw new ItemExeption("查询店铺等级异常", "10016");
		}
		if (grade > 0) { // 评价是好评
			if (x < 8) { // 店铺的等级小于8
				int i = strdao.setStoreGradeAdd(store_name, con); // 将店铺等级加1
				if (i <= 0) {
					request.setAttribute("exption", "添加店铺等级异常,系统异常");
					throw new ItemExeption("添加店铺等级异常", "10017");
				}
				x = i; // 防止返回-1,将i赋值给x
			}
		} else { // 评价是差评
			if (x > 0) { // 店铺的等级大于0
				int i = strdao.setStoreGradeCut_Back(store_name, con); // 将店铺等级减1
				if (i <= 0) {
					request.setAttribute("exption", "减少店铺等级异常,系统异常");
					throw new ItemExeption("减少店铺等级异常", "10018");
				}
				x = i; // 防止返回-1,将i赋值给x
			}
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于更改店铺的密码,同时更新cookie
	 * 
	 * @param oldpassword
	 *            旧密码
	 * @param newpassword
	 *            新密码
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10019 - 10020
	 */
	public int storeUpdateInfo(String oldpassword, String newpassword, String store_name, Connection con,
			HttpServletRequest request) throws Exception {
		int x = 0;
		boolean flag = strdao.storeSelectPassword(store_name, oldpassword, con); // 查看输入的旧密码是否正确
		if (flag == false) {
			request.setAttribute("exption", "密码错误,重新输入密码!");
			throw new ItemExeption("密码错误!", "10019");
		}
		x = strdao.storeUpdated(newpassword, store_name, con); // 更改密码
		if (x <= 0) {
			request.setAttribute("exption", "更改密码异常!");
			throw new ItemExeption("更改密码异常", "10020");
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 用于查询店铺的所有article
	 * 
	 * @param store_name
	 *            店铺名
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.article &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.article &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<article> getSelectArticleList(String store_name, Connection con, HttpServletRequest request)
			throws Exception {
		List<article> list = artdao.articleSelecStore_nametAll(store_name, con); // 查询店铺的所有物品
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 添加物品
	 * 
	 * @param article_name
	 *            物品名字
	 * @param article_money
	 *            物品金额
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10021 - 10022
	 */
	public int addArticleList(String[] article_name, double[] article_money, String store_name, Connection con,
			HttpServletRequest request) throws Exception {
		int x = 0;
		List<article> list = artdao.articleSelecStore_nametAll(store_name, con); // 查询当前店铺的所有物品
		for (int i = 0; i < list.size(); i++) { // 循环List
			article atr = list.get(i); // 拿到封装的article对象
			String name = atr.getArticle(); // 获取到物品的名字
			for (int o = 0; o < article_name.length; o++) { // 循环添加的物品名称数组
				if (name.equals(article_name[o])) { // 判断是否有相同的物品名称
					request.setAttribute("exption", "不要添加重复的物品");
					throw new ItemExeption("不要添加重复的物品", "10021");
				}
			}
		}
		for (int i = 0; i < article_name.length; i++) { // 开始循环添加物品操作
			x = artdao.articleAdd(new article(store_name, article_name[i], article_money[i], 0), con); // 进行物品添加
			if (x <= 0) {
				request.setAttribute("exption", "添加商品失败");
				throw new ItemExeption("添加商品失败", "10022");
			}
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 批量删除article
	 * 
	 * @param articleid
	 *            删除的article id数组
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10023
	 */
	public int deleteArticleList(int[] articleid, Connection con, HttpServletRequest request) throws Exception {
		int x = 0;
		for (int i = 0; i < articleid.length; i++) { // 开始循环articleid数组
			x = artdao.articleDelete(articleid[i], con); // 删除物品
			if (x <= 0) {
				request.setAttribute("exption", "删除物品异常");
				throw new ItemExeption("删除物品异常", "10023");
			}
			x = 0;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 批量更改article
	 * 
	 * @param articleid
	 *            article标识
	 * @param articleName
	 *            物品名字
	 * @param money
	 *            物品金钱
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10024
	 */
	public int UpdateArticleList(int[] articleid, String[] articleName, double[] money, Connection con,
			HttpServletRequest request) throws Exception {
		int x = 0;
		for (int i = 0; i < articleid.length; i++) { // 开始循环更改物品
			x = artdao.articleUpdate(articleid[i], articleName[i], money[i], con); // 根据物品id更改物品信息
			if (x <= 0) {
				request.setAttribute("exption", "更改物品异常");
				throw new ItemExeption("更改物品异常", "10024");
			}
			x = 0;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询所有的订单
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.order &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<order> getSelectOrderList(String store_name, Connection con, HttpServletRequest request)
			throws Exception {
		List<order> list = ordao.orderSelectStore(store_name, con); // 查询店铺所有的订单
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的订单评论纪录
	 * 
	 * @param store_name
	 *            店铺
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.comment &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.comment &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<comment> getStoreCommentList(String store_name, Connection con, HttpServletRequest request)
			throws Exception {
		List<comment> list = comdao.commentSelectStore(store_name, con); // 查询店铺的订单评论纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺某个物品的所有评论
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.comment &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.comment &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<comment> getStoreSeriesCommentList(String store_name, String article, Connection con,
			HttpServletRequest request) throws Exception {
		List<comment> list = comdao.commentSelectStore(store_name, article, con); // 查询店铺某个物品的所有评论
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 返回店铺的一个物品的所有订单
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.order &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.order &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<order> getSelectSeriesList(String store_name, String article_name, Connection con,
			HttpServletRequest request) throws Exception {
		List<order> list = ordao.orderSelectSeriesOrder(store_name, article_name, con); // 查询店铺一个物品的所有订单
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 生产物品
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名字
	 * @param article_num
	 *            物品数量
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.Integer 返回数据库受影响的行数
	 * @throws Exception
	 *             状态码 10025 - 10027
	 */
	public int addProduceList(String store_name, String[] article_name, int[] article_num, Connection con,
			HttpServletRequest request) throws Exception {
		int x = 0;
		double[] money = artdao.articleSelectarticle_moneys(article_num, store_name, article_name, con); // 获取到生产物品的金额数组
		boolean flag = addProduceListFlag(store_name, money, con, request); // 进行判断能否生产
		for (int i = 0; i < article_name.length; i++) { // 开始循环生产
			x = artdao.articleUpdateNumAdd(article_name[i], store_name, article_num[i], con); // 更改物品的数量
			if (x <= 0) {
				request.setAttribute("exption", "找不到生产的商品");
				throw new ItemExeption("找不到生产的商品", "10025");
			}
			x = strdao.setStoreMoneyCut_Back(money[i], store_name, con); // 更改店铺的金额
			if (x <= 0) {
				request.setAttribute("exption", "更改店铺金额异常");
				throw new ItemExeption("更改店铺金额异常", "10026");
			}
			x = prodao.produceAdd(new produce(store_name, article_name[i], article_num[i], money[i], new Date()), con); // 添加生产纪录
			if (x <= 0) {
				request.setAttribute("exption", "添加生产纪录错误");
				throw new ItemExeption("添加生产纪录错误", "10027");
			}
			x = 0;
		}
		return x;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 判断是否有足够的金额生产
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_money
	 *            生产的钱
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return boolean true代表可以生产,false报错
	 * @throws Exception
	 *             状态码 10028
	 */
	public boolean addProduceListFlag(String store_name, double[] article_money, Connection con,
			HttpServletRequest request) throws Exception {
		double money = getProduceMoney(article_money); // 获取总金额
		boolean flag = strdao.storeSelectStore_moey(store_name, money, con); // 查询判断是否有足够金额生产
		if (flag == false) {
			request.setAttribute("exption", "余额不足,不能生产");
			throw new ItemExeption("余额不足,不能生产", "10028");
		}
		return flag;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的所有生产纪录
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.produce &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.produce &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<produce> selectProdceList(String store_name, Connection con, HttpServletRequest request)
			throws Exception {
		List<produce> list = prodao.produceSelectStore(store_name, con); // 查询店铺的所有生产纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 查询店铺的某个物品生产纪录
	 * 
	 * @param store_name
	 *            店铺名字
	 * @param article_name
	 *            物品名称
	 * @param con
	 *            数据库Connection对象
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.util.List &lt; com.Itemhouse.eity.produce &gt;
	 *         返回数据库查询到的纪录,循环纪录并封装到List &lt; com.Itemhouse.eity.produce &gt;的容器中
	 * @throws Exception
	 *             状态码 ~
	 */
	public List<produce> getSelectSeriesProduceList(String store_name, String article_name, Connection con,
			HttpServletRequest request) throws Exception {
		List<produce> list = prodao.produceSelectStore_Article(store_name, article_name, con); // 查询店铺的某个物品生产纪录
		return list;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取cookie
	 * 
	 * @return 返回新的cookie
	 */
	public String storeUpdateCookie() {
		String cook = rondom.getCookie(); // 获取新的cookie
		System.out.println(cook);
		cook = cover.SHAEncode(cook); // 进行单向加密
		System.out.println(cook);
		cookie = cook; // 将cookie赋值给变量,以便其他类获取
		return cook;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取邮箱注册标识
	 * 
	 * @param con
	 *            数据库Connection对象
	 * @return 返回单向加密的mark
	 * @throws Exception
	 *             状态码
	 */
	public String getStoreMark(Connection con) throws Exception {
		String mark = "";
		for (int i = 0; i < 10; i++) {
			mark = rondom.getMark(); // 查询是否有相同的mark
			mark = cover.SHAEncode(mark); // 单向加密
			boolean flag = emaildao.emailSelcet_mark(mark, con); // 再次判断
			if (flag == false) { // 没有就跳出
				return mark;
			}
		}
		return mark;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 计算生产需要的金额
	 * 
	 * @param money
	 *            生产物品的金钱
	 * @return 生产总额
	 */
	public double getProduceMoney(double[] money) {
		double produce_money = 0;
		for (int i = 0; i < money.length; i++) { // 循环生产物品的金额价格
			produce_money += (money[i] / 2); // 计算总金额
		}
		return produce_money; // 返回总金额
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
