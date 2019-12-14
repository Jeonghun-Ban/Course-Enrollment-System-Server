package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            ServerSocket serverSocket = new ServerSocket(10000);
            while (true) {
                Socket client = serverSocket.accept();
                Thread thread = new Thread(() -> new Client(client));
                thread.start();
                System.out.println("소켓통신 연결완료");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
