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
public class RegisterWindow extends JFrame {

	private JPanel contentPane;

    private JLabel message1;
    private JLabel message2;
    private JLabel message3;

    JTextField id;
    JTextField name;
    JTextField password;

    private JButton register;

    /**
     * Build a RegisterWindow.
     */
	public RegisterWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	    setTitle("RegisterWindow");
	    setSize(600, 400);
	    setLocation(200, 50);
	    String path = "Images\\background.jpg";
	    ImageIcon background = new ImageIcon(path);
	    JLabel label = new JLabel(background); 
	    label.setBounds(0, 0, this.getWidth(), this.getHeight());
	    JPanel imagePanel = (JPanel) this.getContentPane();
	    imagePanel.setOpaque(false);  
	    contentPane.setLayout(null);

	    message1 = new JLabel("ID:");
	    message1.setForeground(new Color(204, 204, 255));
	    message1.setBackground(new Color(204, 204, 255));
	    message1.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    message1.setBounds(173, 86, 40, 33);
	    contentPane.add(message1);

	    message2 = new JLabel("Name:");
	    message2.setForeground(new Color(204, 204, 255));
	    message2.setBackground(new Color(204, 204, 255));
	    message2.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    message2.setBounds(168, 145, 71, 33);
	    contentPane.add(message2);

	    message3 = new JLabel("Password:");
	    message3.setForeground(new Color(204, 204, 255));
	    message3.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    message3.setBounds(169, 216, 88, 33);
	    contentPane.add(message3);

	    id = new JTextField();
	    id.setBounds(299, 87, 165, 33);
	    contentPane.add(id);
	    id.setColumns(10);

	    name = new JTextField();
	    name.setBounds(299, 152, 165, 33);
	    contentPane.add(name);
	    name.setColumns(10);

	    password = new JTextField();
	    password.setBounds(299, 217, 165, 33);
	    contentPane.add(password);
	    password.setColumns(10);

	    register = new JButton("Register");
	    register.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    register.setBounds(245, 296, 93, 23);
	    register.setContentAreaFilled(false);
	    register.setFocusable(false);
	    register.setForeground(new Color(204, 204, 255));
	    contentPane.add(register);
	    this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

	    setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

	    register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSONObject text = JsonAnalyzer.type1(id.getText(), name.getText(), password.getText());
                Data.tcpSend.push(text);
            }
        });
	}
}
