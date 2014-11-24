package apache_HttpComponentsClient;

import java.io.IOException;











import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;



public class TestAsyncHttpClientGet {
	
	public final static void main(String[] args) throws Exception {
        
		getSimpleHttp() ;
    }
	
	public static void getSimpleHttp() throws Exception{
		
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            httpclient.start();
            HttpGet request = new HttpGet("http://www.baidu.com/");
            Future<HttpResponse> future = httpclient.execute(request, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response) {
                  
                    System.out.println( "completed response->" + response.getStatusLine());
                    HttpEntity entity = response.getEntity();
                    
                    try {
						String content= entity != null ? EntityUtils.toString(entity) : null;
						System.out.println(Arrays.toString(response.getAllHeaders()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    System.out.println( " response handle ok");
                }

                public void failed(final Exception ex) {
                 
                    System.out.println( "failed Exception->" + ex);
                }

                public void cancelled() {
                   
                    System.out.println( " cancelled");
                }


            });
            
            //Future 可以
            //阻塞 等待 結果
            /*
             * get () throws InterruptedException, ExecutionException  等待任务执行结束，然后获得V类型的结果。InterruptedException 线程被中断异常， ExecutionException任务执行异常，如果任务被取消，还会抛出CancellationException 
*			   get (long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException 
             */
            HttpResponse response = future.get();
           // System.out.println("Response: " + response.getStatusLine());
            System.out.println("Shutting down");
            
          //  Thread.sleep(30000);
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
	}
	
	public static void getMultipleHTTP () throws Exception{
		

	        RequestConfig requestConfig = RequestConfig.custom()
	            .setSocketTimeout(3000)
	            .setConnectTimeout(3000).build();
	        
	        
	        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
	            .setDefaultRequestConfig(requestConfig)
	            .build();
	        try {
	            httpclient.start();
	            final HttpGet[] requests = new HttpGet[] {
	                    new HttpGet("http://www.apache.org/"),
	                    new HttpGet("https://www.verisign.com/"),
	                    new HttpGet("http://www.google.com/")
	            };
	            final CountDownLatch latch = new CountDownLatch(requests.length);
	            for (final HttpGet request: requests) {
	                httpclient.execute(request, new FutureCallback<HttpResponse>() {

	                    public void completed(final HttpResponse response) {
	                        latch.countDown();
	                        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
	                    }

	                    public void failed(final Exception ex) {
	                        latch.countDown();
	                        System.out.println(request.getRequestLine() + "->" + ex);
	                    }

	                    public void cancelled() {
	                        latch.countDown();
	                        System.out.println(request.getRequestLine() + " cancelled");
	                    }

					

	                });
	            }
	            latch.await();
	            System.out.println("Shutting down");
	        } finally {
	            httpclient.close();
	        }
	        System.out.println("Done");
	    }

	
}
