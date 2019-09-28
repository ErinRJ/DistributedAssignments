import java.io.DataOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Client extends JFrame implements ActionListener {
	static Socket myClient = null;
	static JLabel status;
	static JTextArea port;
	static JTextArea host;
	static JFrame userInfoPagePage;
	static JFrame connectPage;

	public static void main(String[] args) throws UnknownHostException, IOException {
		PrintStream out = null;
		
		// create GUI pages
		connectPage = new JFrame("AbDominate! Fitness Client");
		userInfoPage = new JFrame("AbDominate! Fitness Client");
		connectPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set the size of the new window
		connectPage.setSize(600,300);
		userInfoPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set the size of the new window
		userInfoPage.setSize(600,300);
		
		// THINGS TO INCLUDE
		// have different frames/menus:
			// - add new patron
			// - find patron's information, options to delete, update, etc.
			// - get list of all fitness classes
			// - who's in which fitness class
		
	
		Client fc = new Client();
		createConnectionPage(fc);
		createPersonInfoPage(fc);
		connectPage.setVisible(true);
	}
		// CONNECT TO SERVER
//		myClient = new Socket("127.0.0.1", 5000);
//		out = new PrintStream(myClient.getOutputStream());
//		
//		String personInfo = "ADD,jeff bezos,555 sin st.,555-555-5555,email@email.com,part-time";
//		
//		out.println(personInfo);
		
	
	public static void createConnectionPage(Client fc) {
		//get connection information
		JPanel portPanel = new JPanel();
		
		JLabel portLabel = new JLabel("Port Number: ");
		port = new JTextArea(1, 4);
		JLabel hostLabel = new JLabel("Host name: ");
		host = new JTextArea(1,8);
		JButton connectButton = new JButton("Connect!");
		portPanel.add(portLabel);
		portPanel.add(port);
		portPanel.add(hostLabel);
		portPanel.add(host);
		portPanel.add(connectButton);
		
		connectButton.addActionListener(fc);
		
		//create status label
		JPanel statusP = new JPanel();	
		status = new JLabel("Connection not yet established");
		statusP.add(status);
		
		//add the panels to the frame
		connectPage.add(statusP);
		connectPage.add(portPanel);
	
		//Specify the menu bar should be at the top
		connectPage.getContentPane().add(BorderLayout.NORTH, portPanel);
		connectPage.getContentPane().add(BorderLayout.CENTER, statusP);
		
		return;
	}
	
	public static void createPersonInfoPage(Client fc) {
		//get connection information
		JPanel portPanel = new JPanel();
		
		JLabel portLabel = new JLabel("Port Number: ");
		port = new JTextArea(1, 4);
		JLabel hostLabel = new JLabel("Host name: ");
		host = new JTextArea(1,8);
		JButton connectButton = new JButton("Connect!");
		portPanel.add(portLabel);
		portPanel.add(port);
		portPanel.add(hostLabel);
		portPanel.add(host);
		portPanel.add(connectButton);
		
		connectButton.addActionListener(fc);
		
		//create status label
		JPanel statusP = new JPanel();	
		status = new JLabel("Connection not yet established");
		statusP.add(status);
		
		//add the panels to the frame
		connectPage.add(statusP);
		connectPage.add(portPanel);
	
		//Specify the menu bar should be at the top
		connectPage.getContentPane().add(BorderLayout.NORTH, portPanel);
		connectPage.getContentPane().add(BorderLayout.CENTER, statusP);
		
		return;
	}

	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String event = evt.getActionCommand();
		
		if(event.contentEquals("Connect!")) {
			
			//get values of port number and hostname once connected
			String portNumber = port.getText();			
			String hostname = host.getText();
			
			//make sure both port number and hostname have been filled in
			if((portNumber.equals("")) || (hostname.equals(""))) {
				status.setText("Could not connect, ensure host and port are specified above");
			}
		
			int portNum = Integer.parseInt(portNumber);
			//use the port and hostname to connect to server
			try {
				connect(portNum, hostname);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public void connect(int port, String host) throws Exception{
		Socket myClient = null;
		try {
			myClient = new Socket(host, port);
			connectPage.dispose();
			userInfoPage.setVisible(true);
		}catch(Exception e) {
			status.setText("Server not active");
			System.out.println("Could not connect to server");
		}
		status.setText("Server connected on port: " +port);
		
		//wait until file path is given, then perform what's below
		
		myClient.close();
	}

}
