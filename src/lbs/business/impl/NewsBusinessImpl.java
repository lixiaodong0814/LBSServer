package lbs.business.impl;

import java.util.List;

import lbs.business.INewsBusiness;
import lbs.dao.INewsDao;
import lbs.factory.DaoFactory;
import lbs.value.MyNewsValue;
import lbs.value.NewsValue;

public class NewsBusinessImpl implements INewsBusiness {
	private INewsDao newsDao = DaoFactory.getNewsDao();

	@Override
	public List<NewsValue> getTextNews(int pageNo, String likeAccount) throws Exception {
		return newsDao.getTextNews(pageNo, likeAccount);
	}

	@Override
	public List<NewsValue> getPicNews(int pageNo, String likeAccount) throws Exception {
		return newsDao.getPicNews(pageNo, likeAccount);
	}

	@Override
	public List<NewsValue> getVideoNews(int pageNo, String likeAccount) throws Exception {
		return newsDao.getVideoNews(pageNo, likeAccount);
	}

	@Override
	public List<NewsValue> getAllNews(int pageNo, String likeAccount) throws Exception {
		return newsDao.getAllNews(pageNo, likeAccount);
	}

	@Override
	public List<MyNewsValue> getMyNewsByAccount(String account, int pageNo) throws Exception {
		return newsDao.getMyNewsByAccount(account, pageNo);
	}

	@Override
	public int insertNews(NewsValue nv) throws Exception {
		return newsDao.insertNews(nv);
	}

}
