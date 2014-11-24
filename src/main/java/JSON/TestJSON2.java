package JSON;

import httpserver.test.JsonResponseHandler;
import httpserver.test.JsonResponseHandler.Group;
import httpserver.test.JsonResponseHandler.User;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.alibaba.fastjson.JSON;


public class TestJSON2 {


	
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
	
		// jsonString2= JSON.toJSONString(base);
		//System.out.println(jsonString2);
		
		}
}
