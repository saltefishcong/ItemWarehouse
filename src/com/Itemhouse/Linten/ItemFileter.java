package com.Itemhouse.Linten;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 */
public class ItemFileter implements Filter {

	/** 定义编码变量 */
	private String encoding;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("ItemFileter  destroy()" + new Date());
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpServletRequest request = (HttpServletRequest) arg0;
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 设置允许跨域访问的方法
		response.setHeader("Access-Control-Allow-Methods", "POST, GET,OPTIONS, DELETE");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("ItemFileter  init()" + new Date());
		this.encoding = arg0.getInitParameter("encoding");
	}

}
