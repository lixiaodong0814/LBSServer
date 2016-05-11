package lbs.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lbs.business.INewsBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.MyNewsValue;
import lbs.value.NewsValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetMyNewsAction
 */
@WebServlet(name="GetMyNewsAction", value="/getMyNews")
public class GetMyNewsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyNewsAction() {
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
		JSONObject reqObject = null;
		JSONObject respObject = null;
		INewsBusiness newsBusiness = BusinessFactory.getNewsBusiness();
		List<MyNewsValue> myNewsList = new ArrayList<MyNewsValue>();

		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		try {
			reqObject = new JSONObject(reqMsg);
			String type = reqObject.getString("type");
			
			if (type.equals("self")) {
				String account = reqObject.getString("account");
				int pageNo = Integer.parseInt(reqObject.getString("pageNo"));
				myNewsList = newsBusiness.getMyNewsByAccount(account, pageNo);
			}
			
			respObject = new JSONObject();
			if (myNewsList != null ) {
				respObject.put("success", 0);
				respObject.put("myNews", new Gson().toJson(myNewsList));
			} else {
				respObject.put("success", -1);
			}
			
		} catch (JSONException e) {
			System.out.println("GetMyNewsAction---> error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("GetMyNewsAction---> error: " + e.getMessage());
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
