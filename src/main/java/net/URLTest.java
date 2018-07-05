package net;

import java.net.*;
import java.util.*;

public class URLTest {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.neu.edu.cn");
		Scanner in = new Scanner(url.openStream());
		while (in.hasNextLine()) {
			String str = in.nextLine();
			System.out.println(str);
		}
		in.close();
		URI uri = new URI("http://blog.csdn.net/xiazdong");
		System.out.println(uri.getScheme());
		System.out.println(uri.getSchemeSpecificPart());
		System.out.println(uri.getAuthority());
		System.out.println(uri.getUserInfo());
		System.out.println(uri.getHost());
		System.out.println(uri.getPort());
		System.out.println(uri.getPath());
		System.out.println(uri.getQuery());
		System.out.println(uri.getFragment());

		String str = "/article/details/6705033";
		URI combined = uri.resolve(str);
		System.out.println(combined.getScheme() + combined.getSchemeSpecificPart());
		URI relative = uri.relativize(new URI(str));
		System.out.println(relative.getSchemeSpecificPart());
	}
}
