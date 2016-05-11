package lbs.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lbs.business.ILogInBusiness;
import lbs.business.ILogOnBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.UserValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class LoginAction
 */
@WebServlet(name="LoginAction", value="/login")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginAction() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String reqMsg, respMsg;
	//	JSONObject reqObject = null;
		JSONObject respObject = null;

		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		try {
			JSONObject jsonObject = new JSONObject(reqMsg);
			UserValue uv = new UserValue();
			uv = new Gson().fromJson(jsonObject.getString("login"), UserValue.class);
			ILogInBusiness loginBusiness = BusinessFactory.getLogInBusiness();
			ILogOnBusiness logonBusiness = BusinessFactory.getLogOnBusiness();
			if (loginBusiness.checkLogIn(uv)) {
				Gson gson = new Gson();
				String userResp = gson.toJson(logonBusiness.getUserByAccount(uv.getAccount()));
				respObject = new JSONObject().put("sucess", 0).put("user", userResp);
			} else {
				respObject = new JSONObject().put("sucess", -1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			respMsg = respObject == null ? "" : respObject.toString();
			System.out.println("返回报文： " + respMsg);
			PrintWriter out = response.getWriter();
			out.write(respMsg);
			out.flush();
			out.close();
		}

	}

}
