package lbs.business.impl;

import java.util.List;

import lbs.business.ICollectBusiness;
import lbs.dao.ICollectDao;
import lbs.factory.DaoFactory;
import lbs.value.CollectValue;
import lbs.value.MyCollectValue;

public class CollectBusinessImpl implements ICollectBusiness {
	ICollectDao collectDao = DaoFactory.getCollectDao();

	@Override
	public boolean isCollect(int nid, String collectAccount) throws Exception {
		return collectDao.isCollect(nid, collectAccount);
	}

	@Override
	public int addCollect(CollectValue cv) throws Exception {
		return collectDao.addCollect(cv);
	}

	@Override
	public int cancelCollect(CollectValue cv) throws Exception {
		return collectDao.cancelCollect(cv);
	}

	@Override
	public List<MyCollectValue> getMyCollectByAccount(String account, int pageNo)
			throws Exception {
		return collectDao.getMyCollectByAccount(account, pageNo);
	}

}
