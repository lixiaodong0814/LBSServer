package lbs.dao;

import java.util.List;

import lbs.value.CommentValue;

public interface ICommentDao {
	List<CommentValue> getCommentsByNewsId(int newsId, int pageNo) throws Exception;
	
	int addComments(CommentValue cv) throws Exception;
}
