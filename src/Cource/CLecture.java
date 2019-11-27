﻿package Cource;

import java.io.FileNotFoundException;
import java.util.Vector;

import Framework.ICLecture;

public class CLecture implements ICLecture {
	private DAOLecture dAOLecture;
	
	public CLecture() {
		this.dAOLecture = new DAOLecture();
	}
	public Vector<ELecture> getItems(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return this.dAOLecture.getItems(filename);
	}
}
