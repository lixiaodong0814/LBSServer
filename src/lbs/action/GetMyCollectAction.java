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

import lbs.business.ICollectBusiness;
import lbs.business.INewsBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.MyCollectValue;
import lbs.value.MyNewsValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetMyCollectAction
 */
@WebServlet(name="GetMyCollectAction", value="/getMyCollect")
public class GetMyCollectAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyCollectAction() {
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
		ICollectBusiness collectBusiness = BusinessFactory.getCollectBusiness();
		List<MyCollectValue> myCollectList = new ArrayList<MyCollectValue>();

		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		try {
			reqObject = new JSONObject(reqMsg);
			
				String account = reqObject.getString("account");
				int pageNo = Integer.parseInt(reqObject.getString("pageNo"));
				myCollectList = collectBusiness.getMyCollectByAccount(account, pageNo);
			
			respObject = new JSONObject();
			if (myCollectList != null ) {
				respObject.put("success", 0);
				respObject.put("myCollect", new Gson().toJson(myCollectList));
			} else {
				respObject.put("success", -1);
			}
			
		} catch (JSONException e) {
			System.out.println("GetMyCollectAction---> error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("GetMyCollectAction---> error: " + e.getMessage());
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
