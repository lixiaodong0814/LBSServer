package lbs.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lbs.business.ILogOnBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class LogonAction
 */
@WebServlet(name="LogonAction", value="/logon")
public class LogonAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogonAction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String reqMsg, respMsg;
		JSONObject respObject = null;
		JSONObject reqObject = null;

		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("LogonAction--->请求报文: " + reqMsg);
		try {
			reqObject = new JSONObject(reqMsg);
			String account = reqObject.getString("account");
			String password = reqObject.getString("password");
			ILogOnBusiness logOnBusiness = BusinessFactory.getLogOnBusiness();
			if (logOnBusiness.create(account, password)) {
				respObject = new JSONObject().put("sucess", 0);
			} else {
				respObject = new JSONObject().put("sucess", -1);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			respMsg = respObject == null ? "" : respObject.toString();
			System.out.println("LogonAction--->返回报文： " + respMsg);
			PrintWriter out = response.getWriter();
			out.write(respMsg);
			out.flush();
			out.close();
		}

	}

}
