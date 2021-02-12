package lesson4;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessengerInterface extends JFrame {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendMessageButton;

    public MessengerInterface(){
        windowInit();
        topPanelInit("Chat with somebody");
        centerPanelInit();
        bottomPanelInit();
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

    private void bottomPanelInit() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));
        add(bottomPanel, BorderLayout.SOUTH);

        JLabel MessageTitle = new JLabel("Write your message:");
        bottomPanel.add(MessageTitle);

        messageField = new JTextField();
        bottomPanel.add(messageField);
        messageField.addActionListener(e -> onSendClicked());

        sendMessageButton = new JButton("Send");
        bottomPanel.add(sendMessageButton);
        sendMessageButton.addActionListener(e -> onSendClicked());
    }

    private void onSendClicked() {
        if (!messageField.getText().isEmpty()) {
            String messageInfo = new SimpleDateFormat("HH:mm:ss dd.MM.yy").format(new Date());
            String message = messageField.getText().strip();
            chatArea.append(String.format("[You sent at %s] %s\n", messageInfo, message));
            messageField.setText("");
        }
    }

}
