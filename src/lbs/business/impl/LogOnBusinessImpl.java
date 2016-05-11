package lbs.business.impl;

import lbs.business.ILogOnBusiness;
import lbs.dao.ILogOnDao;
import lbs.factory.DaoFactory;
import lbs.value.UserValue;

public class LogOnBusinessImpl implements ILogOnBusiness {

	private ILogOnDao logOnDao = DaoFactory.getLogOnDao();
	
	@Override
	public boolean create(String account, String password) throws Exception {
		return logOnDao.create(account, password);
	}

	@Override
	public UserValue update(UserValue uv) throws Exception {
		return logOnDao.update(uv);
	}

	@Override
	public UserValue getUserByAccount(String account) throws Exception {
		return logOnDao.getUserByAccount(account);
	}

}
