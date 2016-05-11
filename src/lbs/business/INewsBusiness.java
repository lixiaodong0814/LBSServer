package lbs.business;

import java.util.List;

import lbs.value.MyNewsValue;
import lbs.value.NewsValue;

public interface INewsBusiness {
	
	List<NewsValue> getTextNews(int pageNo, String likeAccount) throws Exception;

	List<NewsValue> getPicNews(int pageNo, String likeAccount) throws Exception;

	List<NewsValue> getVideoNews(int pageNo, String likeAccount) throws Exception;

	List<NewsValue> getAllNews(int pageNo, String likeAccount) throws Exception;

	List<MyNewsValue> getMyNewsByAccount(String account, int pageNo) throws Exception;
	
	int insertNews(NewsValue nv) throws Exception;
}
