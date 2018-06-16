package br.com.gestaomaster.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionFactory implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/bd_deprecia";
	private static final String USER = "postgres";
	private static final String SENHA = "123456";


	public static Connection open() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, SENHA);
		} 
		catch (Exception e) {
		}
		return null;
	}

	public void close(Connection con, Statement st, ResultSet rs) {
		closeConnection(null, st, rs);
	}

	public void close(Connection con, Statement st) {
		closeConnection(null, st, null);
	}

	private void closeConnection(Connection con, Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
