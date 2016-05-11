package lbs.dao;

import lbs.value.UserValue;

public interface ILogOnDao {
	
	boolean create(String account, String password) throws Exception;
	
	UserValue update(UserValue uv) throws Exception;
	
	UserValue getUserByAccount(String account) throws Exception;
}
