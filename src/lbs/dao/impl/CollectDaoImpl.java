package lbs.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lbs.dao.ICollectDao;
import lbs.factory.ConnectionFactory;
import lbs.value.CollectValue;
import lbs.value.MyCollectValue;

public class CollectDaoImpl implements ICollectDao {

	private Connection con;
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private final int pageSize = 3;
	
	@Override
	public boolean isCollect(int nid, String collectAccount) throws Exception {
		sql = "select * "
				+ " from tb_collect "
				+ " where collectAccount=? "
				+ " and nid=?";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, collectAccount);
			pstmt.setInt(2, nid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pstmt.close();
				System.out.println("CollectDaoImpl--->boolean isCollect(int nid, String collectAccount) success");
				return true;
			}

		} catch (Exception e) {
			System.out.println("CollectDaoImpl--->boolean isCollect(int nid, String collectAccount) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}

		return false;
	}

	@Override
	public int addCollect(CollectValue cv) throws Exception {
		sql = "insert into tb_collect(nid, newsAccount, collectAccount) values(?, ?, ?)";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cv.getNid());
			pstmt.setString(2, cv.getNewsAccount());
			pstmt.setString(3, cv.getCollectAccount());
			int result = pstmt.executeUpdate();
			System.out.println("LikeDaoImol--->int addCollect(CollectValue cv) success");
			return result;

		} catch (Exception e) {
			System.out.println("CollectDaoImpl--->int addCollect(CollectValue cv) error£º" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
		}
		return 0;
	}

	@Override
	public int cancelCollect(CollectValue cv) throws Exception {
		sql = "delete from tb_collect where nid=? and collectAccount=?";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cv.getNid());
			pstmt.setString(2, cv.getCollectAccount());
			int result = pstmt.executeUpdate();
			System.out.println("LikeDaoImol--->int cancelCollect(CollectValue cv) success");
			return result;

		} catch (Exception e) {
			System.out.println("CollectDaoImpl--->int cancelCollect(CollectValue cv) error£º" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
		}
		return 0;
	}

	@Override
	public List<MyCollectValue> getMyCollectByAccount(String account, int pageNo)
			throws Exception {
		pageNo = (pageNo - 1) * pageSize;
		
		sql = "SELECT n.textContent AS textContent, n.picContent AS picContent, n.publishTime AS publishTime, "
				+ " u.headPicStr AS userPicStr, u.nickName AS nickName, "
				+ " c.id AS id, c.nid AS nid, c.newsAccount AS newsAccount "
				+ " FROM tb_collect AS c "
				+ " JOIN tb_news AS n ON c.nid=n.nid "
				+ " JOIN tb_user AS u ON n.newsAccount=u.account "
				+ " WHERE c.collectAccount=?"
				+ " LIMIT " + pageNo + ", " + pageSize;
		List<MyCollectValue> myCollectList = new ArrayList<MyCollectValue>();

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MyCollectValue mcv = new MyCollectValue();
				mcv.setTextContent(rs.getString("textContent"));
				if (rs.getBinaryStream("picContent") != null) {
					InputStream in = rs.getBinaryStream("picContent");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					mcv.setPicContent(new String(baos.toByteArray()));
				}
				mcv.setPublishTime(new java.util.Date(rs.getTimestamp("publishTime").getTime()));
				
				//ÓÃ»§Í·Ïñ
				if (rs.getBinaryStream("userPicStr") != null) {
					InputStream in2 = rs.getBinaryStream("userPicStr");
					ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
					int count2 = 0;
					byte[] buff2 = new byte[40960];
					while ((count2 = in2.read(buff2)) != -1) {
						baos2.write(buff2, 0, count2);
					}
					mcv.setUserPicStr(new String(baos2.toByteArray()));
				}
				if (rs.getString("nickName") != null) {
					mcv.setNickName(rs.getString("nickName"));
				}
				
				
				mcv.setId(rs.getInt("id"));
				mcv.setNid(rs.getInt("nid"));
				mcv.setNewsAccount(rs.getString("newsAccount"));
				
				myCollectList.add(mcv);
			}
			System.out.println("CollectDaoImpl---> List<MyCollectValue> getMyCollectByAccount(String account, int pageNo) success");
			return myCollectList;

		} catch (Exception e) {
			System.out.println("CollectDaoImpl---> List<MyCollectValue> getMyCollectByAccount(String account, int pageNo) error£º" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
			rs.close();
		}
		return null;
	}

}
