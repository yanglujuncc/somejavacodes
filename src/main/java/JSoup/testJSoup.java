package JSoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class testJSoup {

	public static String getWebCon(String domain,String encode) {
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
	
	public static void main(String[] args) throws IOException {
		//String content=getWebCon("http://www.baidu.com","utf-8");
		Document doc = Jsoup.connect("http://www.1766bbs.com/DuShi/101Html.Html").get();
		
		String title = doc.title();
		System.out.println(doc.html().length());  //65887
	}
}
