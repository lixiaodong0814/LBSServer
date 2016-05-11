package lbs.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lbs.business.ICollectBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.CollectValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class CancelCollectAction
 */
@WebServlet(name="CancelCollectAction", value="/cancelCollect")
public class CancelCollectAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelCollectAction() {
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
		ICollectBusiness collectBusiness = BusinessFactory.getCollectBusiness();
		
		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		
		try {
			reqObject = new JSONObject(reqMsg);
			String collect = reqObject.getString("collect");
			Gson gson = new Gson();
			CollectValue cv = gson.fromJson(collect, CollectValue.class);
			
			int result = collectBusiness.cancelCollect(cv);
			respObject = new JSONObject();
			
			if (result > 0) {
				respObject.put("success", 0);
			} else {
				respObject.put("success", -1);
			}
			
		} catch (JSONException e) {
			System.out.println("CancelCollectAction---> error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("CancelCollectAction---> error: " + e.getMessage());
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
