package util;

import java.util.Arrays;
import java.util.TimeZone;



public class MacAddr {
	public static byte[] macAddrToBytes(String macAddr) {

		// 94:db:c9:5e:6d:2e
		byte[] byteDates = new byte[6];
		String[] byteStrs = macAddr.split("[:.]");
		if (byteStrs.length != 6) {
			//System.out.println("error mac format, macAddr:" + macAddr);
			return null;
		}

		for (int i = 0; i < 6; i++) {
			byteDates[i] = (byte) (Integer.parseInt(byteStrs[i], 16));
		}
		return byteDates;

	}

	public static String macAddrToBytes(byte[] macAddr) {

		// 94:db:c9:5e:6d:2e

		StringBuffer aStringBuffer = new StringBuffer();
		if (macAddr.length != 6) {
			//System.out.println("macAddr:" + Arrays.toString(macAddr));
			System.exit(1);
		}

		for (int i = 0; i < 6; i++) {
			String term=Integer.toHexString(macAddr[i] & 0xFF);
			if(term.length()==1)
				term="0"+term;
			aStringBuffer.append(term);
			if (i != 5)
				aStringBuffer.append(":");
		}
		return aStringBuffer.toString();

	}
	public static void main(String[] args) {


		String macAddr = "0:db:c9:5e:6d:2e";

		System.out.println("  Input macAddr:" + macAddr);
		byte[] bytes = macAddrToBytes(macAddr);
		System.out.println("         bytes :" + Arrays.toString(bytes));

		System.out.println("rebuild macAddr:" + macAddrToBytes(bytes));
	}
}
