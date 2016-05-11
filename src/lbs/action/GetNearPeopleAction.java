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

import lbs.business.INearPeopleBusiness;
import lbs.business.INewsBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.NearPeopleValue;
import lbs.value.NewsValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetNearPeopleAction
 */
@WebServlet(name="GetNearPeopleAction", value="/getNearPeople")
public class GetNearPeopleAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNearPeopleAction() {
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
		JSONObject reqObject = null;
		JSONObject respObject = null;
		INearPeopleBusiness nearPeopleBusiness = BusinessFactory.getNearPeopleBusiness();
		List<NearPeopleValue> nearPeopleList = new ArrayList<NearPeopleValue>();
		
		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		
		try {
			reqObject = new JSONObject(reqMsg);
			int pageNo = Integer.parseInt(reqObject.getString("pageNo"));
			String account = reqObject.getString("account");
			nearPeopleList = nearPeopleBusiness.getNearPeople(pageNo, account);
			respObject = new JSONObject();
			if (nearPeopleList != null) {
				respObject.put("success", 0);
				Gson gson = new Gson();
				respObject.put("nearPeople", gson.toJson(nearPeopleList));
			} else {
				respObject.put("success", -1);
			}
			
			
		} catch (JSONException e) {
			System.out.println("GetNearPeopleAction---> error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("GetNearPeopleAction---> error: " + e.getMessage());
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
