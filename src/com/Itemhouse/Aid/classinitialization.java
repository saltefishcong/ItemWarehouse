package com.Itemhouse.Aid;

import java.util.HashMap;
import java.util.Map;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;用于resolveXml解析后的类对象创建
 */
public class classinitialization {
	/** 创建一个final的resolveXml对象,无法被修改 */
	private static final resolveXml ts = new resolveXml();
	/** 用来存放初始化对象后的容器 */
	private static Map<String, Object> map = new HashMap<String, Object>();

	public void startclassinitialization() {
		try {
			createObjects();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("初始化失败");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("初始化失败");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("初始化失败");
		}
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 创建指定路径的类实例,实例名字为传进去的Name
	 * 
	 * @param objectName
	 *            传进去的实例名字
	 * @param path
	 *            指定的类路径
	 * @return Object 返回创建的对象
	 * @throws ClassNotFoundException
	 *             找不到类
	 * @throws InstantiationException
	 *             实例化异常
	 * @throws IllegalAccessException
	 *             非法访问实例
	 */
	public Object createObject(String objectName, String path)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class zz = Class.forName(path);
		Object object = zz.newInstance();
		return object;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取传进去的类是否是一个单例类
	 * 
	 * @param path
	 *            类路径
	 * @return boolean true单例类,false不是单例类,默认值
	 */
	public boolean getClassSingle(String path) {
		for (int i = 0; i < ts.classTypePaths.size(); i++) {
			Map<String, String> map = ts.classTypePaths.get(i);
			if (map.get("path").equals(path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取单例类的第一个对象
	 * 
	 * @param path
	 *            类路径
	 * @return java.lang.String 返回类对象的名字
	 */
	public String getSingleObject(String path) {
		return ts.location.get(path);
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 初始化所有类对象
	 * 
	 * @return java.util.Map&lt;java.lang.String, Object&gt; 返回创建好的对象集合Map
	 * @throws ClassNotFoundException
	 *             找不到类
	 * @throws InstantiationException
	 *             实例化异常
	 * @throws IllegalAccessException
	 *             非法访问实例
	 */
	public Map<String, Object> createObjects()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		for (int i = 0; i < ts.objectNames.size(); i++) {
			String objectName = ts.objectNames.get(i);
			String path = ts.objectPaths.get(objectName);
			boolean flag = getClassSingle(path);
			if (flag == true) {
				if (map.get(objectName) == null) {
					Object object = createObject(objectName, path);
					map.put(objectName, object);
					System.out.println(objectName + "  " + path + " " + object);
					continue;
				} else {
					continue;
				}
			}
			Object object = createObject(objectName, path);
			System.out.println(object);
			map.put(objectName, object);
		}
		return map;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取xml定义的对象,单例的获取设置的对象,多例获取不一样的对象
	 * 
	 * @param objectName
	 *            获取的对象名字
	 * @param path
	 *            获取对象的路径
	 * @return Object 返回的对象
	 */
	public Object getObject(String objectName, String path) {
		boolean flag = getClassSingle(path);
		if (flag == true) {
			Object object = map.get(objectName);
			return object;
		}
		if (map.get(objectName) != null) {
			Object object2 = map.get(objectName);
			Object object = null;
			try {
				object = createObject(objectName, path);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(objectName, object);
			return object2;
		}
		return null;
	}
}
