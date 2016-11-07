package com.dev.bruno.servicesms.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtils {

	public Document getDocument(String url, String method, Integer timeout, String charset, Map<String, String> data) throws IOException {
		if(charset == null) {
			charset = "UTF-8";
		}
		
		URL website = new URL(url);
		
		HttpURLConnection httpUrlConnetion = (HttpURLConnection) website.openConnection();
			
		httpUrlConnetion.setReadTimeout(timeout);
		httpUrlConnetion.setRequestMethod(method);
		httpUrlConnetion.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpUrlConnetion.addRequestProperty("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4");
		httpUrlConnetion.addRequestProperty("Cache-Control", "no-cache");
		httpUrlConnetion.addRequestProperty("Pragma", "no-cache");
		httpUrlConnetion.addRequestProperty("Connection","close");
		httpUrlConnetion.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		httpUrlConnetion.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
		httpUrlConnetion.connect();
		
		String body = null;
		if(httpUrlConnetion.getHeaderFields().get("Content-Encoding") != null) {
			body = IOUtils.toString(new GZIPInputStream(httpUrlConnetion.getInputStream()));
		} else {
			body = IOUtils.toString(httpUrlConnetion.getInputStream());
		}
		
		return Jsoup.parse(body, charset);
	}
}
