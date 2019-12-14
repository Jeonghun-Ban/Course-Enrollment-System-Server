package Account;
import java.io.FileNotFoundException;
import java.io.IOException;

import Framework.ICLogin;

public class CLogin implements ICLogin {

	private DAOLogin dAOLogin;
	public CLogin() {
		this.dAOLogin = new DAOLogin();
	}
	public void authenticate(String userid, String password) 
			throws FileNotFoundException, InvalidUserException {
		// TODO Auto-generated method stub
		this.dAOLogin.authenticate(userid, password);
		return;
	}
	
	public boolean validId(String id) throws FileNotFoundException {
		return dAOLogin.validId(id);
	}
	
	public String getName() {
		return dAOLogin.getName();
	}
	
	public int getCredit() {
		return dAOLogin.getCredit();
	}
	
	public void addAccount(String id, String pw, String name, String major, int credit) throws IOException {
		dAOLogin.addAccount(id, pw, name, major, credit);
	}
	
}
