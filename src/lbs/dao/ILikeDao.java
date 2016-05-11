package lbs.dao;

import lbs.value.LikeValue;

public interface ILikeDao {
	boolean IsLiked(int nid, String likeAccount) throws Exception;
	int addLiker(LikeValue lv) throws Exception;
}
