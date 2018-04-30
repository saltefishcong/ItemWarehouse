package com.Itemhouse.Aid;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          &nbsp;&nbsp;对敏感消息转换
 */
public class converter {

	private static Key key;
	private static String key_str = "mykey";

	/**
	 * <strong>方法说明:</strong> <br>
	 * 进行MD5加密,不可逆
	 * 
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的字符串
	 */
	public String encryptToMD5(String info) {
		byte[] digesta = null;
		try {
			// 得到一个md5的消息摘要
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes());
			// 得到该摘要
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将摘要转为字符串
		String rs = byte2hex(digesta);
		return rs;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 将加密后的字节数组转换成大写字符串
	 * 
	 * @param b
	 *            传进来的数组
	 * @return String 返回加密的字符串
	 */
	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * SHA 加密算法,不可逆
	 * 
	 * @param info
	 *            要加密的消息
	 * @return 加密后的字符串
	 */
	public String SHAEncode(String info) {
		byte[] digesta = null;
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("SHA");
			md5.update(info.getBytes());
			digesta = md5.digest();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		String s = byte2hex(digesta);
		return s;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 进行加密解密对象初始化
	 * 
	 * @throws NoSuchAlgorithmException
	 *             初始化异常
	 */
	public void initDES() throws NoSuchAlgorithmException {
		KeyGenerator generator = KeyGenerator.getInstance("DES");
		generator.init(new SecureRandom(key_str.getBytes()));
		key = generator.generateKey();
		generator = null;
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 进行加密
	 * 
	 * @param info
	 *            要加密的字符串
	 * @return java.lang.String 返回加密完成的字符串
	 * @throws Exception
	 *             加密异常
	 */
	public String getEncryption(String info) throws Exception {
		BASE64Encoder base = new BASE64Encoder();
		byte[] infoByte = info.getBytes("UTF-8");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptionByte = cipher.doFinal(infoByte);
		return base.encode(encryptionByte);
	}

	/**
	 * <strong>方法说明:</strong> <br>
	 * 进行解密,解密必须要是同一个对象操作,不然出错。重启了服务器初始化的对象又不一样
	 * 
	 * @param info
	 *            要解密的字符串
	 * @return java.lang.String 返回解密完成的字符串
	 * @throws Exception
	 *             解密异常
	 */
	public String getDecryption(String info) throws Exception {
		BASE64Decoder base = new BASE64Decoder();
		byte[] infoByte = base.decodeBuffer(info);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] encryptionByte = cipher.doFinal(infoByte);
		return new String(encryptionByte, "UTF-8");
	}

}
