package Account;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ELogin {
	private String userId;
	private String password;
	private String name;
	
	public void read(Scanner scanner) {
		this.userId = scanner.next();
		this.password = scanner.next();
		this.name = scanner.next();
	}

	public void authenticate(String userid, String password) throws FileNotFoundException, InvalidUserException {
		// TODO Auto-generated method stub
			Scanner scanner = new Scanner(new File("./data/user/login"));
			
			while(scanner.hasNext()) {
				this.read(scanner);
				if (this.userId.equals(userid)&&this.password.equals(password)) {
					scanner.close();
					return;
				}
			}
			// invalid userId or password
			scanner.close();
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
