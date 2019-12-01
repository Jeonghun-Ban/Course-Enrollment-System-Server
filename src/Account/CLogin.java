package Account;
import java.io.FileNotFoundException;
import java.io.IOException;

import Framework.ICLogin;

public class CLogin implements ICLogin {

	private ELogin eLogin;
	public CLogin() {
		this.eLogin = new ELogin();
	}
	public void authenticate(String userid, String password) 
			throws FileNotFoundException, InvalidUserException {
		// TODO Auto-generated method stub
		this.eLogin.authenticate(userid, password);
		return;
	}
	
	public boolean validId(String id) throws FileNotFoundException {
		return eLogin.validId(id);
	}
	
	public String getName() {
		return eLogin.getName();
	}
	
	public void addAccount(String id, String pw, String name) throws IOException {
		eLogin.addAccount(id, pw, name);
	}
	
}
