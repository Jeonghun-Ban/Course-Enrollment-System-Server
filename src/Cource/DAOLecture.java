package Cource;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import main.Constants;

public class DAOLecture {
	
	static final String JDBC_DRIVER = Constants.JDBC_DRIVER; 
	static final String DB_URL = Constants.DB_URL + "lecture" + Constants.DB_URL2;
	static final String USERNAME = Constants.USERNAME;
	static final String PASSWORD = Constants.PASSWORD;
	
	Connection conn = null;
	Statement state = null;
	String sql = "SELECT * FROM "; // select문
	
	public DAOLecture() {
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
	
	
	public Vector<ELecture> getItems(String filename) throws FileNotFoundException {
		Vector<ELecture> eLectures = new Vector<ELecture>();
		
		try {
			ResultSet rs = state.executeQuery(sql + filename+ ";");
			
			while(rs.next()) {
				ELecture eLecture = new ELecture();
				
				eLecture.setNumber(rs.getInt("id"));
				eLecture.setName(rs.getString("name"));
				eLecture.setProfessor(rs.getString("professor"));
				eLecture.setCredit(rs.getInt("credit"));
				eLecture.setTime(rs.getString("time"));
				
				eLectures.add(eLecture);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eLectures;
	}
}
