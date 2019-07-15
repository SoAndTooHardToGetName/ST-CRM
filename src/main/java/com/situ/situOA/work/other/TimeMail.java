package com.situ.situOA.work.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.ParseException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.situ.situOA.work.model.CommunicationModel;

import tools.FmtMail;
import tools.FmtScheduler;

public class TimeMail implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
		JobDataMap jobData = detail.getJobDataMap();
//			    Object list = jobData.get("list");

		String[] to = (String[]) jobData.get("list");// 收件人
		String from = "1278825626@qq.com";// 发件人
		String pass = "auqmwrynegntjjga";
		String hostSend = "smtp.qq.com";
		String portSend = "587";
		FmtMail ms = new FmtMail(to, from, pass, hostSend, portSend);
		try {
			ms.send((String) jobData.get("zhuti"), (String) jobData.get("neirong"));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) throws ParseException {
		CommunicationModel temp = new CommunicationModel();
		String tempTime = "2019-07-10 10:02:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = dateFormat.parse(tempTime);
			temp.setStatus("测试");
			temp.setContent("定时发送测试");
			new TimeMail().fmtSch(temp, time, new String[] { "378030737@qq.com" });
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
