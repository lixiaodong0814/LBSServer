package lbs.value;

import java.io.Serializable;

public class LikeValue implements Serializable {
	int id;
	int nid;
	String nwesAccount;
	String likeAccount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public String getNwesAccount() {
		return nwesAccount;
	}
	public void setNwesAccount(String nwesAccount) {
		this.nwesAccount = nwesAccount;
	}
	public String getLikeAccount() {
		return likeAccount;
	}
	public void setLikeAccount(String likeAccount) {
		this.likeAccount = likeAccount;
	}
	
}
