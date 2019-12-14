package Account;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Constants;
import main.CurrentUser;

public class DAOLogin {
	
	// JDBC 설정
	static final String JDBC_DRIVER = Constants.JDBC_DRIVER;
	static final String USERNAME = Constants.USERNAME;
	static final String PASSWORD = Constants.PASSWORD;
	
	static final String USER_DB_URL = Constants.DB_URL + "user_db" + Constants.DB_URL2;
	static final String BASKET_DB_URL = Constants.DB_URL + "basket" + Constants.DB_URL2;
	static final String APPLY_DB_URL = Constants.DB_URL + "apply" + Constants.DB_URL2;
	
	// Connection
	Connection userCon = null;
	Connection basketCon = null;
	Connection applyCon = null;
	
	// Statement
	Statement userState = null;
	PreparedStatement userPrest = null;
	
	Statement basketState = null;
	Statement applyState = null;

	public DAOLogin(){
		try {
			Class.forName(JDBC_DRIVER);
			userCon = DriverManager.getConnection(USER_DB_URL,USERNAME,PASSWORD);
			basketCon = DriverManager.getConnection(BASKET_DB_URL,USERNAME,PASSWORD);
			applyCon = DriverManager.getConnection(APPLY_DB_URL,USERNAME,PASSWORD);
			
			userState = userCon.createStatement();
			basketState = basketCon.createStatement();
			applyState = applyCon.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void authenticate(String userid, String password) throws FileNotFoundException, InvalidUserException {
		// TODO Auto-generated method stub
		try {
			ResultSet rs = userState.executeQuery("select * from user_table");
			
			while(rs.next()) {
				CurrentUser.userId = rs.getString("id");
				CurrentUser.password = rs.getString("password");
				CurrentUser.name = rs.getString("name");
				CurrentUser.major = rs.getString("major");
				CurrentUser.credit = rs.getInt("credit");
				
				if(CurrentUser.userId.equals(userid)&&CurrentUser.password.equals(password)){
					return;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new InvalidUserException(); // throw
	}

	public boolean validId(String id) throws FileNotFoundException {
		// TODO Auto-generated method stub
		boolean valid = true;
		try {
			ResultSet rs = userState.executeQuery("select id from user_table");
			
			while(rs.next()) {
				CurrentUser.userId = rs.getString("id");
				
				if(CurrentUser.userId.equals(id)){
					return false;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valid;
	}
	
	// 현재 로그인한 사용자 이름 반환
	public String getName() {
		// TODO Auto-generated method stub
		return CurrentUser.name;
	}
	
	public String getMajor() {
		return CurrentUser.major;
	}
	
	public int getCredit() {
		return CurrentUser.credit;
	}
	
	
	public void addAccount(String id, String pw, String name, String major, int credit) throws IOException {
		// TODO Auto-generated method stub

		// 회원가입한 사용자의 파일 생성
		try {
			basketState.execute("create table basket_" + id + 
					" (id int, name varchar(50), professor varchar(10), credit int, time varchar(50));");
			applyState.execute("create table apply_" + id + 
					" (id int, name varchar(50), professor varchar(10), credit int, time varchar(50));");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// 유저 DB에 추가
		try {
			userPrest = userCon.prepareStatement("insert into user_table (id, password, name, major, credit) values (?, ?, ?, ?, ?)");
			
			userPrest.setString(1, id);
			userPrest.setString(2, pw);
			userPrest.setString(3, name);
			userPrest.setString(4, major);
			userPrest.setInt(5, credit);
			
			userPrest.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
