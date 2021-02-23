package lesson7.client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    ClientInterface clientInterface;

    public Client() {
        clientInterface = new ClientInterface(this);
        SwingUtilities.invokeLater(clientInterface);
        init();
    }

    private void init() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String strFromServer = in.readUTF();
                            if(strFromServer.startsWith("/authok")) {
                                clientInterface.onSuccessfulLogin();
                                break;
                            }
                            clientInterface.displayMessage(strFromServer + "\n");
                        }
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            clientInterface.displayMessage(strFromServer + "\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAuthClick(String login, String password) {
        try {
            if (!login.isEmpty() && !password.isEmpty()) {
                out.writeUTF("/auth " + login + " " + password);
                out.flush();
            } else {
                clientInterface.displayMessage("Заполните оба поля");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
