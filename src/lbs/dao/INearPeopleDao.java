package lbs.dao;

import java.util.List;

import lbs.value.NearPeopleValue;

public interface INearPeopleDao {
	public List<NearPeopleValue> getNearPeople(int pageNo, String account) throws Exception;
}
