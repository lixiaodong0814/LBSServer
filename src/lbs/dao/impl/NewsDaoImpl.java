package lbs.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.sun.jmx.snmp.Timestamp;

import lbs.dao.ICollectDao;
import lbs.dao.ILikeDao;
import lbs.dao.INewsDao;
import lbs.factory.ConnectionFactory;
import lbs.factory.DaoFactory;
import lbs.value.MyNewsValue;
import lbs.value.NewsValue;

public class NewsDaoImpl implements INewsDao {

	private Connection con;
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private List<NewsValue> newsList;
	private int pageSize = 2;
	private ILikeDao likeDao = DaoFactory.getLikeDao();
	private ICollectDao collectDao = DaoFactory.getCollectDao();

	@Override
	public List<NewsValue> getTextNews(int pageNo, String likeAccount) throws Exception {
		pageNo = (pageNo - 1) * pageSize;
		
		sql = "select n.nid as nid, n.textContent as text, n.newsAccount as account, n.publishTime as publishTime, "
				+ " n.likeNum as likeNum, n.commentNum as commentNum, n.latitude as latitude, n.longitude as longitude, n.address as address, "
				+ " u.headPicStr as accountPic, u.nickName as accountNickName "
				+ " from tb_news as n, tb_user as u "
				+ " where account=u.account and n.picContent is null"
				+ " LIMIT " + pageNo + ", " + pageSize;

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			newsList = new ArrayList<NewsValue>();

			while (rs.next()) {
				NewsValue nv = new NewsValue();
				int nid = rs.getInt("nid");
				nv.setId(nid);
				//是否赞过
				nv.setLike(likeDao.IsLiked(nid, likeAccount));
				//是否收藏
				nv.setCollect(collectDao.isCollect(nid, likeAccount));;
				nv.setTextContent(rs.getString("text"));
				nv.setNewsAccount(rs.getString("account"));
				nv.setPublishTime(new java.util.Date(rs.getTimestamp("publishTime").getTime()));
				
				nv.setLikeNum(rs.getInt("likeNum"));
				nv.setCommentNum(rs.getInt("commentNum"));
				nv.setMyAddress(rs.getString("address"));
				nv.setLatitude(rs.getDouble("latitude"));
				nv.setLongitude(rs.getDouble("longitude"));
				
				if (rs.getBinaryStream("accountPic") != null) {
					InputStream in = rs.getBinaryStream("accountPic");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					nv.setAccountPic(new String(baos.toByteArray()));
					nv.setAccountNickName(rs.getString("accountNickName"));
				}
				if (rs.getString("accountNickName") != null) {
					nv.setAccountNickName(rs.getString("accountNickName"));
				}
				
				newsList.add(nv);
			}
			pstmt.close();
			System.out.println("NewsDaoImpl--->List<NewsValue> getTextNews(int pageNo, String likeAccount) success");

		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->List<NewsValue> getTextNews(int pageNo, String likeAccount) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}


