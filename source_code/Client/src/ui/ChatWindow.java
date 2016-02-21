package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

/**
 * Private chat functional window.
 */
public class ChatWindow extends JFrame {

    private JPanel contentPane;

    private JLabel message1;
    private JLabel message2;

    JTextArea message;
    JTextField said;

    private JButton send;
    private JButton close;

    private String name;
    private boolean online;

    /**
     * Build a ChatWindow with given title.
     * @param s the title of the window
     */
    public ChatWindow(String s) {
        name = s;
        online = true;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        setTitle(s);
        setSize(600, 400);  
        setLocation(200, 50);  
        String path = "Images\\background.jpg";  
        String path1 = "Images\\text.png"; 
        ImageIcon background = new ImageIcon(path);
        ImageIcon text = new ImageIcon(path1);
        JLabel label = new JLabel(background);  
        label.setBounds(0, 0, this.getWidth(), this.getHeight());   
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);  
        contentPane.setLayout(null);
        contentPane.setLayout(null);

        message1 = new JLabel("Message:");
        message1.setForeground(new Color(204, 204, 255));
        message1.setFont(new Font("宋体", Font.PLAIN, 15));
        message1.setBounds(10, 10, 83, 23);
        contentPane.add(message1);

        message2 = new JLabel("Statement:");
        message2.setForeground(new Color(204, 204, 255));
        message2.setFont(new Font("华文细黑", Font.PLAIN, 15));
        message2.setBounds(10, 187, 83, 23);
        contentPane.add(message2);

        said = new JTextField();
        said.setFont(new Font("华文细黑", Font.PLAIN, 15));
        said.setBounds(59, 220, 432, 103);
        contentPane.add(said);
        said.setColumns(10);

        send = new JButton("Send");
        send.setFont(new Font("华文细黑", Font.PLAIN, 15));
        send.setBounds(295, 333, 93, 23);
        contentPane.add(send);

        close = new JButton("Close");
        close.setFont(new Font("华文细黑", Font.PLAIN, 15));
        close.setBounds(398, 333, 93, 23);
        contentPane.add(close);

        message = new JTextArea();
        message.setFont(new Font("华文细黑", Font.PLAIN, 15));
        message.setBounds(59, 33, 432, 144);
        message.setOpaque(false);
        message.setFocusable(false);

        JScrollPane scroll = new JScrollPane(message);
        scroll.setBounds(59, 33, 432, 144);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scroll);

        JLabel lblNewLabel = new JLabel(text);
        lblNewLabel.setBounds(59, 33, 432, 144);
        contentPane.add(lblNewLabel);

        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);		

        JSONObject chat = JsonAnalyzer.type18(Data.userID, Data.userName, "Ready");
        Data.udpSend.push(chat);
        Data.udpGuide.push(name);
        speak(Data.userName + ": Ready");

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (online) {
                    String text = said.getText();
                    if (text.length() < 1000) {
                        JSONObject chat = JsonAnalyzer.type18(Data.userID, Data.userName, text);
                        Data.udpSend.push(chat);
                        Data.udpGuide.push(name);
                        speak(Data.userName + ": " + said.getText());
                        said.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Message is too long", "message", JOptionPane.ERROR_MESSAGE);
                        said.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
                    said.setText("");
                }
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (online) {
                    JSONObject quit = JsonAnalyzer.type19(Data.userID, Data.userName);
                    Data.udpSend.push(quit);
                    Data.udpGuide.push(name);
                    if (Data.chatMap.containsKey(Data.onlineName.get(name))) {
                        Data.chatMap.remove(Data.onlineName.get(name));
                    }
                    online = false;

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

     /**
     * Change the text of the TextArea by given content.
     * @param s the content to be show on the TextArea
     */
    public void speak(String s) {
        message.setText(message.getText() + "\n" + s);
        message.setCaretPosition(message.getText().length());
    }
}
