package lbs.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import lbs.dao.ILogOnDao;
import lbs.factory.ConnectionFactory;
import lbs.value.UserValue;

public class LogOnDaoImpl implements ILogOnDao {

	private Connection con;
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public boolean create(String account, String password) throws Exception {
		sql = "insert into tb_user (account, password) values(?, ?)";
		
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account);
			pstmt.setString(2, password);
			int result = pstmt.executeUpdate();
			pstmt.close();
			if (result != 0) {
					return true;
			}
			
		} catch (Exception e) {
			System.out.println("LogOnDaoImpl.create(String account, String password) error£º" + e.getMessage());
		} finally {
			con.close();
		}

		return false;
	}

	@Override
	public UserValue update(UserValue uv) throws Exception {
		sql = "update tb_user set nickName=?, sex=?, telephone=?, headPicStr=?, picName=?, picPath=? where account=?";
		
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uv.getNickName());
			pstmt.setString(2, uv.getSex());
			pstmt.setString(3, uv.getTelephone());
			InputStream in = new ByteArrayInputStream(uv.getHeadPicStr().getBytes());
			pstmt.setBinaryStream(4, in);
		//	pstmt.setString(4, uv.getHeadPicStr());
			pstmt.setString(5, uv.getPicName());
			pstmt.setString(6, uv.getPicPath());
			pstmt.setString(7, uv.getAccount());
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println("LogOnDaoImpl.update(UserValue uv) error: " + e.getMessage());
		} finally {
			con.close();
		}
		return getUserByAccount(uv.getAccount());
	}

	@Override
	public UserValue getUserByAccount(String account) throws Exception {
		sql = "select * from tb_user where account=?";
		UserValue uv = new UserValue();
		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				uv.setId(rs.getInt("id"));
				uv.setAccount(rs.getString("account"));
				uv.setPassword(rs.getString("password"));
				if (rs.getString("nickName") != null) {
					uv.setNickName(rs.getString("nickName"));
				}
				if (rs.getString("sex") != null) {
					uv.setSex(rs.getString("sex"));
				}
				if (rs.getString("telephone") != null) {
					uv.setTelephone(rs.getString("telephone"));
				}
				uv.setAddress(rs.getString("address"));
				uv.setLatitude(rs.getDouble("latitude"));
				uv.setLongitude(rs.getDouble("longitude"));
				if (rs.getBinaryStream("headPicStr") != null) {
					InputStream in = rs.getBinaryStream("headPicStr");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					uv.setHeadPicStr(new String(baos.toByteArray()));
					uv.setPicName(rs.getString("picName"));
					uv.setPicPath(rs.getString("picPath"));
				}
				
			}
		} catch (Exception e) {
			System.out.println("LogOnDaoImpl.getUserByAccount(String account) error: " + e.getMessage());
		} finally {
			con.close();
			pstmt.close();
			rs.close();
		}
		
		return uv;
	}

}
