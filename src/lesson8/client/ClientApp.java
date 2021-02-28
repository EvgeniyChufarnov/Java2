package lesson8.client;

import javax.swing.*;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client();
        SwingUtilities.invokeLater(new ClientInterface(client));
    }
}
