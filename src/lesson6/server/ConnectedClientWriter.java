package lesson6.server;

import java.io.PrintWriter;

class ConnectedClientWriter {
    PrintWriter pw;

    public ConnectedClientWriter(PrintWriter pw) {
        this.pw = pw;
    }

    public void sendMessage(String message) {
        pw.println(message);
        pw.flush();
    }
}