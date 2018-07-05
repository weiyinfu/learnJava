package net;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLConnectionTest {
	static String ip = "http://www.baidu.com";

	public static void main(String[]args) {
		String string=go();
		System.out.println(string);
	}
	static String go() {
		String s = "";
		try {
			URLConnection connection = new URL(ip).openConnection();
			InputStream in=connection.getInputStream();
			System.out.println(in.available());
			Scanner cin = new Scanner(in);
			while (cin.hasNext()) {
				s+=cin.nextLine();
			}
			cin.close();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
