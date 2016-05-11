package lbs.business.impl;

import lbs.business.ILogInBusiness;
import lbs.dao.ILogInDao;
import lbs.factory.DaoFactory;
import lbs.value.UserValue;

public class LogInBusinessImpl implements ILogInBusiness {

	private ILogInDao logInDao;
	
	@Override
	public boolean checkLogIn(UserValue uv) throws Exception {
		logInDao = DaoFactory.getLogInDao();
		return logInDao.checkLogIn(uv);
	}

}
