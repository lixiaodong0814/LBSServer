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
import javax.xml.stream.events.Comment;

import lbs.business.ICommentBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.CommentValue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetCommentsAction
 */
@WebServlet(name="GetCommentsAction", value="/getComments")
public class GetCommentsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommentsAction() {
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
		ICommentBusiness commentBusiness = BusinessFactory.getCommentBusiness();
		List<CommentValue> commentList = new ArrayList<CommentValue>();
		
		reqMsg = LBSUtils.getRequestMsg(request);
		System.out.println("请求报文: " + reqMsg);
		try {
			reqObject = new JSONObject(reqMsg);
			int newsId = Integer.parseInt(reqObject.getString("newsId"));
			int pageNo = Integer.parseInt(reqObject.getString("pageNo"));
			commentList = commentBusiness.getCommentsByNewsId(newsId, pageNo);
			
			respObject = new JSONObject();
			if (commentList != null ) {
				respObject.put("success", 0);
				respObject.put("commentList", new Gson().toJson(commentList));
			} else {
				respObject.put("success", -1);
			}
			
		} catch (JSONException e) {
			System.out.println("GetNewsAction---> error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("GetNewsAction---> error: " + e.getMessage());
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
