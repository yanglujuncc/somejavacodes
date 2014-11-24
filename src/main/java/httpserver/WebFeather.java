package httpserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebFeather {

	public static String getURL(String urlSource) {

		try {
			System.out.println("urlSource:"+urlSource);
			StringBuffer htmlBuffer = new StringBuffer();
			
		
		//	System.out.println("encodedURL:"+encodedURL);
			URL su = new URL(urlSource);
			URLConnection conn = su.openConnection();
			// conn.setRequestProperty("Proxy-Authorization", authentication);

			InputStreamReader imageSource = new InputStreamReader(conn.getInputStream(), "utf-8");
			BufferedReader aBufferedReader = new BufferedReader(imageSource);
			String line = null;
			while ((line = aBufferedReader.readLine()) != null) {
				htmlBuffer.append(line);
			}
			imageSource.close();

			return htmlBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static class WebFetch implements Runnable{
		String url;
		static	long beginGloble=0; 
		static	long endGloble=0;
		public WebFetch(String url){
			this.url=url;
		}
		@Override
		public void run() {
			
			long begin=System.currentTimeMillis();
			if(beginGloble==0)
				beginGloble=begin;
			
			String result=getURL( url);
			long end=System.currentTimeMillis();
			endGloble=end;
			long cost=end-begin;
			
			System.out.println("ok ,cost="+cost+"'ms "+url+" >> "+ result);
		}
		
	}
	
	public static void main(String[] args){
		
		int threadNum=20;
		int maxThreadNum=40;
		int taskQueueSize=1000;
		ThreadPoolExecutor aThreadPool= new ThreadPoolExecutor(threadNum, maxThreadNum, 300, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(taskQueueSize),  new ThreadPoolExecutor.DiscardOldestPolicy());  
		
		List<String> urlList=new LinkedList<String>();
		
		// id1=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1    id2=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1
 		urlList.add("http://10.100.83.125:8084/book?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&begintime=2013-09-10&endtime=2013-10-30");
		urlList.add("http://10.100.83.125:8084/book?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&begintime=2013-09-10&endtime=2013-10-30&timeslot=24");
		urlList.add("http://10.100.83.125:8084/book_client?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&starttime=2013-10-01&endtime=2013-10-30&attr=client");
		urlList.add("http://10.100.83.125:8084/book_client?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&starttime=2013-10-01&endtime=2013-10-30&attr=scr_res");
		urlList.add("http://10.100.83.125:8084/book_client?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&starttime=2013-10-01&endtime=2013-10-30&attr=os");
		urlList.add("http://10.100.83.125:8084/book_client?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&starttime=2013-10-01&endtime=2013-10-30&attr=os_ver&os=ios");
		urlList.add("http://10.100.83.125:8084/book_client?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&starttime=2013-10-01&endtime=2013-10-30&attr=province");
		urlList.add("http://10.100.83.125:8084/book_client?kind=open&id=3b3fd7e9-f2cb-4145-9387-eeb7affff9ac_1&timetype=day&starttime=2013-10-01&endtime=2013-10-30&attr=city");

		for(String url:urlList){
			aThreadPool.execute(new WebFetch(url));
	
		}
		
		while(aThreadPool.getActiveCount()>0){
			
		}
		
		long costG=WebFetch.endGloble-WebFetch.beginGloble;
		aThreadPool.shutdownNow();
		System.out.println("globle:"+costG+"'ms");
	}
	
	
	
	
}
