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

import lbs.business.ICommentBusiness;
import lbs.business.INewsBusiness;
import lbs.factory.BusinessFactory;
import lbs.utils.LBSUtils;
import lbs.value.CommentValue;
import lbs.value.NewsValue;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class AddCommentsAction
 */
@WebServlet(name="AddCommentsAction", value="/addComments")
public class AddCommentsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCommentsAction() {
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

		reqMsg = LBSUtils.getRequestMsg(request);
		respObject = new JSONObject();
		System.out.println("请求报文: " + reqMsg);
		try {
			reqObject = new JSONObject(reqMsg);
			Gson gson = new Gson();
			CommentValue cv = gson.fromJson(reqObject.getString("comment"), CommentValue.class);
			int result = 0;
			if (cv != null) {
				result = commentBusiness.addComments(cv);
			}
			System.out.println("result: " + result);
			if (result > 0) {
				respObject.put("success", 0);
			} else {
				respObject.put("success", -1);
			}
			
		} catch (JSONException e) {
			System.out.println("AddCommentsAction--->JSONException error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("AddCommentsAction--->Exception error: " + e.getMessage());
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
