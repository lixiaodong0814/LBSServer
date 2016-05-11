package lbs.factory;

import lbs.business.ICollectBusiness;
import lbs.business.ICommentBusiness;
import lbs.business.ILikeBusiness;
import lbs.business.ILogInBusiness;
import lbs.business.ILogOnBusiness;
import lbs.business.INearPeopleBusiness;
import lbs.business.INewsBusiness;
import lbs.business.impl.CollectBusinessImpl;
import lbs.business.impl.CommentBusinessImpl;
import lbs.business.impl.LikeBusinessImpl;
import lbs.business.impl.LogInBusinessImpl;
import lbs.business.impl.LogOnBusinessImpl;
import lbs.business.impl.NearPeopleBusinessImpl;
import lbs.business.impl.NewsBusinessImpl;
import lbs.dao.impl.NewsDaoImpl;

public class BusinessFactory {
	
	private BusinessFactory() {}
	
	private static ILogInBusiness logInBusiness = null;
	
	private static ILogOnBusiness logOnBusiness = null;
	
	private static INewsBusiness newsBusiness = null;
	
	private static ILikeBusiness likeBusiness = null;
	
	private static ICollectBusiness collectBusiness = null;
	
	private static ICommentBusiness commentBusiness = null;
	
	private static INearPeopleBusiness nearPeopleBusiness = null;
	
	public static ILogInBusiness getLogInBusiness() {
		if (logInBusiness == null) {
			logInBusiness = new LogInBusinessImpl();
		}
		
		return logInBusiness;
	}
	
	public static ILogOnBusiness getLogOnBusiness() {
		if (logOnBusiness == null) {
			logOnBusiness = new LogOnBusinessImpl();
		}
		
		return logOnBusiness;
	}
	
	public static INewsBusiness getNewsBusiness() {
		if (newsBusiness == null) {
			newsBusiness = new NewsBusinessImpl();
		}
		
		return newsBusiness;
	}
	
	public static ILikeBusiness getLikeBusiness() {
		if (likeBusiness == null) {
			likeBusiness = new LikeBusinessImpl();
		}
		
		return likeBusiness;
	}
	
	public static ICollectBusiness getCollectBusiness() {
		if (collectBusiness == null) {
			collectBusiness = new CollectBusinessImpl();
		}
		
		return collectBusiness;
	}
	
	public static ICommentBusiness getCommentBusiness() {
		if (commentBusiness == null) {
			commentBusiness = new CommentBusinessImpl();
		}
		
		return commentBusiness;
	}
	
	public static INearPeopleBusiness getNearPeopleBusiness() {
		if (nearPeopleBusiness == null) {
			nearPeopleBusiness = new NearPeopleBusinessImpl();
		}
		
		return nearPeopleBusiness;
	}
}
