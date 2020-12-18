package Home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.jasper.tagplugins.jstl.core.Out;

/**
 * Servlet implementation class Tools
 */
@WebServlet("/Tools")
public class Tools extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Tools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String paString = request.getParameter("Method").toString();
		if (paString.equals("SendRan")) {
			String ph = request.getParameter("PhoneNum").toString();
			String Ran = ranString();
			 //将数据存储到session中
			SendMess(ph, Ran);
			System.out.println(Ran);
			 dic.put(ph, Ran);
		}
		
		else {
			String phoneNumString = request.getParameter("Username").toString();
			String passWordString = request.getParameter("Password").toString();
			
			System.out.println(phoneNumString);
			String TransRan =request.getParameter("Ran").toString();
			String bString = dic.get(phoneNumString);
			System.out.println(TransRan+":::"+bString);
			 String jsonstr="{\"code\":0,\"msg\":\"注册失败\"}";
				if (bString.equals(TransRan)) {
				// 两次验证码相同 则注册，否者直接返回
					RegUser reg=new RegUser();
				try {
					boolean suc=reg.regval(phoneNumString, passWordString);
					if(suc)
					jsonstr="{\"code\":1,\"msg\":\"注册成功\"}";

				} catch (SQLException e) {
					
					// TODO Auto-generated catch block
					 e.printStackTrace();
				}
				
				
			} else {
				
				jsonstr="{\"code\":0,\"msg\":\"验证码输入不正确\"}";
			
			}
			        response.setContentType("text/html;charset=utf-8");
			        PrintWriter out=response.getWriter();
			        System.out.println(jsonstr);
			        out.print(jsonstr);
			        out.flush();
			        out.close();
		}
		doGet(request, response);
	}

	HashMap<String, String> dic = new HashMap<String, String>();

	private void SendMess(String PhoneNumber, String ranString) {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
			NameValuePair[] data = { new NameValuePair("Uid", "liwei233"),
					new NameValuePair("Key", "d41d8cd98f00b204e980"), new NameValuePair("smsMob", PhoneNumber),
					new NameValuePair("smsText", "【李伟中文网】您的短信验证码是:" + ranString) };
			post.setRequestBody(data);

			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statcode = post.getStatusCode(); // 返回状态码 检测短信是否发送成功
			System.out.println("状态码:" + statcode);

			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
			System.out.println(result); // 打印返回消息状态

			post.releaseConnection();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String ranString() {
		int n = 0;
		while (n < 100000) {
			n = (int) (Math.random() * 1000000);
		}
		return Integer.toString(n);

	}
}
