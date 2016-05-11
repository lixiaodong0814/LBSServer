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
import lbs.utils.Base64Coder;
import lbs.utils.LBSUtils;
import lbs.value.UserValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class UpdateUserAction
 */
@WebServlet(name="UpdateUserAction",value="/updateUser")
public class UpdateUserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserAction() {
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
		String reqMsg = null;
		String respMsg = null;

		reqMsg = LBSUtils.getRequestMsg(request);
		Gson gson = new Gson();
		UserValue uv = new UserValue();
		uv = gson.fromJson(reqMsg, UserValue.class);
		System.out.println("UpdateUserAction--->请求报文: " + reqMsg);
		
		
		
		try {
			ILogOnBusiness logonBusiness = BusinessFactory.getLogOnBusiness();
			uv = logonBusiness.update(uv);
			Gson gsonResp = new Gson();
			respMsg = gsonResp.toJson(logonBusiness.getUserByAccount(uv.getAccount()));
			System.out.println("UpdateUserAction--->返回报文： " + respMsg);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			PrintWriter out = response.getWriter();
			out.write(respMsg);
			out.flush();
			out.close();
		}
	}

}
