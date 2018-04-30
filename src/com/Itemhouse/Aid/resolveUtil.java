package com.Itemhouse.Aid;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;处理获取的前台传递cookie
 */
public class resolveUtil {

	private converter cover;

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取最后登陆的用户cookie
	 * 
	 * @param cook
	 *            从前端获取的cookie数组
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return Cookie 最后登陆的用户cookie
	 * @throws ItemExeption
	 *             自定义异常
	 */
	public Cookie getCookie(Cookie[] cook, HttpServletRequest request) throws ItemExeption {
		Cookie cookie = null;
		long cookie_time = 0;
		for (Cookie e : cook) {
			if (!e.getName().equals("JSESSIONID")) {
				String context = e.getValue(); // 获取cookie的值
				String text[] = context.split("\\^"); // 进行分割
				if (text[0] == null || text[0].equals("0")) { // 分割后字符串为空
					request.setAttribute("exption", "cookie时间异常!");
					throw new ItemExeption("获取的cookie出现异常", "100");
				}
				long date = Long.parseLong(text[0]); // 将之前存的时间拿出来比较
				if (date > cookie_time) { // 循环找出时间最大的一个(即使最后登录的用户)
					cookie_time = date;
					cookie = e;
				}
			}
		}
		return cookie;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 每次用户登陆都把其余的cookie删了
	 * 
	 * @param cook
	 *            从前台获取到的cookie数组
	 * @param response
	 *            HttpServletResponse对象 删除cookie
	 */
	public void remvoeCookies(Cookie[] cook, HttpServletResponse response) {
		for (Cookie e : cook) {
			e.setMaxAge(0); // 删除cookie
			e.setPath("/ItemWarehouse/servlet/loginServlet");
			response.addCookie(e); // 把cookie写进response返回给前端
		}
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 每次店铺登陆都把其余的cookie删了
	 * 
	 * @param cook
	 *            从前台获取到的cookie数组
	 * @param response
	 *            HttpServletResponse对象 删除cookie
	 */
	public void removeStoreCookie(Cookie[] cook, HttpServletResponse response) {
		for (Cookie e : cook) {
			e.setMaxAge(0); // 删除cookie
			e.setPath("/ItemWarehouse/servlet/loginServlet");
			response.addCookie(e); // 把cookie写进response返回给前端
		}
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 将获取到的cookie进行解密
	 * 
	 * @param cook
	 *            获取的cookie数组
	 * @param request
	 *            HttpServletRequest对象 存储异常信息
	 * @return java.lang.String 解析后返回的cookie
	 * @throws Exception
	 *             解析异常
	 */
	public String getCookieResolve(Cookie[] cook, HttpServletRequest request) throws Exception {
		String cookie_name = "";
		Cookie cookie = getCookie(cook, request); // 获取cookie
		if (cookie != null) {
			String context = cookie.getValue();
			String cok[] = context.split("\\^");
			if (cok[1] == null || cok[1].equals("")) {
				request.setAttribute("exption", "解析cookie异常!");
				throw new ItemExeption("解析cookie异常", "200");
			}
			cookie_name = cover.getDecryption(cok[1]); // 解析获取的cookie值
		}
		return cookie_name;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 将生成的mark进行加密
	 * 
	 * @param mark
	 *            生成的mark
	 * @return java.lang.String 返回加密的mark
	 * @throws Exception
	 *             加密异常
	 */
	public String getEncryptionMark(String mark) throws Exception {
		mark = cover.getEncryption(mark);
		return mark;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 将生成的mark进行解密
	 * 
	 * @param mark
	 *            获取的mark
	 * @return java.lang.String 返回解密的mark
	 * @throws Exception
	 *             解密异常
	 */
	public String getDecryptionMark(String mark) throws Exception {
		mark = cover.getEncryption(mark);
		return mark;
	}

	public void init(ServletContext sContext) {
		classinitialization in = (classinitialization) sContext.getAttribute("in");
		cover = (converter) in.getObject("converter", "com.Itemhouse.Aid.converter");
	}

}
