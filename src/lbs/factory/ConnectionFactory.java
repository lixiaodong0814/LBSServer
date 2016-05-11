package lbs.factory;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionFactory {
	
	public static Connection getConnection() throws Exception {
		Connection con = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/lbs");
			con = ds.getConnection();
			context.close();
			System.out.println("ConnectionFactory  getConnection() sucess");
		} catch (Exception e) {
			throw new Exception("ConnectionFactory  getConnection() error: " + e.getMessage());
		}
		
		return con;
	}
	
}
