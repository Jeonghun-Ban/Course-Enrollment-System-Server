package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
import Framework.Launcher;

public class Client implements Serializable {
    private Socket client;
    private InputStream inputStream;
    private OutputStream outputStream;


    public Client(Socket client) {
        try {
            this.client = client;
            this.inputStream = this.client.getInputStream();
            this.outputStream = this.client.getOutputStream();
            this.initializeObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeObject() {
        ICLogin iCLogin = new CLogin();
        ICDirectory iCDirectory = new CDirectory();
        ICLecture iCLecture = new CLecture();
        ICApply iCApply = new CApply();
        ICBasket iCBasket = new CBasket();
        boolean communicate = true;
        try {
            while (communicate) {
                ObjectInputStream inputStream = new ObjectInputStream(this.inputStream);
                Object object = inputStream.readObject();
                if (object != null) {
                    if (object.equals("exit")) communicate = false;
                    else {
                        Object returnObject = null;
                        if (object instanceof Launcher) {
                            Launcher launcher = (Launcher) object;
                            try {
                                switch (launcher.getClassName()) {
                                    case "ICLogin":
                                        returnObject = iCLogin.getClass().getMethod(launcher.getMethodName(), launcher.getParameterTypes()).invoke(iCLogin, launcher.getParameters());
                                        break;
                                    case "ICDirectory":
                                        returnObject = iCDirectory.getClass().getMethod(launcher.getMethodName(), launcher.getParameterTypes()).invoke(iCDirectory, launcher.getParameters());
                                        break;
                                    case "ICLecture":
                                        returnObject = iCLecture.getClass().getMethod(launcher.getMethodName(), launcher.getParameterTypes()).invoke(iCLecture, launcher.getParameters());
                                        break;
                                    case "ICApply":
                                        returnObject = iCApply.getClass().getMethod(launcher.getMethodName(), launcher.getParameterTypes()).invoke(iCApply, launcher.getParameters());
                                        break;
                                    case "ICBasket":
                                        returnObject = iCBasket.getClass().getMethod(launcher.getMethodName(), launcher.getParameterTypes()).invoke(iCBasket, launcher.getParameters());
                                        break;
                                }
                            } catch (InvocationTargetException e) {
                                returnObject = e;
                            }
                        }
                        ObjectOutputStream output = new ObjectOutputStream(outputStream);
                        output.writeObject(returnObject);
                    }
                }
            }
        } catch (IOException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException ignored) {
        }
    }

}
