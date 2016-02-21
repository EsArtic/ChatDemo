package ui;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

/**
 * Group chat functional window.
 */
public class GroupWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;

    private JLabel message1;
    private JLabel message2;
    private JLabel message3;

    JTextArea members;
    JTextArea message;
    JTextField said;

    private JButton send = new JButton("Send");
    private JButton close = new JButton("Quit");

    private boolean online;
    private String groupID;

    /**
     * Records of the users in current group chat.
     */
    private Map<String, String> list = new HashMap<String, String>();

    /**
     * Build a GroupWindow with given title.
     * @param s the title of the window
     */
	public GroupWindow(String s) {
		groupID = s;
        online = true;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

	    message1 = new JLabel("Members:");
	    message1.setForeground(new Color(204, 204, 255));
	    message1.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    message1.setBounds(457, 10, 81, 18);
	    contentPane.add(message1);

	    message2 = new JLabel("Messages:");
	    message2.setForeground(new Color(204, 204, 255));
	    message2.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    message2.setBounds(10, 5, 81, 23);
	    contentPane.add(message2);

	    message3 = new JLabel("Statement:");
	    message3.setForeground(new Color(204, 204, 255));
	    message3.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    message3.setBounds(10, 198, 93, 23);
	    contentPane.add(message3);

	    said = new JTextField();
		said.setFont(new Font("华文细黑", Font.PLAIN, 20));
	    said.setBounds(20, 238, 432, 80);
	    contentPane.add(said);
	    said.setColumns(10);

	    send = new JButton("Send");
	    send.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    send.setBounds(254, 328, 93, 23);
	    contentPane.add(send);

	    close = new JButton("Quit");
	    close.setFont(new Font("华文细黑", Font.PLAIN, 15));
	    close.setBounds(359, 328, 93, 23);
	    contentPane.add(close);

	    JLabel lblNewLabel = new JLabel(text);
	    lblNewLabel.setBounds(20, 35, 432, 153);
	    contentPane.add(lblNewLabel);

	    message = new JTextArea();
		message.setFont(new Font("华文细黑", Font.BOLD, 15));
	    message.setBounds(20, 38, 432, 150);
	    message.setOpaque(false);
	    message.setFocusable(false);
		
		JScrollPane scroll1 = new JScrollPane(message);
		scroll1.setBounds(20, 38, 432, 150);
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    contentPane.add(scroll1);

	    members = new JTextArea();
	    members.setFont(new Font("华文细黑", Font.BOLD, 15));
	    members.setForeground(new Color(255, 51, 0));
	    members.setBounds(467, 38, 107, 313);
	    members.setOpaque(false);
	    members.setFocusable(false);
		JScrollPane scroll2 = new JScrollPane(members);
		scroll2.setBounds(467, 38, 107, 313);
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    contentPane.add(scroll2);

	    this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	    setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

	    send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (online) {
                    JSONObject groupChat = JsonAnalyzer.type4(Data.userID, groupID, said.getText());
                    Data.tcpSend.push(groupChat);
                    said.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
                    said.setText("");
                }
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (online) {
                    JSONObject quit = JsonAnalyzer.type7(Data.userID, groupID);
                    Data.tcpSend.push(quit);
                    if (Data.groupMap.containsKey(groupID)) {
                        Data.groupMap.remove(groupID);
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
     * Change the text of the message TextArea by given content.
     * @param s the content to be show on the message TextArea
     */
    public void said(String s) {
        message.setText(message.getText() + "\n" + s);
        message.setCaretPosition(message.getText().length());
    }

    /**
     * Print the list of the users' name on the members TextArea.
     */
    private void showMember() {
        String temp = "";
        for (Map.Entry<String, String> entry : list.entrySet()) {
            temp = temp + "\n" + entry.getValue();
        }
        members.setText(temp);
    }

    /**
     * Analyze the given idList and nameList and build the user Map.
     * @param idList the list contains user's ID
     * @param nameList the list contains user's name
     */
    public void build(String idList, String nameList) {
        ArrayList<String> id = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();

        int i, index;
        i = 2;
        index = 0;
        while (true) {
            index = idList.indexOf(',', i);
            if (index < 0) break;

            String temp = idList.substring(i, index-1);
            id.add(temp);
            i = index+2;
        }
        id.add(idList.substring(i, idList.length()-2));

        i = 2;
        index = 0;
        while (true) {
            index = nameList.indexOf(',', i);
            if (index < 0) break;

            String temp = nameList.substring(i, index-1);
            name.add(temp);
            i = index+2;
        }
        name.add(nameList.substring(i, nameList.length()-2));

        for (i = 0; i < id.size(); i++) {
            list.put(id.get(i), name.get(i));
        }

        showMember();
    }

    /**
     * Remove the target user from the list and update the content on members TextArea.
     * @param key the target user's ID
     */
    public void delete(String key) {
        list.remove(key);
        showMember();
    }
}
