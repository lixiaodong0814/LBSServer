package lbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lbs.dao.ILogInDao;
import lbs.factory.ConnectionFactory;
import lbs.value.UserValue;

public class LogInDaoImpl implements ILogInDao {

	private Connection con;
	private String sql;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	@Override
	public boolean checkLogIn(UserValue uv) throws Exception {
		sql = "select account, password from tb_user where account = ? and password = ?";
		con = ConnectionFactory.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uv.getAccount());
			pstmt.setString(2, uv.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int result = updateUserLocation(uv);
				if (result != 0) {
					System.out.println("LogInDaoIpml-->boolean checkLogIn(UserValue uv) success");
					return true;
				}
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("LogInDaoIpml-->boolean checkLogIn(UserValue uv) error£º" + e.getMessage());
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return false;
	}

	@Override
	public int updateUserLocation(UserValue uv) throws Exception {
		sql = "update tb_user set latitude=?, longitude=?, address=? where account=?";
	//	con = ConnectionFactory.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, uv.getLatitude());
			pstmt.setDouble(2, uv.getLongitude());
			pstmt.setString(3, uv.getAddress());
			pstmt.setString(4, uv.getAccount());
			int result = pstmt.executeUpdate();
			System.out.println("int updateUserLocation(UserValue uv) success");
			return result;
		} catch (Exception e) {
			System.out.println("int updateUserLocation(UserValue uv) error£º" + e.getMessage());
			e.printStackTrace();
		} finally {
			pstmt.close();
			con.close();
		}
		
		return 0;
	}
	
	

}
