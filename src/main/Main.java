package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Account.CLogin;
import Cource.CDirectory;
import Cource.CLecture;
import Enrollment.CApply;
import Enrollment.CBasket;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
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
