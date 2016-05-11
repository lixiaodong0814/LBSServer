package lbs.business;

import java.util.List;

import lbs.value.CommentValue;

public interface ICommentBusiness {
	List<CommentValue> getCommentsByNewsId(int newsId, int pageNo) throws Exception;
	
	int addComments(CommentValue cv) throws Exception;
}
