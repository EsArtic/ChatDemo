package ui;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

/**
 * Login functional window.
 */
public class LoginWindow extends JFrame {

	private JPanel contentPane;

    JTextField user;
    JTextField password;

    private JButton login;

	/**
     * Build a LoginWindow.
     */
	public LoginWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	    setTitle("LoginWindow");
	    setSize(600, 400);  
	    setLocation(200, 50);  
	    String path = "Images\\background.jpg";
	    String path1 = "Images\\head.png";
	    ImageIcon background = new ImageIcon(path);
	    ImageIcon head = new ImageIcon(path1);
	    JLabel label = new JLabel(background); 
	    label.setBounds(0, 0, this.getWidth(), this.getHeight()); 
	    JPanel imagePanel = (JPanel) this.getContentPane();
	    imagePanel.setOpaque(false);  
	    contentPane.setLayout(null);

	    user = new JTextField();
	    user.setText("ID");
	    user.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    user.setBounds(199, 152, 162, 40);
	    contentPane.add(user);
	    user.setColumns(10);

	    password = new JTextField();
	    password.setText("Password");
	    password.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    password.setBounds(199, 202, 162, 40);
	    contentPane.add(password);
	    password.setColumns(10);

	    login = new JButton("Login");
	    login.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    login.setBounds(228, 269, 95, 31);
	    login.setContentAreaFilled(false);
	    login.setFocusable(false);
	    login.setForeground(new Color(204, 204, 255));
	    contentPane.add(login);

	    JLabel lblNewLabel = new JLabel(head);
	    lblNewLabel.setBounds(237, 39, 86, 85);
	    contentPane.add(lblNewLabel);
	    this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	    setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

		login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!Data.isLogin) {
                    JSONObject text = JsonAnalyzer.type2(user.getText(), password.getText());
                    Data.tcpSend.push(text);
                } else {
                    JOptionPane.showMessageDialog(null, "Already Login", "message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
	}
}
