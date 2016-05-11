package lbs.business;

import lbs.value.LikeValue;

public interface ILikeBusiness {
	boolean IsLiked(int nid, String likeAccount) throws Exception;
	int addLiker(LikeValue lv) throws Exception;
}
