package lesson6.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_MESSAGE_PREFIX = "Server message: ";
    private static final String CLOSE_MESSAGE = "Stop";
    private static final String FORCED_CLOSE_MESSAGE = "Forced close";
    private static final String ON_CONNECT = "Connected to the server";
    private static final String ON_FORCED_DISCONNECT = "Server has been shutdown";

    private static Scanner scanner;

    public static void main(String[] args) {
        startClient();
    }

    private static void startClient() {
        try(Socket socket = new Socket("localhost", 8180);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            ) {

            scanner = new Scanner(System.in);

            System.out.println(ON_CONNECT);

            Thread writingThread = new Thread(() -> {
                String userMessage = "";

                while (!socket.isClosed()) {
                    userMessage = scanner.nextLine();
                    if (userMessage != null) {
                        pw.println(userMessage);
                        pw.flush();
                    }
                }
            });
            writingThread.setDaemon(true);
            writingThread.start();

            String messageFromServer = "";

            while (!messageFromServer.equalsIgnoreCase(CLOSE_MESSAGE) && !messageFromServer.equalsIgnoreCase(FORCED_CLOSE_MESSAGE)) {
                messageFromServer = br.readLine();


                if (messageFromServer.equals(FORCED_CLOSE_MESSAGE)) {
                    pw.println(CLOSE_MESSAGE);
                    pw.flush();
                    System.out.println(ON_FORCED_DISCONNECT);
                } else if(!messageFromServer.equalsIgnoreCase(FORCED_CLOSE_MESSAGE)) {
                    System.out.println(SERVER_MESSAGE_PREFIX + messageFromServer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
