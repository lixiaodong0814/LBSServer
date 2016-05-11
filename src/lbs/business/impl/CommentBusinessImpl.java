package lbs.business.impl;

import java.util.List;

import lbs.business.ICommentBusiness;
import lbs.dao.ICommentDao;
import lbs.factory.DaoFactory;
import lbs.value.CommentValue;

public class CommentBusinessImpl implements ICommentBusiness {
	private ICommentDao commentDao = DaoFactory.getCommentDao();
	
	@Override
	public List<CommentValue> getCommentsByNewsId(int newsId, int pageNo) throws Exception {
		return commentDao.getCommentsByNewsId(newsId, pageNo);
	}

	@Override
	public int addComments(CommentValue cv) throws Exception {
		return commentDao.addComments(cv);
	}

}
