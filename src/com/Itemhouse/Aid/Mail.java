package com.Itemhouse.Aid;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author &nbsp;&nbsp;Lwc
 * @since &nbsp;&nbsp;1.6
 * @version &nbsp;&nbsp;1.8<br>
 *          <strong>类说明:</strong><br>
 *          邮件类,通过邮件注册
 */
public class Mail {

	public static void main(String[] args) {
	}

	public static void send(String to, String form, String righerMess) {
		try {
			Properties proper = new Properties();
			proper.setProperty("mail.debug", "true");
			proper.setProperty("mail.smtp.auth", "true");
			proper.setProperty("mail.host", "smtp.qq.com");
			proper.setProperty("mail.transport.protocol", "smtp");
			proper.setProperty("mail.smtp.ssl.enable", "true");
			proper.setProperty("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(proper);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(form));
			InternetAddress iAddress = new InternetAddress(to);
			message.setRecipient(Message.RecipientType.TO, iAddress);
			message.setSubject("zhuni");
			message.setSentDate(new Date());
			message.setText("zhuni!" + righerMess);
			// message.setHeader("X-Mailer", "LOTONtechEmail");
			Transport transport = session.getTransport();
			transport.connect("smtp.qq.com", form, "zcyoikrswbffbaib");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("发送成功");
		} catch (AddressException e) { // TODO
			System.out.println("Address异常" + e.getMessage());
		} catch (MessagingException e) { // TODO
			e.printStackTrace();
			System.out.println("发送失败" + e.getMessage());
		}
	}

	public static void sendProduce(String to, String form, String produceArticle) {
		try {
			Properties proper = new Properties();
			proper.setProperty("mail.debug", "true");
			proper.setProperty("mail.smtp.auth", "true");
			proper.setProperty("mail.host", "smtp.qq.com");
			proper.setProperty("mail.transport.protocol", "smtp");
			proper.setProperty("mail.smtp.ssl.enable", "true");
			proper.setProperty("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(proper);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(form));
			InternetAddress iAddress = new InternetAddress(to);
			message.setRecipient(Message.RecipientType.TO, iAddress);
			message.setSubject("produce");
			message.setSentDate(new Date());
			message.setText("你的" + produceArticle + "物品不足,请及时添加!");
			// message.setHeader("X-Mailer", "LOTONtechEmail");
			Transport transport = session.getTransport();
			transport.connect("smtp.qq.com", form, "zcyoikrswbffbaib");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("发送成功");
		} catch (AddressException e) { // TODO
			System.out.println("Address异常" + e.getMessage());
		} catch (MessagingException e) { // TODO
			e.printStackTrace();
			System.out.println("发送失败" + e.getMessage());
		}
	}
}
