package lucene.suggest.pris;

import httpserver.MutiTheadHttpHandler;
import httpserver.ParameterFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lucene.suggest.pris.BookTitleSuggester.BookTitleSuggestion;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import util.ConnectionUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class BookTitleSuggestHttpHandler implements HttpHandler{

	
	protected static Logger logger = Logger.getLogger(BookTitleSuggestHttpHandler.class.getName());

	
	BookTitleSuggester bookTitleSuggester;
	
	
	public BookTitleSuggestHttpHandler(BookTitleSuggester bookTitleSuggester){
		this.bookTitleSuggester=bookTitleSuggester;
	}
	
	@Override
	public void handle(HttpExchange xchg) throws IOException {
	
		logger.info("Handle  HttpExchange  ...");
		Map<String, Object> params = (Map<String, Object>) xchg.getAttribute("parameters");
		for (Entry<String, Object> entry : params.entrySet()) {
			logger.info("Param " + entry+" "+entry.getValue().getClass());
		}

		// kind=open&id=1&timetype=day&starttime=2013-08-01&endtime=2013-08-03

		String query = (String) params.get("query");
		int maxResult=Integer.parseInt((String) params.get("maxResult"));
		long begin=System.currentTimeMillis();
		List<BookTitleSuggestion>	bookTitleSuggestions=bookTitleSuggester.suggest(query, maxResult);
		long cost=System.currentTimeMillis()-begin;
		
		//MSG returnMSG = new MSG(MSG.SUCCESS, "getResultSuccess,suggest cost:"+cost+"'ms.", bookTitleSuggestions);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code", "success");
		jsonObject.put("msg", "getResultSuccess,suggest cost:"+cost+"'ms.");
		jsonObject.put("content", bookTitleSuggestions);
		
		String returnString = JSON.toJSONString(jsonObject);
		byte[] returnDatas = returnString.getBytes("utf-8");
		
		
		try {
			xchg.sendResponseHeaders(200, returnDatas.length);
			OutputStream os = xchg.getResponseBody();
			os.write(returnDatas);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("# error when send response   ...");
		}
		
		logger.info("Handle  End  ...   , suggest cost:"+cost+"'ms.");
	}
	
	public static void main(String[] args) throws Exception {
		
		String serverIP = InetAddress.getLocalHost().getHostAddress();
		int port = 8888;
		int backLog = 1000;
		int threadNum=40;
		int maxThreadNum=40;
		int taskQueueSize=1000;
	//	MemData memData = new MemData();
		ConnectionUtil.init("conf/dbcp_online.properties");
	
		
		//DataCacher.setMaxSize(10000);

	
		HttpServer server = HttpServer.create(new InetSocketAddress(serverIP, port), backLog);

		long startTime=System.currentTimeMillis();
		BookTitleSuggester bookTitleSuggester=new BookTitleSuggester();
		bookTitleSuggester.init("2013-11-01","2013-11-21");
		long cost=System.currentTimeMillis()-startTime;
		System.out.println("build cost:"+cost+"'ms");
		
		HttpContext bookTitleSuggestContext = server.createContext("/BookTitleSuggest", new MutiTheadHttpHandler(threadNum,maxThreadNum,taskQueueSize,new BookTitleSuggestHttpHandler(bookTitleSuggester)));
		bookTitleSuggestContext.getFilters().add(new ParameterFilter("utf-8"));
		
		server.setExecutor(null); // creates a default executor
		
		server.start();
		System.out.println("  BookTitleSuggest Server start ...");
		System.out.println("  serverIP:" + serverIP);
		System.out.println("serverPort:" + port);
		System.out.println("   backLog:" + backLog);
		
		
	}

}
