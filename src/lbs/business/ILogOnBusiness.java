package lbs.business;

import lbs.value.UserValue;

public interface ILogOnBusiness {
	
	boolean create(String account, String password) throws Exception;
	
	UserValue update(UserValue uv) throws Exception;
	
	UserValue getUserByAccount(String account) throws Exception;
}
