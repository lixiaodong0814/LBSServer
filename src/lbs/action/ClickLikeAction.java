package lbs.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lbs.business.ILikeBusiness;
import lbs.business.INewsBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.LikeValue;
import lbs.value.NewsValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class ClickLikeAction
 */
@WebServlet(name="ClickLikeAction", value="/clickLike")
public class ClickLikeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClickLikeAction() {
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
		ILikeBusiness likeBusiness = BusinessFactory.getLikeBusiness();
		
		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		
		try {
			reqObject = new JSONObject(reqMsg);
			String like = reqObject.getString("like");
			Gson gson = new Gson();
			LikeValue lv = gson.fromJson(like, LikeValue.class);
			int result = likeBusiness.addLiker(lv);
			respObject = new JSONObject();
			
			if (result > 0) {
				respObject.put("success", 0);
			} else {
				respObject.put("success", -1);
			}
			
		} catch (JSONException e) {
			System.out.println("ClickLikeAction---> error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ClickLikeAction---> error: " + e.getMessage());
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
