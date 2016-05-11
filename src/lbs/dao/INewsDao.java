package lbs.dao;


import java.util.List;

import lbs.value.MyNewsValue;
import lbs.value.NewsValue;

public interface INewsDao {

	List<NewsValue> getTextNews(int pageNo, String likeAccount) throws Exception;
	
	List<NewsValue> getPicNews(int pageNo, String likeAccount) throws Exception;
	
	List<NewsValue> getVideoNews(int pageNo, String likeAccount) throws Exception;
	
	List<NewsValue> getAllNews(int pageNo, String likeAccount) throws Exception;
	
	List<MyNewsValue> getMyNewsByAccount(String account, int pageNo) throws Exception;
	
	int insertNews(NewsValue nv) throws Exception;
	
	NewsValue getInsertedNews(NewsValue nv) throws Exception;
	
	int updateLikeNumByNid(int nid) throws Exception;
	
	int updateCommentNumByNid(int nid) throws Exception;
	
}
