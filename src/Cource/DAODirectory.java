package Cource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import main.Constants;

public class DAODirectory {
	
	static final String JDBC_DRIVER = Constants.JDBC_DRIVER; 
	static final String DB_URL = Constants.DB_URL + "directory" + Constants.DB_URL2;
	static final String USERNAME = Constants.USERNAME;
	static final String PASSWORD = Constants.PASSWORD;
	
	Connection conn = null;
	Statement state = null;
	String sql = "SELECT * FROM "; // select문
	
	public DAODirectory() {
		// JDBC 연결준비 
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			state = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// file open
	public Vector<EDirectory> getItems(String filename) throws FileNotFoundException {
		Vector<EDirectory> eDirectories = new Vector<EDirectory>();
		
		try {
			ResultSet rs = state.executeQuery(sql + filename+ ";");
			
			while(rs.next()) {
				EDirectory eDirectory = new EDirectory();
				eDirectory.setNumber(rs.getInt("id"));
				eDirectory.setName(rs.getString("name"));
				eDirectory.setHyperLink(rs.getString("hyperlink"));
				eDirectories.add(eDirectory);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eDirectories;
	}

}
