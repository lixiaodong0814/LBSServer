package lbs.factory;

import lbs.dao.ICollectDao;
import lbs.dao.ICommentDao;
import lbs.dao.ILikeDao;
import lbs.dao.ILogInDao;
import lbs.dao.ILogOnDao;
import lbs.dao.INearPeopleDao;
import lbs.dao.INewsDao;
import lbs.dao.impl.CollectDaoImpl;
import lbs.dao.impl.CommentDaoImpl;
import lbs.dao.impl.LikeDaoImol;
import lbs.dao.impl.LogInDaoImpl;
import lbs.dao.impl.LogOnDaoImpl;
import lbs.dao.impl.NearPeopleDaoImpl;
import lbs.dao.impl.NewsDaoImpl;

public class DaoFactory {
	
	private DaoFactory(){}
	
	private static ILogInDao logInDao= null;
	
	private static ILogOnDao logOnDao = null;
	
	private static INewsDao newsDao = null;
	
	private static ILikeDao likeDao = null;
	
	private static ICollectDao collectDao = null;
	
	private static ICommentDao commentDao = null;
	
	private static INearPeopleDao nearPeopleDao = null;
	
	
	public static ILogInDao getLogInDao() {
		if (logInDao == null) {
			logInDao = new LogInDaoImpl();
		}
		
		return logInDao;
	}
	
	public static ILogOnDao getLogOnDao() {
		if (logOnDao == null) {
			logOnDao = new LogOnDaoImpl();
		}
		
		return logOnDao;
	}
	
	public static INewsDao getNewsDao() {
		if (newsDao == null) {
			newsDao = new NewsDaoImpl();
		}
		
		return newsDao;
	}
	
	public static ILikeDao getLikeDao() {
		if (likeDao == null) {
			likeDao = new LikeDaoImol();
		}
		
		return likeDao; 
	}
	
	public static ICollectDao getCollectDao() {
		if (collectDao == null) {
			collectDao = new CollectDaoImpl();
		}
		
		return collectDao;
	}
	
	public static ICommentDao getCommentDao() {
		if (commentDao == null) {
			commentDao = new CommentDaoImpl();
		}
		
		return commentDao;
	}
	
	
	public static INearPeopleDao getNearPeopleDao() {
		if (nearPeopleDao == null) {
			nearPeopleDao = new NearPeopleDaoImpl();
		}
		
		return nearPeopleDao;
	}
}
