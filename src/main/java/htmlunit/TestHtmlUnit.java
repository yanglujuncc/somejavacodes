package htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestHtmlUnit {

	public static void main1(String[] args) throws Exception{
		
		   	WebClient webClient = new WebClient(BrowserVersion.CHROME);
		   	
		    HtmlPage page =	webClient.getPage("http://www.baidu.com");
		  
		    DomElement fromE=page.getElementById("form");
		    
		    DomElement kwIputE=page.getElementById("kw");
		    // System.out.println(page.asXml());
		  //   System.out.println(fromE.asXml());
		   //  System.out.println(kwIputE.asXml());
		     
		  //  page.e
		      HtmlForm form =(HtmlForm) fromE;
		   
		      HtmlSubmitInput suButton = (HtmlSubmitInput) page.getElementById("su");
		      HtmlTextInput textField = (HtmlTextInput) page.getElementById("kw");

		     // Change the value of the text field
		     textField.setText("中国");

		     // Now submit the form by clicking the button and get back the second page.
		      HtmlPage page2 = suButton.click();

		    System.out.println(page2.asXml());
				
		    webClient.closeAllWindows();
	}
	
	public static void main2(String[] args) throws Exception {  
	    String url = "http://www.google.com.hk";  
	  
	    //
	    final WebClient webClient = new WebClient();  
	    HtmlPage htmlPage = webClient.getPage(url);  
	  
	    // HtmlUnit dom模型  
	    // 获取表单 ,获得form标签name属性=f  
	    HtmlForm form = htmlPage.getFormByName("f");  
	    // 获取输入框, 获取 input标签 ，name属性=q  
	    HtmlTextInput text = form.getInputByName("q");  
	    // 搜索百度  
	    text.setText("baidu");  
	    // 获取提交按钮  
	    HtmlSubmitInput button = form.getInputByName("btnG");  
	    // 提交表单  
	    HtmlPage listPage = button.click();  
	  
	 //   System.out.println(listPage.asXml());  
	           
	      webClient.closeAllWindows();  
	}  
	
	public static void main(String[] args) throws Exception{
		   java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

		 main1( args);
	}
}
