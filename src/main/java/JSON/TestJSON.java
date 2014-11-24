package JSON;

import httpserver.test.JsonResponseHandler;
import httpserver.test.JsonResponseHandler.Group;
import httpserver.test.JsonResponseHandler.User;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class TestJSON {

	public static abstract class Base{
		
	}
	public static  class child1 extends Base{
		String date;
		
		public String getDate(){
			return date;
		}
		public void setDate(String date){
			
			 this.date=date;
		}
	}
	
	public static class Query{
		String date;
		String target;
		String token;
		int pageSize;
		int pageNum;
		
		String[] keyWords;
		
		public String getDate(){
			return date;
		}
		public void setDate(String date){
			
			 this.date=date;
		}
		public String getTarget(){
			return target;
		}
		public void setTarget(String target){
			 this.target=target;
		}
		public String getToken(){
			return token;
		}
		public void setToken(String token){
			 this.token=token;
		}
		
		public int getPageSize(){
			return pageSize; 
		}
		public void setPageSize(int pageSize){
			this.pageSize=pageSize;
		}
		
		public int getPageNum(){
			return pageNum;
		}
		public void setPageNum(int pageNum){
			this.pageNum=pageNum;
		}
		
		public String[] getKeyWords(){
			return keyWords;
		}
		public void setKeyWords(String[] keywords){
			this.keyWords=keywords;
		}
		public String toString(){
			
			return "date:"+date+" target:"+target+" token:"+token+" pageSize:"+pageSize+" pageNum:"+pageNum;
		
		}
	}
	
	public static void main(String[] args) throws IOException {

		Group group = new Group();
		group.setId(0L);
		group.setName("admi\"n");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		String jsonString = JSON.toJSONString(group);
		System.out.println(jsonString);

		Group group2 = JSON.parseObject(jsonString, Group.class);
		System.out.println(group2);

		String[] keywords={"key1","key2"};
		Query aQuery=new Query();
		
		aQuery.date="2012-12-12";
		aQuery.target="ÍøÒ×";
		aQuery.token="¿Óµù";
		aQuery.pageSize=10;
		aQuery.pageNum=1;
		aQuery.keyWords=keywords;
		System.out.println(aQuery);
		String jsonStrBrowserCompatible= JSON.toJSONString(aQuery,SerializerFeature.BrowserCompatible);
	
		String jsonStr= JSON.toJSONString(aQuery);
	
		System.out.println(jsonStr);
		System.out.println(jsonStrBrowserCompatible);
	
		 
		}
}
