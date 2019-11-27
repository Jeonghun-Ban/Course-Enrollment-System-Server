package Cource;
import java.io.FileNotFoundException;
import java.util.Vector;

import Framework.ICDirectory;

public class CDirectory implements ICDirectory {
	private DAODirectory dAODirectory;
	
	public CDirectory() {
		dAODirectory = new DAODirectory();
	}

	public Vector<EDirectory> getItems(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return this.dAODirectory.getItems(filename);
	}
}
