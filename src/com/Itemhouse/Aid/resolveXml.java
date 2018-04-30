package com.Itemhouse.Aid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;用于解析xml并在web应用启动的时候初始化
 */
public class resolveXml {
	/** 纪录类对象对应的路径 */
	Map<String, String> objectPaths = new HashMap<String, String>();
	/** 纪录一个class的type */
	Map<String, String> classTypePath = new HashMap<String, String>();
	/** 纪录所有单例类的路径 */
	List<Map<String, String>> classTypePaths = new ArrayList<Map<String, String>>();
	/** 纪录类对象名字 */
	List<String> objectNames = new ArrayList<String>();
	/** 纪录类对象对应的所有属性 */
	Map<String, ArrayList<HashMap<String, String>>> attribele = new HashMap<String, ArrayList<HashMap<String, String>>>();
	/** 纪录一个class子标签的属性 */
	Map<String, String> map = new HashMap<String, String>();
	/** 纪录整个class标签放进数组里面 */
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	/** 用于纪录对象是不是单例,但是这个单例属性应该是类级的,所以设计的不好,以后重写. */
	List<Map<String, String>> types = new ArrayList<Map<String, String>>();
	/** 用于纪录一个对象是不是单例,但是这个单例属性应该是类级的,所以设计的不好,以后重写. */
	Map<String, String> typeMap = new HashMap<String, String>();
	/** 纪录单例类的第一个对象 */
	Map<String, String> location = new HashMap<String, String>();
	/** 纪录类路径 */
	String path;

	public void lwc() {
		SAXReader reader = new SAXReader();
		String objectName = "";
		try {
			// 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
			Document document = reader.read(new File(
					"C:/Users/LIUweicong/Workspaces/MyEclipse 2016 CI/ItemWarehouse/WebRoot/WEB-INF/classes/lwc.xml"));
			// 通过document对象获取根节点lwc
			Element bookStore = document.getRootElement();
			// 通过element对象的elementIterator方法获取迭代器
			Iterator it = bookStore.elementIterator();
			// 遍历迭代器，获取根节点中的信息
			while (it.hasNext()) {
				// System.out.println("=====开始遍历某一个标签=====");
				Element book = (Element) it.next();
				// 获取class的属性名以及 属性值
				List<Attribute> bookAttrs = book.attributes();
				objectName = setpath(bookAttrs);
				Iterator itt = book.elementIterator();
				while (itt.hasNext()) {
					Element bookChild = (Element) itt.next();
					List<Attribute> bookAttrs2 = bookChild.attributes();
					for (Attribute attr : bookAttrs2) {
						map = setZiBianQian(attr);
						// 每一个便签属性都有一个map.因为每个便签都有属性name和value,不分开后一个会把前一个属性覆盖
						typeMap = setTypes(attr);
						// 如果是type便签的话,就直接把type属性和值放在typeMap里面
						classTypePath = setTyePath(path, attr); // 将单例类的路径添加到classTypePath
					}
					if (!map.isEmpty()) {
						list.add((HashMap<String, String>) map); // 判断为空便签里面的type属性已经移到type里面,所以会有一个空的属性map
					}
					map = new HashMap<String, String>(); // 重置map的原因是,hashMap操作的是指针,后面的同名属性会直接去内存覆盖前面的属性值.
				}
				if (!classTypePath.isEmpty()) {
					classTypePaths.add(classTypePath); // 将classTypePath添加到classTypePaths里面
					classTypePath = new HashMap<String, String>(); // 重置classTypePath,,hashMap操作的是指针,后面的同名属性会直接去内存覆盖前面的属性值.
				}
				typeMap.put("name", objectName); // 将添加本次type的对象名字
				// typeMap.put("path", path);
				setLocation(path, objectName);
				types.add(typeMap); // 将一个class便签的type放到types里面
				typeMap = new HashMap<String, String>(); // 重置typeMap的原因是,hashMap操作的是指针,后面的同名属性会直接去内存覆盖前面的属性值.
				objectPaths.put(objectName, path); // 添加创建的对象的路径和名字
				attribele.put(objectName, list); // 添加一个属性数组
				objectNames.add(objectName); // 将对象名字添加到名字数组
				list = new ArrayList<HashMap<String, String>>();
				// 重置list的原因是list里面包含了hashMap,直接清除会把hashMap清除掉.不清楚的话添加后一个的时候,数组原本就包含了前一个标签的所有东西.所以只能new
				// System.out.println("=====结束遍历某一个标签=====");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取路径和名字并设置到相应的数组,集合或者变量里面
	 * 
	 * @param bookAttrs
	 *            解析class标签的属性
	 * @return 返回当前class对象的名字,路径已经用实例变量存储
	 */
	public String setpath(List<Attribute> bookAttrs) { //
		String id = "";
		for (Attribute attr : bookAttrs) {
			if (attr.getName().equals("id")) {
				id = attr.getValue();
			}
			if (attr.getName().equals("path")) {
				path = attr.getValue();

			}
		}
		return id;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取对象的属性并设置到相应的数组,集合或者变量里面
	 * 
	 * @param attr
	 *            有document对象解析传进来的class子标签
	 * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
	 *         返回了设置属性的Map
	 */
	public Map<String, String> setZiBianQian(Attribute attr) {
		if (attr.getName().equals("value")) {
			map.put("value", attr.getValue());
		} else if (attr.getName().equals("name")) {
			map.put("name", attr.getValue());
		}
		// else if(attr.getName().equals("type")){
		// map.put("type", attr.getValue());
		// }
		return map;
	}

	/**
	 * 重写默认构造器,直接构造的时候初始化
	 */
	public resolveXml() {
		lwc();
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 获取对象的type并设置到相应的数组,集合或者变量里面
	 * 
	 * @param attr
	 *            解析class子标签的属性
	 * @return 一个class子标签的type是不是为单例,单例为true,默认为多例false
	 */
	public Map<String, String> setTypes(Attribute attr) {
		if (attr.getName().equals("type")) {
			typeMap.put("type", attr.getValue());
		}
		return typeMap;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 将单例类的路径添加到classTypePath里面
	 * 
	 * @param path
	 *            对象的类路径
	 * @param attr
	 *            解析class子标签的属性
	 * @return 设置完成某个类的type属性
	 */
	public Map<String, String> setTyePath(String path, Attribute attr) {
		if (attr.getName().equals("type")) {
			if (attr.getValue().equals("true")) {
				for (int i = 0; i < classTypePaths.size(); i++) {
					Map<String, String> map = classTypePaths.get(i);
					if (map.get("path").equals(path)) {
						return classTypePath;
					}
				}
				classTypePath.put("path", path);
				classTypePath.put("type", attr.getValue());
				return classTypePath;
			}
		}
		return classTypePath;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 纪录某个单例类的第一个对象和名字
	 * 
	 * @param path
	 *            对象的类路径
	 * @param objectName
	 *            对象名字
	 */
	public void setLocation(String path, String objectName) {
		if (location.get(path) != null) {
			return;
		}
		for (int i = 0; i < classTypePaths.size(); i++) {
			Map<String, String> map = classTypePaths.get(i);
			if (map.get("path") != null) {
				if (map.get("path").equals(path)) {
					location.put(path, objectName);
				}
			}
		}
	}
}
