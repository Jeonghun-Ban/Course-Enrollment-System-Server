package Account;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import main.Constants;

public class ELogin {
	
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
	Statement basketState = null;
	Statement applyState = null;
	
	private String userId;
	private String password;
	private String name;

	ELogin(){
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
			ResultSet rs = userState.executeQuery("select id, password from user_table");
			
			while(rs.next()) {
				this.userId = rs.getString("id");
				this.password = rs.getString("password");
				
				if(this.userId.equals(userid)&&this.password.equals(password)){
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
		Scanner scanner = new Scanner(new File("./data/user/login"));
		
		while(scanner.hasNext()) {
			this.read(scanner);
			if(this.userId.equals(id)) {
				valid = false;
				return valid;
			}
		}
		valid = true;
		return valid;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void addAccount(String id, String pw, String name) throws IOException {
		// TODO Auto-generated method stub

		// 회원가입한 사용자의 파일 생성
		File basket = new File("data/user/" + id+ "_basket");
		basket.createNewFile();
		File apply = new File("data/user/" + id+ "_apply");
		apply.createNewFile();
		
		// 로그인 파일에 추가
		FileWriter fw = new FileWriter("data/user/login", true);
		fw.write(id+" "+pw+" "+name+"\r\n");
		fw.close();
	}

}
