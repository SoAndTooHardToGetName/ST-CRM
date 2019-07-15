package com.situ.situOA.work.other;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FanYi {

	public static String baseUrl = "https://baike.baidu.com/item/";

	public String getContent(String url) {
//		使用默认配置的httpclient
		CloseableHttpClient client = HttpClients.createDefault();
//		使用get方法
		HttpGet httpGet = new HttpGet(url);
		try {
			// 执行请求，获取响应
			CloseableHttpResponse response = client.execute(httpGet);
			// 是否请求成功，打印http状态码
			System.out.println("状态码：" + response.getStatusLine().getStatusCode());
			// 获取响应的实体内容
			HttpEntity entity = response.getEntity();
			return parse(entity.getContent(), url);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String parse(InputStream is, String url) {
		// TODO Auto-generated method stub
		String contentText = "";
		try {
			// 将HTML内容解析成UTF-8
			Document doc = Jsoup.parse(is, "utf-8", url);
			// 刷选所需的网页内容
			contentText = doc.select("div.lemma-summary").first().text();
			// 李忠正则表达式去掉字符串中的“[数字]”
			contentText = contentText.replaceAll("\\[\\d+\\]", "");
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentText;
	}
}
