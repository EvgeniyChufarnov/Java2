package lesson6.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Scanner;

public class ConnectedClient implements Runnable {
    private final String CLIENT_MESSAGE_PREFIX = "Client message: ";
    private final String CLOSE_MESSAGE = "Stop";
    private final String DISCONNECT_MESSAGE = "Client has been disconnected";

    private Socket socket;
    private PrintWriter pw;
    private ConnectedClientWriter writer = null;

    public ConnectedClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            pw = new PrintWriter(socket.getOutputStream());
            writer = new ConnectedClientWriter(pw);

            String clientMessage;

            while (true) {
                clientMessage = br.readLine();

                if (clientMessage.equalsIgnoreCase(CLOSE_MESSAGE)) {
                    writer.sendMessage(CLOSE_MESSAGE);
                    break;
                } else {
                    System.out.println(CLIENT_MESSAGE_PREFIX + clientMessage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.close();

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(DISCONNECT_MESSAGE);
            Server.removeFromClients(this);
        }
    }

    public ConnectedClientWriter getWriter() throws Exception {
        if (writer != null) {
            return writer;
        } else {
            throw new Exception("Writer hasn't been initiate yet");
        }
    }
}