		return newsList;
	}

	@Override
	public List<NewsValue> getPicNews(int pageNo, String likeAccount) throws Exception {
		pageNo = (pageNo - 1) * pageSize;
		
		sql = "select n.nid as nid, n.textContent as text, n.picContent as pic, n.newsAccount as account, n.publishTime as publishTime, "
				+ " n.likeNum as likeNum, n.commentNum as commentNum, n.latitude as latitude, n.longitude as longitude, n.address as address, "
				+ " u.headPicStr as accountPic, u.nickName as accountNickName"
				+ " from tb_news as n, tb_user as u "
				+ " where account=u.account and n.picContent is not null"
				+ " LIMIT " + pageNo + ", " + pageSize;

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			newsList = new ArrayList<NewsValue>();
			while (rs.next()) {
				NewsValue nv = new NewsValue();
				int nid = rs.getInt("nid");
				nv.setId(nid);
				//是否赞过
				nv.setLike(likeDao.IsLiked(nid, likeAccount));
				//是否收藏
				nv.setCollect(collectDao.isCollect(nid, likeAccount));;
				nv.setTextContent(rs.getString("text"));
				nv.setNewsAccount(rs.getString("account"));

				//获取图片资源
				InputStream in2 = rs.getBinaryStream("pic");
				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
				int count2 = 0;
				byte[] buff2 = new byte[40960];
				while ((count2 = in2.read(buff2)) != -1) {
					baos2.write(buff2, 0, count2);
				}
				nv.setPicContent(new String(baos2.toByteArray()));
				nv.setPublishTime(new java.util.Date(rs.getTimestamp("publishTime").getTime()));
				
				nv.setLikeNum(rs.getInt("likeNum"));
				nv.setCommentNum(rs.getInt("commentNum"));
				nv.setMyAddress(rs.getString("address"));
				nv.setLatitude(rs.getDouble("latitude"));
				nv.setLongitude(rs.getDouble("longitude"));
				
				if (rs.getBinaryStream("accountPic") != null) {
					InputStream in = rs.getBinaryStream("accountPic");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					nv.setAccountPic(new String(baos.toByteArray()));
					nv.setAccountNickName(rs.getString("accountNickName"));
				}
				if (rs.getString("accountNickName") != null) {
					nv.setAccountNickName(rs.getString("accountNickName"));
				}
				newsList.add(nv);
			}
			pstmt.close();
			System.out.println("NewsDaoImpl--->List<NewsValue> getPicNews(int pageNo, String likeAccount) success");
		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->List<NewsValue> getPicNews(int pageNo, String likeAccount) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}


		return newsList;
	}

	@Override
	public List<NewsValue> getVideoNews(int pageNo, String likeAccount) throws Exception {
		return null;
	}

	@Override
	public List<NewsValue> getAllNews(int pageNo, String likeAccount) throws Exception {
		pageNo = (pageNo - 1) * pageSize;
		
		sql = "select n.nid as nid, n.textContent as text, n.picContent as pic, n.newsAccount as account, n.publishTime as publishTime, "
				+ " n.likeNum as likeNum, n.commentNum as commentNum, n.latitude as latitude, n.longitude as longitude, n.address as address, "
				+ " u.headPicStr as accountPic, u.nickName as accountNickName"
				+ " from tb_news as n, tb_user as u "
				+ " where account=u.account"
				+ " LIMIT " + pageNo + ", " + pageSize;

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			newsList = new ArrayList<NewsValue>();

			while (rs.next()) {
				NewsValue nv = new NewsValue();
				int nid = rs.getInt("nid");
				nv.setId(nid);
				//是否赞过
				nv.setLike(likeDao.IsLiked(nid, likeAccount));
				//是否收藏
				nv.setCollect(collectDao.isCollect(nid, likeAccount));;
				nv.setTextContent(rs.getString("text"));
				nv.setNewsAccount(rs.getString("account"));
				//获取图片资源
				if (rs.getBinaryStream("pic") != null) {
					InputStream in2 = rs.getBinaryStream("pic");
					ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
					int count2 = 0;
					byte[] buff2 = new byte[40960];
					while ((count2 = in2.read(buff2)) != -1) {
						baos2.write(buff2, 0, count2);
					}
					nv.setPicContent(new String(baos2.toByteArray()));
				}
				nv.setPublishTime(new java.util.Date(rs.getTimestamp("publishTime").getTime()));
				
				nv.setLikeNum(rs.getInt("likeNum"));
				nv.setCommentNum(rs.getInt("commentNum"));
				nv.setMyAddress(rs.getString("address"));
				nv.setLatitude(rs.getDouble("latitude"));
				nv.setLongitude(rs.getDouble("longitude"));

				//获取账号头像
				if (rs.getBinaryStream("accountPic") != null) {
					InputStream in = rs.getBinaryStream("accountPic");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					nv.setAccountPic(new String(baos.toByteArray()));
				}
				if (rs.getString("accountNickName") != null) {
					nv.setAccountNickName(rs.getString("accountNickName"));
				}
				
				newsList.add(nv);
			}
			pstmt.close();
			System.out.println("NewsDaoImpl--->List<NewsValue> getAllNews(int pageNo, String likeAccount) success");
		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->List<NewsValue> getAllNews(int pageNo, String likeAccount) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}


		return newsList;
	}

	@Override
	public List<MyNewsValue> getMyNewsByAccount(String account, int pageNo) throws Exception {
		pageNo = (pageNo - 1) * pageSize;
		
		sql = "select n.nid as nid, n.textContent as text, n.picContent as pic, n.publishTime as publishTime "
				+ " from tb_news as n, tb_user as u "
				+ " where account=u.account and account=?"
				+ " LIMIT " + pageNo + ", " + pageSize;
		
		List<MyNewsValue> myNewsList = new ArrayList<MyNewsValue>();
		
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MyNewsValue mnv = new MyNewsValue();
				mnv.setId(rs.getInt("nid"));
				mnv.setTextContent(rs.getString("text"));

				//获取图片资源
				if (rs.getBinaryStream("pic") != null) {
					InputStream in2 = rs.getBinaryStream("pic");
					ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
					int count2 = 0;
					byte[] buff2 = new byte[40960];
					while ((count2 = in2.read(buff2)) != -1) {
						baos2.write(buff2, 0, count2);
					}
					mnv.setPicContent(new String(baos2.toByteArray()));
				}
				mnv.setPublishTime(new java.util.Date(rs.getTimestamp("publishTime").getTime()));
				myNewsList.add(mnv);
			}
			pstmt.close();
			System.out.println("NewsDaoImpl--->List<MyNewsValue> getMyNewsByAccount(String account, int pageNo) success");
		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->List<MyNewsValue> getMyNewsByAccount(String account, int pageNo) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}


		return myNewsList;
	}

	@Override
	public int insertNews(NewsValue nv) throws Exception {
		sql = "insert into tb_news(textContent, picContent, newsAccount, publishTime, address, latitude, longitude) values(?, ?, ?, ?, ?, ?, ?)";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nv.getTextContent());
			
			if (nv.getPicContent() != null) {
				InputStream in = new ByteArrayInputStream(nv.getPicContent().getBytes());
				pstmt.setBinaryStream(2, in);
			} else {
				pstmt.setBinaryStream(2, null);
			}
			pstmt.setString(3, nv.getNewsAccount());
			pstmt.setTimestamp(4, new java.sql.Timestamp(nv.getPublishTime().getTime()));
			pstmt.setString(5, nv.getMyAddress());
			pstmt.setDouble(6, nv.getLatitude());
			pstmt.setDouble(7, nv.getLongitude());
			int result = pstmt.executeUpdate();

			if (result != 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()&& rs != null) {
					return rs.getInt(1);
				}
			}
			pstmt.close();
			System.out.println("NewsDaoImpl--->int insertNews(NewsValue nv) success");
		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->int insertNews(NewsValue nv) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}
		return 0;
	}

	@Override
	public NewsValue getInsertedNews(NewsValue nv) throws Exception {
		sql = "select u.nickName as accountNickName, u.headPicStr as accountPic"
				+ " from tb_user as u, tb_news as n "
				+ " where u.account=n.newsAccount and n.nid=?";
		
		System.out.println("刚刚插入的数据id: " + nv.getId());
		
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nv.getId());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				//获取账号头像
				if (rs.getBinaryStream("accountPic") != null) {

					InputStream in = rs.getBinaryStream("accountPic");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					nv.setAccountPic(new String(baos.toByteArray()));
				}
				System.out.println("刚刚插入的accountNickName: " + rs.getString("accountNickName"));
				nv.setAccountNickName(rs.getString("accountNickName"));
			}
			
			pstmt.close();
			return nv;
		} catch (Exception e) {
			System.out.println("NewsValue getInsertedNEws(NewsValue nv)--->error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}
		
		return null;
	}

	@Override
	public int updateLikeNumByNid(int nid) throws Exception {
		sql = "update tb_news set likeNum=likeNum+1 where nid=?";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nid);
			int result = pstmt.executeUpdate();
			System.out.println("NewsDaoImpl--->int updateLikeNumByNid(int nid) success");
			return result;

		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->int updateLikeNumByNid(int nid) error：" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
		}
		return 0;
	}

	@Override
	public int updateCommentNumByNid(int nid) throws Exception {
		sql = "update tb_news set commentNum=commentNum+1 where nid=?";

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nid);
			int result = pstmt.executeUpdate();
			System.out.println("NewsDaoImpl--->int updateCommentNumByNid(int nid) success");
			return result;

		} catch (Exception e) {
			System.out.println("NewsDaoImpl--->int updateCommentNumByNid(int nid) error：" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
		}
		return 0;
	}

}
