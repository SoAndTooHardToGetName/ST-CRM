package com.situ.situOA.work.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ParseException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.work.model.CommunicationModel;
import com.situ.situOA.work.model.CustomerInfoModel;
import com.situ.situOA.work.model.MailModel;
import com.situ.situOA.work.other.TimeMail;
import com.situ.situOA.work.service.ICommunicationService;
import com.situ.situOA.work.service.ICustomerInfoService;

import tools.FmtMail;
import tools.FmtScheduler;

@Controller
@RequestMapping("mail")
public class CommunicationController {
	@Autowired
	private ICommunicationService<CommunicationModel> service;
	@Autowired
	private ICustomerInfoService<CustomerInfoModel> serviceCust;

	/**
	 * 发送邮件
	 * 
	 * @param to
	 * @param subject
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("send")
	public String emailSend(CommunicationModel communicationModel, HttpServletRequest request, Date time)
			throws Exception {
		String[] tos = { getMail(communicationModel) };
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		communicationModel.setCreateTime(dateFormat.format(new Date()));
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		communicationModel.setUserCode(user.getCode());
		if (time == null) {
			FmtMail a = new FmtMail(tos, "1278825626@qq.com", "auqmwrynegntjjga", "smtp.qq.com", "587");
			a.send(communicationModel.getStatus(), communicationModel.getContent());
		} else
			fmtSch(communicationModel, time, tos);
		service.insert(communicationModel);
		return "/WEB-INF/work/mail/send.jsp";
	}

	/**
	 * 获取收件人邮箱
	 * 
	 * @param communicationModel
	 * @return
	 */
	private String getMail(CommunicationModel communicationModel) {
		CustomerInfoModel customerInfoModel = new CustomerInfoModel();
		customerInfoModel.setCode(communicationModel.getCustCode());
		return serviceCust.select(customerInfoModel).getEmail();
	}

	/**
	 * 查看邮箱收到的邮件
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "recives", produces = "application/json;charset=utf-8")
	public String emailRecives() throws Exception {
		// 定义连接POP3服务器的属性信息
		String pop3Server = "pop.qq.com";
		String protocol = "pop3";
		String username = "1278825626@qq.com";
		String password = "auqmwrynegntjjga"; // QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocol); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", pop3Server); // 发件人的邮箱的 SMTP服务器地址

		// 获取连接
		Session session = Session.getDefaultInstance(props);
		session.setDebug(false);

		// 获取Store对象
		Store store = session.getStore(protocol);
		store.connect(pop3Server, username, password); // POP3服务器的登陆认证

		// 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
		Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
		folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限

		Message[] messages = folder.getMessages();// 得到邮箱帐户中的所有邮件

		List<MailModel> dataList = new ArrayList<MailModel>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		map.put("code", 0);

		for (Message message : messages) {
			String subject = message.getSubject();// 获得邮件主题
			Address from = (Address) message.getFrom()[0];// 获得发送者地址
			System.out.println("邮件的主题为: " + subject + "\t发件人地址为: " + from);
			System.out.println("邮件的内容为：");
			message.writeTo(System.out);// 输出邮件内容到控制台

			MailModel mailModel = new MailModel();
			mailModel.setTitle(subject);
			mailModel.setFrom(from.toString());
			mailModel.setContent(message.getContent().toString());
			dataList.add(mailModel);
			count++;
		}

		map.put("data", dataList);
		map.put("count", count);

		folder.close(false);// 关闭邮件夹对象
		store.close(); // 关闭连接对象
		return new JSONObject(map).toString();
	}

	/**
	 * 查看邮件记录
	 * 
	 * @param communicationModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selctModel(CommunicationModel communicationModel, HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		if ("empSell".equals(user.getRoleCode()))
			communicationModel.setUserCode(user.getCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("data", service.selectModel(communicationModel));
		map.put("count", service.count(communicationModel));
		return new JSONObject(map).toString();
	}

	/**
	 * 单删除
	 * 
	 * @param communicationModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String delete(CommunicationModel communicationModel, HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		if ("empSell".equals(user.getRoleCode()))
			return "2";
		if (service.deleteModel(communicationModel) > 0)
			return "1";
		return null;
	}

	private void fmtSch(CommunicationModel communicationModel, Date time, String[] tos) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", tos);
		map.put("neirong", communicationModel.getContent());
		map.put("zhuti", communicationModel.getStatus());
		FmtScheduler fmt = FmtScheduler.getInit(TimeMail.class, map);
//    	fmt.startSimpleTrigger(3);
		SimpleDateFormat dddd = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
		String da = dddd.format(time);
		fmt.startCronTrigger(da);
	}

}
