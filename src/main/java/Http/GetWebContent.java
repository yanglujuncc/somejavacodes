package Http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetWebContent {
	
	public static String getWebCon(String domain) {
		// System.out.println("开始读取内容...("+domain+")");
		StringBuffer sb = new StringBuffer();
		try {
			java.net.URL url = new java.net.URL(domain);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			in.close();
		} catch (Exception e) { // Report any errors that arise
			sb.append(e.toString());
			System.err.println(e);
			System.err.println("Usage:   java   HttpClient   <URL>   [<filename>]");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		getWebCon("http://localhost:8080/search?date=2013-05-09&target=%E8%B4%9F%E9%9D%A2&token=%E5%9D%91%E7%88%B9&pageSize=1&pageNum=1");
	}
}
