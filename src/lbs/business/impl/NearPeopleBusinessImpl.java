package lbs.business.impl;

import java.util.List;

import lbs.business.INearPeopleBusiness;
import lbs.dao.INearPeopleDao;
import lbs.factory.DaoFactory;
import lbs.value.NearPeopleValue;

public class NearPeopleBusinessImpl implements INearPeopleBusiness {
	INearPeopleDao nearPeopleDao = DaoFactory.getNearPeopleDao();

	@Override
	public List<NearPeopleValue> getNearPeople(int pageNo, String account) throws Exception {
		return nearPeopleDao.getNearPeople(pageNo, account);
	}

}
