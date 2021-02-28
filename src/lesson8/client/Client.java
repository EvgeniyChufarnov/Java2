package lesson8.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean logIn = false;

    private ClientOnLoginCallback onLoginCallback;
    private ClientOnMessageCallback onMessageCallback;

    public Client() {
        init();
    }

    interface ClientOnMessageCallback {
        void onNewMessage(String message);
    }

    interface ClientOnLoginCallback {
        void onSuccessfulLogin();
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
                                onLoginCallback.onSuccessfulLogin();
                                logIn = true;
                                break;
                            } else if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            onMessageCallback.onNewMessage(strFromServer + "\n");
                        }
                        if (logIn) {
                            while (true) {
                                String strFromServer = in.readUTF();
                                if (strFromServer.equalsIgnoreCase("/end")) {
                                    break;
                                }
                                onMessageCallback.onNewMessage(strFromServer + "\n");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                onMessageCallback.onNewMessage("Заполните оба поля");
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

    public void setOnLoginCallback(ClientOnLoginCallback onLoginCallback) {
        this.onLoginCallback = onLoginCallback;
    }

    public void setOnMessageCallback(ClientOnMessageCallback onMessageCallback) {
        this.onMessageCallback = onMessageCallback;
    }
}
