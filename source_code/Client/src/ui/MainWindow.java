package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

/**
 * Main functional window for the client program.
 */
public class MainWindow extends JFrame {

    private JPanel contentPane;

    private JLabel message1;
    private JLabel message2;
    private JLabel message3;
    private JLabel state;

    JTextArea message;
    JTextField target;

    private JButton login;
    private JButton register;
    private JButton chat;
    private JButton group;
    private JButton logout;

    /**
     * Build a MainWondow.
     */
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        setTitle("MainWindow"); 
        setSize(600, 400);
        setLocation(200, 50);
        String path = "Images\\background.jpg";
        ImageIcon background = new ImageIcon(path);
        JLabel label = new JLabel(background); 
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel imagePanel = (JPanel) this.getContentPane();  
        imagePanel.setOpaque(false);  
        contentPane.setLayout(null);

        message1 = new JLabel("System State:");
        message1.setFont(new Font("华文细黑", Font.BOLD, 15));
        message1.setBounds(154, 57, 116, 45);
        message1.setForeground(new Color(204, 204, 255));
        contentPane.add(message1);

        state = new JLabel("Haven't Login");
        state.setFont(new Font("华文细黑", Font.BOLD, 15));
        state.setBounds(321, 65, 141, 29);
        state.setForeground(new Color(204, 204, 255));
        contentPane.add(state);

        message2 = new JLabel("Online User List:");
        message2.setFont(new Font("华文细黑", Font.BOLD, 15));
        message2.setBounds(142, 144, 148, 25);
        message2.setForeground(new Color(204, 204, 255));
        contentPane.add(message2);

        message3 = new JLabel("Target(user/user,user):");
        message3.setFont(new Font("华文细黑", Font.BOLD, 14));
        message3.setBounds(121, 233, 183, 22);
        message3.setForeground(new Color(204, 204, 255));
        contentPane.add(message3);

        target = new JTextField();
        target.setForeground(new Color(255, 51, 0));
        target.setFont(new Font("华文细黑", Font.BOLD, 15));
        target.setBounds(314, 226, 148, 38);
        contentPane.add(target);

        target.setColumns(10);
        target.setOpaque(false);

        login = new JButton("Log in");
        login.setFont(new Font("华文细黑", Font.PLAIN, 15));
        login.setBounds(10, 65, 101, 38);
        login.setContentAreaFilled(false);
        login.setFocusable(false);
        login.setForeground(new Color(204, 204, 255));
        contentPane.add(login);

        register = new JButton("Register");
        register.setFont(new Font("华文细黑", Font.PLAIN, 15));
        register.setBounds(10, 98, 101, 38);
        register.setContentAreaFilled(false);
        register.setFocusable(false);
        register.setForeground(new Color(204, 204, 255));
        contentPane.add(register);

        chat = new JButton("Chat");
        chat.setFont(new Font("华文细黑", Font.PLAIN, 15));
        chat.setBounds(10, 137, 101, 38);
        chat.setContentAreaFilled(false);
        chat.setForeground(new Color(204, 204, 255));
        chat.setFocusable(false);
        contentPane.add(chat);

        group = new JButton("GroupChat");
        group.setFont(new Font("华文细黑", Font.PLAIN, 12));
        group.setBounds(10, 176, 101, 38);
        group.setContentAreaFilled(false);
        group.setForeground(new Color(204, 204, 255));
        group.setFocusable(false);
        contentPane.add(group);

        logout = new JButton("Logout");
        logout.setFont(new Font("华文细黑", Font.PLAIN, 15));
        logout.setBounds(10, 215, 101, 38);
        logout.setContentAreaFilled(false);
        logout.setForeground(new Color(204, 204, 255));
        logout.setFocusable(false);
        contentPane.add(logout);

        message = new JTextArea();
        message.setBounds(314, 146, 148, 38);
        message.setFocusable(false);
        message.setFont(new Font("华文细黑", Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane(message);
        scroll.setBounds(314, 146, 148, 38);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scroll);

        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));   
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!Data.isLogin) {
                    Data.lPointer = new LoginWindow();
                }
            }
        });

        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Data.rPointer = new RegisterWindow();
            }
        });

        chat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Data.isLogin) {
                    String talk = target.getText();
                    if (Data.onlineID.containsValue(talk)) {
                        JSONObject chatRequest = JsonAnalyzer.type5(Data.userID, Data.onlineName.get(talk));
                        Data.udpSend.push(chatRequest);
                        Data.udpGuide.push("Server");
                        target.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "User doesn't exist or doesn't Online", "message", JOptionPane.ERROR_MESSAGE);
                        target.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please login", "message", JOptionPane.ERROR_MESSAGE);
                    target.setText("");
                }
            }
        });

        group.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Data.isLogin) {
                    boolean correct = true;
                    String temp = target.getText().trim();
                    ArrayList<String> groupList = new ArrayList<String>();

                    int i = 0, index = 0;
                    while (true) {
                        index = temp.indexOf(',', i);
                        if (index < 0) {
                            String buf = temp.substring(i, temp.length());
                            if (Data.onlineName.containsKey(buf)) {
                                groupList.add(Data.onlineName.get(buf));
                            } else {
                                correct = false;
                            }
                            break;
                        }

                        String buffer = temp.substring(i, index);
                        if (Data.onlineName.containsKey(buffer)) {
                            groupList.add(Data.onlineName.get(buffer));
                            i = index + 1;
                        } else {
                            correct = false;
                        }
                    }
                    if (correct) {
                        JSONObject groupChat = JsonAnalyzer.type3(Data.userID, groupList);
                        Data.tcpSend.push(groupChat);
                    } else {
                        JOptionPane.showMessageDialog(null, "User doesn't exist or doesn't Online", "message", JOptionPane.ERROR_MESSAGE);
                    }
                    target.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please login", "message", JOptionPane.ERROR_MESSAGE);
                    target.setText("");
                }
            }
        });

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Data.isLogin) {
                    JSONObject quit = JsonAnalyzer.type0(Data.userID);
                    Data.tcpSend.push(quit);
                    setState("Haven't Login");
                    show("");
                    Data.clean();
                }
            }
        });
    }

    /**
     * Update the state TextArea by given content.
     * @param s the content to be show on the state TextArea
     */
    public void setState(String s) {
        state.setText(s);
    }

    /**
     * Update the message TextArea by given content.
     * @param s the content to be show on the message TextArea
     */
    public void show(String s) {
        message.setText(s);
    }

    /**
     * Print the list of the online users' name on the message TextArea.
     */
    public void update() {
        String temp = "";
        for (Map.Entry<String, String> entry : Data.onlineID.entrySet()) {
            temp = temp + " " + entry.getValue();
        }
        message.setText(temp);
    }
}
