package lesson8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean logIn = false;

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    authentication();
                    if (logIn)
                        readMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok " + nick);
                        name = nick;
                        myServer.broadcastMsg(name + " зашел в чат");
                        myServer.subscribe(this);
                        logIn = true;
                        return;
                    } else {
                        sendMsg("Учетная запись уже используется");
                    }
                } else {
                    sendMsg("Неверные логин/пароль");
                }
            } else if (str.equals("/end")) {
                sendMsg("/end");
                return;
            }
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            System.out.println("от " + name + ": " + strFromClient);
            if (strFromClient.equals("/end")) {
                sendMsg("/end");
                return;
            }
            if (strFromClient.startsWith("/w")) {
                String[] parts = strFromClient.split("\\s");
                String nickOfAddressee = parts[1];
                String message = name + " (private):  " + strFromClient.substring(nickOfAddressee.length() + 4);

                if (myServer.isNickBusy(nickOfAddressee)) {
                    myServer.sendMessageByNick(message, nickOfAddressee, name);
                } else {
                    sendMsg("Пользователь " + nickOfAddressee + " не в сети");
                }
            } else {
                myServer.broadcastMsg(name + ": " + strFromClient);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        if (logIn)
            myServer.broadcastMsg(name + " вышел из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}