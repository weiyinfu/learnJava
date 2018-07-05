package net;

import java.net.InetAddress;

public class InetAddressTest {
	public static void main(String[] args) throws Exception {
		InetAddress local = InetAddress.getLocalHost();
		System.out.println("Local Host Address:" + local.getHostAddress());
		System.out.println("Local Host Name:" + local.getHostName());
		System.out.println("Local Host :" + local);
		System.out.println(InetAddress.getByName("58.154.188.240").getHostName());
		System.out.println(InetAddress.getLoopbackAddress());
		System.out.println(InetAddress.getByName(local.getHostName()));
		System.out.println(InetAddress.getByName("www.baidu.com"));
		InetAddress[] remote = InetAddress.getAllByName("www.taobao.com");
		for (InetAddress a : remote) {
			System.out.println(a);
		}
	}
}
