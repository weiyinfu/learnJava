package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestCookie2 {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.baidu.com");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		String content = "username=admin&password=admin";
		//connection.setRequestProperty("Cookie", responseCookie);
		OutputStream os = connection.getOutputStream();
		os.write(content.toString().getBytes("GBK"));
		os.close();

		String responseCookie = connection.getHeaderField("Set-Cookie");// 取到所用的Cookie
		String sessionIdString = "";
		if (responseCookie != null) {
			sessionIdString = responseCookie.substring(0,
					responseCookie.indexOf(";"));
			System.out.println(sessionIdString);
		}

		BufferedReader br = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String line = br.readLine();
		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
		URL url1 = new URL("网页的登录后的页面");
		HttpURLConnection connection1 = (HttpURLConnection) url1
				.openConnection();
		connection1.setRequestProperty("Cookie", responseCookie);// 给服务器送登录后的cookie
		BufferedReader br1 = new BufferedReader(
				new InputStreamReader(connection1.getInputStream()));
		line = null;
		while ((line = br1.readLine()) != null) {
			System.out.println(line);
		}
	}
}
