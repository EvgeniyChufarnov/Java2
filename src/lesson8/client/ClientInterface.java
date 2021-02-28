package lesson8.client;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientInterface extends JFrame implements Runnable {
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 400;

    private JTextArea chatArea;
    private JTextField loginField;
    private JTextField passwordField;
    private JTextField messageField;
    private JButton sendMessageButton;
    private JPanel authPanel;
    private JButton authButton;

    private boolean logIn;
    private Client client;

    public ClientInterface(Client client) throws HeadlessException {
        this.client = client;
        logIn = false;

        this.client.setOnLoginCallback(this::onSuccessfulLogin);
        this.client.setOnMessageCallback(this::displayMessage);

        loginTimerInit();
    }

    private void loginTimerInit() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(this::onTimerFinish, 2, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        windowInit();
        topPanelInit("Chat with somebody");
        centerPanelInit();
        authPanelInit();
        setVisible(true);
    }

    private void windowInit() {
        setTitle("Messenger");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation((screenWidth - WINDOW_WIDTH) /2, (screenHeight - WINDOW_HEIGHT) /2);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (logIn) {
                    client.sendMessage("/end");
                }
            }
        });
    }

    private void topPanelInit(String chatTitleText) {
        JLabel chatTitle = new JLabel(chatTitleText);
        chatTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(chatTitle, BorderLayout.NORTH);
    }

    private void centerPanelInit() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollableArea = new JScrollPane(chatArea);
        add(chatScrollableArea);

    }

    private void authPanelInit() {
        authPanel = new JPanel();
        authPanel.setLayout(new GridLayout(5, 1));
        add(authPanel, BorderLayout.SOUTH);

        JLabel loginTitle = new JLabel("Login:");
        authPanel.add(loginTitle);

        loginField = new JTextField();
        authPanel.add(loginField);

        JLabel passwordTitle = new JLabel("Password:");
        authPanel.add(passwordTitle);

        passwordField = new JTextField();
        authPanel.add(passwordField);

        authButton = new JButton("Login");
        authPanel.add(authButton);
        authButton.addActionListener(e -> onLoginClicked());
    }

    private void bottomPanelInit() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));
        add(bottomPanel, BorderLayout.SOUTH);

        JLabel messageTitle = new JLabel("Write your message:");
        bottomPanel.add(messageTitle);

        messageField = new JTextField();
        bottomPanel.add(messageField);
        messageField.addActionListener(e -> onSendClicked());

        sendMessageButton = new JButton("Send");
        bottomPanel.add(sendMessageButton);
        sendMessageButton.addActionListener(e -> onSendClicked());
    }

    private void onSendClicked() {
        client.sendMessage(messageField.getText());
        messageField.setText("");
    }

    private void onLoginClicked() {
        client.onAuthClick(loginField.getText(), passwordField.getText());
        loginField.setText("");
        passwordField.setText("");
    }

    public void displayMessage(String message) {
        chatArea.append(message);
    }

    public void onTimerFinish() {
        if (!logIn) {
            displayMessage("Время для входа истекло, пожалуйта, презапустите приложение.\n");

            client.sendMessage("/end");

            authPanel.removeAll();
            remove(authPanel);
            revalidate();
            repaint();
        }
    }

    public void onSuccessfulLogin() {
        authPanel.removeAll();
        remove(authPanel);
        bottomPanelInit();
        revalidate();
        repaint();

        logIn = true;
    }
}
