package com.Itemhouse.Aid;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author &nbsp; &nbsp;Lwc
 * @since &nbsp; &nbsp;1.6
 * @version &nbsp; &nbsp;1.8<br>
 *          <strong>类说明:</strong> <br>
 *          用于处理异常消息
 */
public class ItemExeption extends Exception {
	/**
	 * <strong>方法说明:</strong> <br>
	 * 默认构造器+
	 * 
	 */
	public ItemExeption() {
		super();
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 自定义异常信息构造器
	 * 
	 * @param s
	 *            自定义的异常码或者异常标记
	 */
	public ItemExeption(String s) {
		super(s);
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 自定义异常信息构造器
	 * 
	 * @param info
	 *            异常信息
	 * @param s
	 *            状态码
	 */
	public ItemExeption(String info, String s) {
		super("异常信息" + " " + info + "状态码:" + s);
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取详细的异常信息
	 * 
	 * @param e
	 *            Exception的子类或者Exception类型
	 * @param s
	 *            自定义的类型信息
	 * @return java.lang.String 返回异常发生的文件,具体所在类,方法,原因,所在行数
	 */
	public static String getInfo(Exception e, String s) {
		s = "发生异常的原因: " + getStackTraceInfo(e);
		s += "发生异常的类文件:  " + e.getStackTrace()[0].getFileName() + "\n";
		s += "发生异常的类:  " + e.getStackTrace()[0].getClassName() + "\n";
		s += "发生异常的方法:  " + e.getStackTrace()[0].getMethodName() + "\n";
		s += "发生在类的第行:  " + e.getStackTrace()[0].getLineNumber() + "行 \n";
		System.out.println(s);
		return s;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 将获取的异常信息写进一个字符串里面返回
	 * 
	 * @param e
	 *            Exception的子类或者Exception类型
	 * @return java.lang.String 返回一个异常信息字符串
	 */
	public static String getStackTraceInfo(Exception e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);// 将出错的栈信息输出到printWriter中
			pw.flush();
			sw.flush();
			return sw.toString();
		} catch (Exception ex) {
			return "发生错误";
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
	}
}
