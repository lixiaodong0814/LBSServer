package lbs.business.impl;

import lbs.business.ILikeBusiness;
import lbs.dao.ILikeDao;
import lbs.factory.DaoFactory;
import lbs.value.LikeValue;

public class LikeBusinessImpl implements ILikeBusiness {
	ILikeDao likeDao = DaoFactory.getLikeDao();

	@Override
	public boolean IsLiked(int nid, String likeAccount) throws Exception {
		return likeDao.IsLiked(nid, likeAccount);
	}

	@Override
	public int addLiker(LikeValue lv) throws Exception {
		return likeDao.addLiker(lv);
	}

}
