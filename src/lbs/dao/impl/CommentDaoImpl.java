package lbs.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lbs.dao.ICommentDao;
import lbs.dao.INewsDao;
import lbs.factory.ConnectionFactory;
import lbs.factory.DaoFactory;
import lbs.value.CommentValue;

public class CommentDaoImpl implements ICommentDao {
	private Connection con;
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private List<CommentValue> commentList;
	private final int pageSize = 3;
	private INewsDao newsDao;

	@Override
	public List<CommentValue> getCommentsByNewsId(int newsId, int pageNo) throws Exception {
		pageNo = (pageNo -1) * pageSize;

		sql = "SELECT u.nickName AS nickName, u.headPicStr AS headPicStr, "
				+ " c.cid AS cid, c.nid AS newsId, c.commentAccount AS commentAccount, c.commentContent AS commentContent, c.commentTime AS commentTime "
				+ " FROM tb_comment AS c "
				+ " JOIN tb_user AS u ON u.account=c.commentAccount "
				+ " JOIN tb_news AS n ON c.nid=n.nid "
				+ " WHERE c.nid=?"
				+ " LIMIT " + pageNo + ", " + pageSize;
		commentList = new ArrayList<CommentValue>();

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newsId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CommentValue cv = new CommentValue();
				cv.setId(rs.getInt("cid"));
				cv.setCommentAccount(rs.getString("commentAccount"));
				cv.setCommentContent(rs.getString("commentContent"));
				if (rs.getBinaryStream("headPicStr") != null) {
					InputStream in = rs.getBinaryStream("headPicStr");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					cv.setCommentAccountPicStr(new String(baos.toByteArray()));
				}
				if (rs.getString("nickName") != null) {
					cv.setCommentNickName(rs.getString("nickName"));
				}
				cv.setCommentTime(new java.util.Date(rs.getTimestamp("commentTime").getTime()));
				commentList.add(cv);
			}
			System.out.println("CommentDaoImpl--->List<CommentValue> getCommentsByNewsId(int newsId) success");
			return commentList;

		} catch (Exception e) {
			System.out.println("CommentDaoImpl--->List<CommentValue> getCommentsByNewsId(int newsId) error£º" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
			rs.close();
		}

		return null;
	}

	@Override
	public int addComments(CommentValue cv) throws Exception {
		sql = "insert into tb_comment(nid, commentAccount, commentContent, commentTime) values(?, ?, ?, ?)";
		newsDao = DaoFactory.getNewsDao();
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cv.getNewsId());
			pstmt.setString(2, cv.getCommentAccount());
			pstmt.setString(3, cv.getCommentContent());
			pstmt.setTimestamp(4, new java.sql.Timestamp(cv.getCommentTime().getTime()));
			int result = pstmt.executeUpdate();
			if (result != 0) {
				if (newsDao.updateCommentNumByNid(cv.getNewsId()) != 0) {
					System.out.println("CommentDaoImpl--->int addComments(CommentValue cv) success");
					return result;
				}
			}

		} catch (Exception e) {
			System.out.println("CommentDaoImpl--->int addComments(CommentValue cv) error£º" + e.getMessage());
		} finally {
			pstmt.close();
			con.close();
		}

		return 0;
	}

}
