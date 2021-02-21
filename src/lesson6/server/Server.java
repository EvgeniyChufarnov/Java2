package lesson6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static String INIT_MESSAGE = "Sever is running";
    private static final String CLOSE_MESSAGE = "Stop";
    private static final String FORCED_CLOSE_MESSAGE = "Forced close";
    private static final String NO_ONE_LISTENING_WARNING = "There is no one to send the message";

    private static Scanner scanner = new Scanner(System.in);
    private static List<ConnectedClient> clients = new ArrayList<>();
    private static boolean forcedStop = false;

    public static void main(String[] args) {
        serverInit();
    }

    private static void serverInit() {
        try (ServerSocket serverSocket = new ServerSocket(8180)) {
            System.out.println(INIT_MESSAGE);
            new Thread(Server::keyboardListener).start();

            while (true) {
                Socket socket = serverSocket.accept();
                if (!forcedStop) {
                    ConnectedClient client = new ConnectedClient(socket);
                    clients.add(client);
                    new Thread(client).start();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void keyboardListener() {
        String message;

        while (true) {
            message = scanner.nextLine();

            if (message.equalsIgnoreCase(CLOSE_MESSAGE)) {
                forcedStop = true;

                if (clients.size() == 0) {
                    System.exit(0);
                } else {
                    broadcast(FORCED_CLOSE_MESSAGE);
                }
            } else if (clients.size() == 0) {
                System.out.println(NO_ONE_LISTENING_WARNING);
            } else {
                broadcast(message);
            }
        }
    }

    private static void broadcast(String message) {
        clients.forEach(client -> {
            try {
                client.getWriter().sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized void removeFromClients(ConnectedClient client) {
        clients.remove(client);

        if (forcedStop && clients.size() == 0) {
            System.exit(0);
        }
    }
}
