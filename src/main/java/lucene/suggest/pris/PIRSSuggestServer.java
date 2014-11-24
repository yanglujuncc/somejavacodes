package lucene.suggest.pris;

import httpserver.MutiTheadHttpHandler;
import httpserver.ParameterFilter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import util.ConnectionUtil;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class PIRSSuggestServer {

	protected static Logger logger = Logger.getLogger(PIRSSuggestServer.class.getName());

	public static void main(String[] args) throws Exception {

		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		ConnectionUtil.init("conf/dbcp_online.properties");

		SimpleDateFormat ISO_date_format = new SimpleDateFormat("yyyy-MM-dd");

		String serverIP = InetAddress.getLocalHost().getHostAddress();

		int port = 8085;
		int backLog = 4000;
		int threadNum = 2;
		int maxThreadNum = 4;
		int taskQueueSize = 1000;

		HttpServer server = HttpServer.create(new InetSocketAddress(serverIP, port), backLog);

		long timeNow = System.currentTimeMillis();
		int day = 31;
		long TimeOfDay = 24 * 3600 * 1000;
		String  endDateStr= ISO_date_format.format(new Date(timeNow));
		String  beginDateStr = ISO_date_format.format(new Date(timeNow - day * TimeOfDay));

		logger.info("Count Record  Bettwen :[" + beginDateStr + " , " + endDateStr + "]");

		long startTime = System.currentTimeMillis();
		FeedTitleSuggester feedTitleSuggester = new FeedTitleSuggester();
		feedTitleSuggester.init(beginDateStr, endDateStr);

		BookTitleSuggester bookTitleSuggester = new BookTitleSuggester();
		bookTitleSuggester.init(beginDateStr, endDateStr);

		SearchKeywordSuggester searchKeywordSuggester = new SearchKeywordSuggester();
		searchKeywordSuggester.init(beginDateStr, endDateStr);

		long cost = System.currentTimeMillis() - startTime;
		logger.info("build cost:" + cost + "'ms");

		HttpContext feedTitleSuggestContext = server.createContext("/FeedTitleSuggest", new MutiTheadHttpHandler(threadNum, maxThreadNum, taskQueueSize,
				new FeedTitleSuggestHttpHandler(feedTitleSuggester)));
		feedTitleSuggestContext.getFilters().add(new ParameterFilter("utf-8"));

		HttpContext bookTitleSuggestContext = server.createContext("/BookTitleSuggest", new MutiTheadHttpHandler(threadNum, maxThreadNum, taskQueueSize,
				new BookTitleSuggestHttpHandler(bookTitleSuggester)));
		bookTitleSuggestContext.getFilters().add(new ParameterFilter("utf-8"));

		HttpContext searchKeywordSuggestContext = server.createContext("/SearchKeywordSuggest", new MutiTheadHttpHandler(threadNum, maxThreadNum, taskQueueSize,
				new SearchKeywordSuggestHttpHandler(searchKeywordSuggester)));
		searchKeywordSuggestContext.getFilters().add(new ParameterFilter("utf-8"));

		server.setExecutor(null); // creates a default executor

		server.start();
		logger.info("   Suggest Server start ...");
		logger.info("  serverIP:" + serverIP);
		logger.info("serverPort:" + port);
		logger.info("   backLog:" + backLog);

	}
}
