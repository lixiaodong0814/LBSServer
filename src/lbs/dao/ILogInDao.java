package lbs.dao;

import lbs.value.UserValue;

public interface ILogInDao {
	
	boolean checkLogIn(UserValue uv) throws Exception;
	
	int updateUserLocation(UserValue uv) throws Exception;
}
