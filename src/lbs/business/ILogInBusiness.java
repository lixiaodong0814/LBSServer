package lbs.business;

import lbs.value.UserValue;

public interface ILogInBusiness {
	boolean checkLogIn(UserValue uv) throws Exception;
	
}
