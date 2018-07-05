package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {
	public static void main(String[] args) throws UnknownHostException {
		inetAddressByIp("58.154.188.240");
	}

	private static byte[] getIpByte(String ipAddress) {
		String[] ipStr = ipAddress.split("\\.");// 以"."拆分字符串
		byte[] ipByte = new byte[ipStr.length];
		for (int i = 0; i < ipStr.length; i++) {
			ipByte[i] = (byte) (Integer.parseInt(ipStr[i]) & 0xFF);// 调整整数大小,byte的数值范围为-128~127
		}
		return ipByte;
	}

	public static void inetAddressByIp(String ipAddress) throws UnknownHostException {

		byte[] ipByte = getIpByte(ipAddress);

		InetAddress ia = InetAddress.getByAddress(ipByte);

		System.out.println("Canonical Host Name = " + ia.getCanonicalHostName());
		System.out.println("Host Address = " + ia.getHostAddress());
		System.out.println("Host Name = " + ia.getHostName());
		System.out.println("Is Loopback Address = " + ia.isLoopbackAddress());
	}
}