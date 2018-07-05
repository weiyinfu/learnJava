package net;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestCookie {
	public static void main(String args[]) throws Exception {
		String lianzaiUrl = "http://tieba.baidu.com/p/1243174814?pn=";
		String loginAction = "https://passport.baidu.com/?login?";
		String cookie = getCookie("test", "test", loginAction);
		if (!cookie.contains("USERID=")) {
			System.out.println("登录失败");
			System.exit(1);
		}
		StringBuffer result = new StringBuffer();
		StringBuffer errorList = new StringBuffer();
		for (int i = 1; i <= 3; i++) {
			String allUrl = getUrl(lianzaiUrl + i);
			String all[] = allUrl.split(";");
			for (int x = 0; x < all.length; x++) {// 拿到每个帖子的地址
				String content = doRead(cookie, all[x]);
				if (null != content && !"".equals(content)) {
					result.append(content);
				} else {
					errorList.append(all[x] + "\r\n");
				}
			}
		}
		BufferedWriter writer = new BufferedWriter(
				new FileWriter(new File("F:\\遮天.txt")));
		BufferedWriter errorWriter = new BufferedWriter(
				new FileWriter(new File("F:\\errorList.txt")));
		writer.write(result.toString());
		writer.close();
		errorWriter.write(errorList.toString());
		errorWriter.close();
	}

	public static String doRead(String cookie, String url) throws IOException {
		BufferedReader reader = null;
		String titleBegin = "<h1>";
		String titleEnd = "</h1>";
		String contentBegin = "class=\"d_post_content\">";
		String contentEnd = "</p>";
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestProperty("Cookie", cookie);
		reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), "gbk"));
		String line = "";
		StringBuffer resultBuffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			resultBuffer.append(line);
		}
		String result = resultBuffer.toString();
		int titleBeginIndex = result.indexOf(titleBegin) + titleBegin.length();
		int titleEndIndex = result.indexOf(titleEnd);
		if (titleBeginIndex < 0 || titleEndIndex < 0) {
			System.out.println("帖子不存在,url:" + url);
			return null;
		}
		String title = result.substring(titleBeginIndex, titleEndIndex);
		System.out.println("正在读取帖子:" + title + "...");
		String content = title + "\r\n";
		while (result.contains(contentBegin)) {
			int contentBeginIndex = result.indexOf(contentBegin)
					+ contentBegin.length();
			result = result.substring(contentBeginIndex);
			int contentEndIndex = result.indexOf(contentEnd);
			content += result.substring(0, contentEndIndex);
			result = result.substring(contentEndIndex + contentEnd.length());
		}
		conn.disconnect();
		reader.close();
		content = content.replaceAll("<br>", "\r\n");
		content = content.replaceAll("</br>", "\r\n");
		content += "\r\n";
		return content;
	}

	/**
	 * 获得一连载贴内容中的所有超链接
	 * 
	 * @param lianzaiUrl
	 * @return
	 * @throws Exception
	 */
	public static String getUrl(String lianzaiUrl) throws Exception {
		URL url = new URL(lianzaiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), "gbk"));
		String line = "";
		StringBuffer buffer = new StringBuffer();
		StringBuffer urlBuf = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String result = buffer.toString();
		String contentBegin = "class=\"d_post_content\">";
		String contentEnd = "</p>";
		String urlBegin = "<a href=\"";
		String urlEnd = "\"";
		while (result.contains(contentBegin)) {
			int contentStartIndex = result.indexOf(contentBegin)
					+ contentBegin.length();
			result = result.substring(contentStartIndex);
			int contentEndIndex = result.indexOf(contentEnd);
			String content = result.substring(0, contentEndIndex);
			while (content.contains(urlBegin)) {
				int urlBeginIndex = content.indexOf(urlBegin)
						+ urlBegin.length();
				content = content.substring(urlBeginIndex);
				int urlEndIndex = content.indexOf(urlEnd);
				String href = content.substring(0, urlEndIndex).trim();
				/*
				 * http://tieba.baidu.com/p/1196506653
				 * http://tieba.baidu.com/p/1196506653?see_lz=1
				 * http://tieba.baidu.com/f?kz=1127473409
				 * http://tieba.baidu.com/p/1127473409?see_lz=1
				 */
				// http://tieba.baidu.com/f?kz=1127600193
				// http://tieba.baidu.com/p/1127600193?see_lz=1
				// 将超链接转为只看楼主模式
				if (href.contains("http://tieba.baidu.com/f?")) {
					String kz = href
							.substring("http://tieba.baidu.com/f?kz=".length());
					href = "http://tieba.baidu.com/p/" + kz.trim()
							+ "?see_lz=1";
				} else {
					href += "?see_lz=1";
				}
				urlBuf.append(href + ";");
				content = content.substring(urlEndIndex + urlEnd.length());
			}
			result = result.substring(contentEndIndex + contentEnd.length());
		}
		reader.close();
		return urlBuf.toString();
	}

	/**
	 * post方式登录
	 * 
	 * @param username
	 * @param password
	 * @param loginAction
	 * @return
	 * @throws Exception
	 */
	public static String getCookie(String username, String password,
			String loginAction) throws Exception {
		// 登录
		URL url = new URL(loginAction);
		String param = "username=" + username + "&password=" + password;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		OutputStream out = conn.getOutputStream();
		out.write(param.getBytes());
		out.flush();
		out.close();
		String sessionId = "";
		String cookieVal = "";
		String key = null;
		// 取cookie
		for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
			if (key.equalsIgnoreCase("set-cookie")) {
				cookieVal = conn.getHeaderField(i);
				cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
				sessionId = sessionId + cookieVal + ";";
			}
		}
		return sessionId;
	}
}