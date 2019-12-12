package Enrollment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Cource.ELecture;
import main.Constants;

public class DAOBasket {

	static final String JDBC_DRIVER = Constants.JDBC_DRIVER; 
	static final String DB_URL = Constants.DB_URL + "basket" + Constants.DB_URL2;
	static final String USERNAME = Constants.USERNAME;
	static final String PASSWORD = Constants.PASSWORD;
	
	String tableName = null;
	
	Connection conn = null;
	Statement state = null;
	PreparedStatement prest = null;
	
	private Vector<ELecture> storedLectures = new Vector<>();
	private boolean invalidLecture = false;

	public DAOBasket() {
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
	
	public boolean add(Vector<ELecture> lectures, Vector<ELecture> applyLectures, String id) throws IOException {
		// TODO Auto-generated method stub

		// 중복 강의리스트 삭제
		for (int i = 0; i < storedLectures.size(); i++) {
			ELecture storedLecture = storedLectures.get(i);
			for (int j = 0; j < lectures.size(); j++) {
				ELecture lecture = lectures.get(j);
				if (lecture.getName().equals(storedLecture.getName())) {
					lectures.remove(lecture);
					invalidLecture = true;
				}
			}
		}
		
		for (int i = 0; i < applyLectures.size(); i++) {
			ELecture applyLecture = applyLectures.get(i);
			for (int j = 0; j < lectures.size(); j++) {
				ELecture lecture = lectures.get(j);
				if (lecture.getName().equals(applyLecture.getName())) {
					lectures.remove(lecture);
					invalidLecture = true;
				}
			}
		}

		if(invalidLecture) {
			invalidLecture = false;
			return true; // invalid
		}
		
		// db write
		try {
			for (ELecture lecture : lectures) {
				prest = conn.prepareStatement("insert into " + tableName + "(id, name, professor, credit, time) values (?, ?, ?, ?, ?)");
		
				prest.setInt(1, lecture.getNumber());
				prest.setString(2,  lecture.getName());
				prest.setString(3,  lecture.getProfessor());
				prest.setInt(4, lecture.getCredit());
				prest.setString(5, lecture.getTime());
				
				prest.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false; // valid

	}

	public Vector<ELecture> show(String id) throws FileNotFoundException {
		tableName = "basket_"+id;
		
		storedLectures.removeAllElements();
		
		try {
			ResultSet rs = state.executeQuery("select * from " + tableName);

			while (rs.next()) {
				ELecture storedLecture = new ELecture();
				
				storedLecture.setNumber(rs.getInt("id"));
				storedLecture.setName(rs.getString("name"));
				storedLecture.setProfessor(rs.getString("professor"));
				storedLecture.setCredit(rs.getInt("credit"));
				storedLecture.setTime(rs.getString("time"));
				
				storedLectures.add(storedLecture);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storedLectures;
	}

	public void delete(Vector<ELecture> lectures, String id) throws IOException {
		
		try {
			for (ELecture lecture : lectures) {
				state.executeUpdate("delete from " + tableName + " where id = " + lecture.getNumber() + ";");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
