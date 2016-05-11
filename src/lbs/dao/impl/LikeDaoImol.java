package lbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lbs.dao.ILikeDao;
import lbs.dao.INewsDao;
import lbs.factory.ConnectionFactory;
import lbs.factory.DaoFactory;
import lbs.value.LikeValue;

public class LikeDaoImol implements ILikeDao {
	private Connection con;
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private INewsDao newsDao;
	
	@Override
	public boolean IsLiked(int nid, String likeAccount) throws Exception {
		sql = "select * "
				+ " from tb_like "
				+ " where likeAccount=? "
				+ " and nid=?";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, likeAccount);
			pstmt.setInt(2, nid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pstmt.close();
				System.out.println("boolean IsLiked(int nid, String likeAccount) success");
				return true;
			}
			

		} catch (Exception e) {
			System.out.println("boolean IsLiked(int nid, String likeAccount) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}

		return false;
	}

	@Override
	public int addLiker(LikeValue lv) throws Exception {
		sql = "insert into tb_like(nid, newsAccount, likeAccount) values(?, ?, ?)";
		newsDao = DaoFactory.getNewsDao();
		
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lv.getNid());
			pstmt.setString(2, lv.getNwesAccount());
			pstmt.setString(3, lv.getLikeAccount());
			int result = pstmt.executeUpdate();
			if (result != 0) {
				if (newsDao.updateLikeNumByNid(lv.getNid()) != 0) {
					System.out.println("LikeDaoImol--->int addLiker(LikeValue lv) success");
					return result;
				}
			}
			

		} catch (Exception e) {
			System.out.println("LikeDaoImol--->int addLiker(LikeValue lv) error£º" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
		}

		
		return 0;
	}

}
