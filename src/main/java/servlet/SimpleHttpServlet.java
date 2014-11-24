package servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SimpleHttpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Map<String, String> parameters = new HashMap<String, String>();
	int count = 0;

	public SimpleHttpServlet() {

		super();

	}

	public void init() throws ServletException {
		
		Enumeration<String> parameterNames = this.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			String value = this.getInitParameter(parameterName);

			parameters.put(parameterName, value);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("good evening, user :"+count);
		
		response.getWriter().println("----------- parameters -------------");
		for(Entry<String, String> entry:parameters.entrySet()){
			response.getWriter().println(entry.getKey()+" : "+entry.getValue());
		}
		response.getWriter().println("----------- Session -------------");
		HttpSession session=request.getSession(true);
		
		response.getWriter().println("sessionID:"+session.getId());
		
		session.getAttributeNames();
		
		String msg=null;
		Integer accessCount=(Integer)session.getAttribute("accessCount");
		if(accessCount==null){
			accessCount=new Integer(0);
			msg="Welcome, NewComer!";
		}else{
			accessCount=new Integer(accessCount.intValue()+1);
			msg="Welcome,back! accessCount:"+accessCount;
		}
		session.setAttribute("accessCount", accessCount);
		
		response.getWriter().println("msg:"+msg);
		
		
		count++;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);// ��post��Ӧ�û����� Ȼ��ҵ���߼��Ӱ�doget��д
	}

}
