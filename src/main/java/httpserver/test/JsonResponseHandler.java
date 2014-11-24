package httpserver.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class JsonResponseHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange xchg) throws IOException {

		System.out.println("handle called...");

		Map<String, Object> params = (Map<String, Object>) xchg.getAttribute("parameters");
		for (Entry<String, Object> entry : params.entrySet()) {
			System.out.println("Param " + entry);
		}
		// System.out.println("called..." + xchg.getProtocol());
		// System.out.println("called..." + xchg.getRequestMethod());

		// byte[] datas=response.toString().getBytes("utf-8");
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		String jsonString = JSON.toJSONString(group);

		byte[] datas = jsonString.getBytes("utf-8");

		xchg.sendResponseHeaders(200, datas.length);
		OutputStream os = xchg.getResponseBody();
		os.write(datas);
		os.close();

		System.out.println("handle end...");

	}

	public static class User {
		private Long id;
		private String name;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return "ID:" + id + ",NAME:" + name;
		}
	}

	public static class Group {
		private Long id;
		private String name;
		private List<User> users = new ArrayList<User>();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}

		public String toString() {
			StringBuilder aStringBuilder = new StringBuilder();
			aStringBuilder.append("[");
			for (User user : users) {
				aStringBuilder.append(user.toString() + ",");
			}
			aStringBuilder.append("]");
			return "ID:" + id + ",NAME:" + name + " -- " + aStringBuilder.toString();
		}
	}

	public static void main(String[] args) throws IOException {

	

		// 允许最大连接数
		int backLog = 10;
		HttpServer server = HttpServer.create(new InetSocketAddress("192.168.136.144", 8080), backLog);

		HttpContext context = server.createContext("/search", new JsonResponseHandler());
		context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("HttpServer start OK");

	}

}
