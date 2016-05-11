package lbs.business;

import java.util.List;

import lbs.value.NearPeopleValue;

public interface INearPeopleBusiness {
	List<NearPeopleValue> getNearPeople(int pageNo, String account) throws Exception;
}
