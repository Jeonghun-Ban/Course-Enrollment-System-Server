package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Account.CLogin;
import Cource.CDirectory;
import Cource.CLecture;
import Enrollment.CApply;
import Enrollment.CBasket;
import Framework.ICApply;
import Framework.ICBasket;
import Framework.ICDirectory;
import Framework.ICLecture;
import Framework.ICLogin;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CLogin clogin = new CLogin();
		CDirectory cDirectory = new CDirectory();
		CLecture cLecture = new CLecture();
		CApply cApply = new CApply();
		CBasket cBasket = new CBasket();
		
		ICLogin iCLogin;
		ICDirectory iCDirectory;
		ICLecture iCLecture;
		ICApply iCApply;
		ICBasket iCBasket;
		
		try {
			iCLogin = (ICLogin) UnicastRemoteObject.exportObject(clogin, 0);
			iCDirectory = (ICDirectory) UnicastRemoteObject.exportObject(cDirectory, 0);
			iCLecture = (ICLecture) UnicastRemoteObject.exportObject(cLecture, 0);
			iCApply = (ICApply) UnicastRemoteObject.exportObject(cApply, 0);
			iCBasket = (ICBasket) UnicastRemoteObject.exportObject(cBasket, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			
			registry.bind("iCLogin", iCLogin);
			registry.bind("iCDirectory", iCDirectory);
			registry.bind("iCLecture", iCLecture);
			registry.bind("iCApply", iCApply);
			registry.bind("iCBasket", iCBasket);
			
			System.out.println("통신 준비 완료");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
