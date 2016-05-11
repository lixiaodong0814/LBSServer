package lbs.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lbs.dao.INearPeopleDao;
import lbs.factory.ConnectionFactory;
import lbs.value.NearPeopleValue;
import lbs.value.NewsValue;

public class NearPeopleDaoImpl implements INearPeopleDao {
	private Connection con;
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private List<NearPeopleValue> nearPeopleList;
	private int pageSize = 3;

	@Override
	public List<NearPeopleValue> getNearPeople(int pageNo, String account) throws Exception {
		pageNo = (pageNo - 1) * pageSize;

		sql = "SELECT id, account, nickName, latitude, longitude, address, headPicStr "
				+ " FROM tb_user "
				+ " WHERE account!=?"
				+ " LIMIT " + pageNo + ", " + pageSize;

		try {
			con = ConnectionFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			nearPeopleList = new ArrayList<NearPeopleValue>();

			while (rs.next()) {
				NearPeopleValue npv = new NearPeopleValue();
				npv.setId(rs.getInt("id"));
				npv.setAccount(rs.getString("account"));
				npv.setNickName(rs.getString("nickName"));
				npv.setLatitude(rs.getDouble("latitude"));
				npv.setLongitude(rs.getDouble("longitude"));
				npv.setAddrress(rs.getString("address"));
				if (rs.getBinaryStream("headPicStr") != null) {
					InputStream in = rs.getBinaryStream("headPicStr");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int count = 0;
					byte[] buff = new byte[40960];
					while ((count = in.read(buff)) != -1) {
						baos.write(buff, 0, count);
					}
					npv.setUserPicStr(new String(baos.toByteArray()));
				}
				nearPeopleList.add(npv);
			}
			pstmt.close();
			System.out.println("NearPeopleDaoImpl--->List<NearPeopleValue> getNearPeople(int pageNo, String account) success");
			return nearPeopleList;

		} catch (Exception e) {
			System.out.println("NearPeopleDaoImpl--->List<NearPeopleValue> getNearPeople(int pageNo, String account) error: " + e.getMessage());
		} finally {
			con.close();
			rs.close();
		}
		return null;
	}

}
