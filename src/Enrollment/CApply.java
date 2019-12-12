package Enrollment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Cource.ELecture;
import Framework.ICApply;

public class CApply implements ICApply {

	public DAOApply dAOApply;
	
	public CApply() {
		this.dAOApply = new DAOApply();
	}
	
	public boolean add(Vector<ELecture> lectures, Vector<ELecture> basketLectures, String id) throws IOException {
		// TODO Auto-generated method stub
		return dAOApply.add(lectures, basketLectures, id);
	}
	
	public Vector<ELecture> show(String id) throws FileNotFoundException {
		return dAOApply.show(id);
	}

	public void delete(Vector<ELecture> lectures, String id) throws IOException {
		// TODO Auto-generated method stub
		dAOApply.delete(lectures, id);
	}
}
