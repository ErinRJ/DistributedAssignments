import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.io.InputStreamReader;
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
import javax.swing.JComboBox;

import javax.swing.JFrame;

public class Client extends JFrame implements ActionListener {
	static Socket myClient = null;
	static PrintStream ps = null;
	static BufferedReader in = null;
	
	static JLabel status;
	static JTextArea port;
	static JTextArea host;
	
	static JFrame userInfoPage;
	static JFrame connectPage;
	static JFrame addPersonPage;
	static JFrame delPersonPage;
	static JFrame findPersonPage;
	
	static JTextArea addnametxt;
	static JTextArea delnametxt;
	static JTextArea findnametxt;
	static JTextArea addresstxt;
	static JTextArea phonetxt;
	static JTextArea emailtxt;
	static JComboBox<String> comboMemberships;
	
	
	static JLabel received_nameL;
	static JLabel received_addressL;
	static JLabel received_phoneL;
	static JLabel received_emailL;
	static JLabel received_fitnessL;
	
	static JLabel delresults;
	static JLabel addresults;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		PrintStream out = null;
		
		// create GUI pages
		connectPage = new JFrame("AbDominate! Fitness Client");
		userInfoPage = new JFrame("AbDominate! Fitness Client");
		addPersonPage = new JFrame("AbDominate! Fitness Client - Add New Member");
		delPersonPage = new JFrame("AbDominate! Fitness Client - Delete Member");
		findPersonPage = new JFrame("AbDominate! Fitness Client - Member Information");
		connectPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connectPage.setSize(800,300);
		userInfoPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userInfoPage.setSize(800,300);
		
		addPersonPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addPersonPage.setSize(1500,300);
		delPersonPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		delPersonPage.setSize(800,300);
		findPersonPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		findPersonPage.setSize(800,300);
		
	
		Client fc = new Client();
		
		//create all of the frames
		createConnectionPage(fc);
		createPersonInfoPage(fc);
		createAddPersonPage(fc);
		createDelPersonPage(fc);
		createFindPersonPage(fc);
		
		connectPage.setVisible(true);
	}

		
	
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
		// menubar buttton to disconnect
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Manage Connections");
		JMenuItem disconn = new JMenuItem("Disconnect");
		menu.add(disconn);
		menubar.add(menu);
		disconn.addActionListener(fc);
		userInfoPage.setJMenuBar(menubar);		
		
		JPanel options = new JPanel();
		JPanel options2 = new JPanel();
				
		//three, each will have their own display
		JButton addbtn = new JButton("Add new AbDominate Member");
		JButton delbtn = new JButton("Remove Member");
		JButton findbtn = new JButton("Find Member Information");
		addbtn.addActionListener(fc);
		delbtn.addActionListener(fc);
		findbtn.addActionListener(fc);
		options.add(addbtn);
		options.add(delbtn);
		options2.add(findbtn);
		
		userInfoPage.add(options);
		userInfoPage.add(options2);

		userInfoPage.getContentPane().add(BorderLayout.NORTH, options);
		userInfoPage.getContentPane().add(BorderLayout.CENTER, options2);
		
		return;
	}
	
	public static void createAddPersonPage(Client fc) {		
		//5 text fields for Name, Address, Phone, Email, Membership type
		//button for adding person
		//back button
		JPanel mainP = new JPanel();
		
		String[] memTypes = new String[] {"All-inclusive", "Gym + Pool", "Pool-Only", "Gym-Only", "Fitness Classes"};
		 
		// create a combo box with the fixed array:
		comboMemberships = new JComboBox<String>(memTypes);
		
		JLabel nameL = new JLabel("Name: ");
		addnametxt = new JTextArea(1, 15);
		
		JLabel addressL = new JLabel("Address: ");
		addresstxt = new JTextArea(1, 20);
		
		JLabel phoneL = new JLabel("Phone: ");
		phonetxt = new JTextArea(1, 9);
		
		JLabel emailL = new JLabel("Email: ");
		emailtxt = new JTextArea(1, 20);
		
		JLabel memberL = new JLabel("Membership type: ");
		
		JButton addbtn = new JButton("Add New Member");
		addbtn.addActionListener(fc);  
		
		
		mainP.add(nameL);
		mainP.add(addnametxt);
		mainP.add(addressL);
		mainP.add(addresstxt);
		mainP.add(phoneL);
		mainP.add(phonetxt);
		mainP.add(emailL);
		mainP.add(emailtxt);
		mainP.add(memberL);
		mainP.add(comboMemberships);
		mainP.add(addbtn);
		
		//results section
		JPanel resultsP = new JPanel();
		addresults = new JLabel("");
		resultsP.add(addresults);
				
		
		JPanel backP = new JPanel();
		JButton backbtn = new JButton("Back");
		backbtn.addActionListener(fc);
		backP.add(backbtn);
		
		addPersonPage.add(mainP);
		addPersonPage.add(resultsP);
		
		addPersonPage.getContentPane().add(BorderLayout.NORTH, mainP);
		addPersonPage.getContentPane().add(BorderLayout.CENTER, resultsP);
		addPersonPage.getContentPane().add(BorderLayout.SOUTH, backP);
		
		return;
	}
	
	public static void createDelPersonPage(Client fc) {
		// menubar buttton to disconnect
		JPanel mainP = new JPanel();
		JLabel nameL = new JLabel("Name of Member to be removed: ");
		delnametxt = new JTextArea(1, 15);
		JButton delbtn = new JButton("Remove From Database");
		delbtn.addActionListener(fc);  
		
		mainP.add(nameL);
		mainP.add(delnametxt);
		mainP.add(delbtn);
		
		//results section
		JPanel resultsP = new JPanel();
		delresults = new JLabel("");
		resultsP.add(delresults);
		
		
		//back button
		JPanel backP = new JPanel();
		JButton backbtn = new JButton("Back");
		backbtn.addActionListener(fc);  
		backP.add(backbtn);
		
		
		delPersonPage.add(mainP);
		delPersonPage.add(resultsP);
		delPersonPage.add(backP);
		delPersonPage.getContentPane().add(BorderLayout.NORTH, mainP);
		delPersonPage.getContentPane().add(BorderLayout.CENTER, resultsP);
		delPersonPage.getContentPane().add(BorderLayout.SOUTH, backP);
		return;
	}
	
	public static void createFindPersonPage(Client fc) {
		// enter info
		JPanel mainP = new JPanel();
		JLabel nameL = new JLabel("Name of Member: ");
		findnametxt = new JTextArea(1, 15);
		JButton searchbtn = new JButton("Search Database");
		searchbtn.addActionListener(fc);  
		
		mainP.add(nameL);
		mainP.add(findnametxt);
		mainP.add(searchbtn);
		
		//label panel to display information
		JPanel infoP = new JPanel();
		received_nameL = new JLabel("");
		received_addressL = new JLabel("");
		received_phoneL = new JLabel("");
		received_emailL = new JLabel("");
		received_fitnessL = new JLabel("");
		
		infoP.add(received_nameL);
		infoP.add(received_addressL);
		infoP.add(received_phoneL);
		infoP.add(received_emailL);
		infoP.add(received_fitnessL);
		
		//back button
		JPanel backP = new JPanel();
		JButton backbtn = new JButton("Back");
		backbtn.addActionListener(fc);  
		backP.add(backbtn);
		
		
		findPersonPage.add(mainP);
		findPersonPage.add(infoP);
		findPersonPage.add(backP);
		findPersonPage.getContentPane().add(BorderLayout.NORTH, mainP);
		findPersonPage.getContentPane().add(BorderLayout.CENTER, infoP);
		findPersonPage.getContentPane().add(BorderLayout.SOUTH, backP);
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
		else if(event.contentEquals("Disconnect")) {
			try {
				userInfoPage.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		else if(event.contentEquals("Add new AbDominate Member")) {
			addPersonPage.setVisible(true);
		}
		else if(event.contentEquals("Add New Member")) {
			//combine all inputs into one string
			String memType = (String) comboMemberships.getSelectedItem();
			if(addnametxt.getText().equals("") || addresstxt.getText().equals("") || phonetxt.getText().equals("") || emailtxt.getText().equals("") || (memType).equals("")) {
				addresults.setText("Please enter all fields");
			}
			else {
				String combined = "ADD," + addnametxt.getText() + "," + addresstxt.getText() + "," + phonetxt.getText() + "," + emailtxt.getText() + "," + memType;
				System.out.println(combined);
				//send string to server
				ps.println(combined);
				//clear txt boxes
				addnametxt.setText(null);
				addresstxt.setText(null);
				phonetxt.setText(null);
				emailtxt.setText(null);
				
				addPersonPage.dispose();
			}
		}
		else if(event.contentEquals("Back")) {
			addPersonPage.dispose();
			delPersonPage.dispose();
			findPersonPage.dispose();
		}
		else if(event.contentEquals("Remove Member")) {
			delPersonPage.setVisible(true);
		}
		else if(event.contentEquals("Remove From Database")) {
			//combine name with DEL command
			String name = delnametxt.getText();
			String combined = "DEL," + name;
			ps.println(combined);
			delresults.setText(name + " has been deleted from the database");
		}
		else if(event.contentEquals("Find Member Information")) {
			findPersonPage.setVisible(true);
		}
		else if(event.contentEquals("Search Database")) {
			//combine name with DEL command
			String name = findnametxt.getText();
			String combined = "FIND," + name;

			System.out.println("Person to be found: " + name);
			ps.println(combined);
			
			//receive info from server, put into label values
			try {
				String result = in.readLine();
				if(result.equals("Member does not exist")) {
					received_nameL.setText(result);
					received_addressL.setText("");
					received_phoneL.setText("");
					received_emailL.setText("");
					received_fitnessL.setText("");
				}
				else {
				String[] personInfo = result.split(",");
				received_nameL.setText("Name: " + personInfo[0]);
				received_addressL.setText("| Address: " + personInfo[1]);
				received_phoneL.setText("| Phone Number: " + personInfo[2]);
				received_emailL.setText("| Email: " + personInfo[3]);
				received_fitnessL.setText("| Membership type: " + personInfo[4]);
				System.out.println("Received: " + result);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
			//clear variable from the textbox
			findnametxt.setText(null);
		}
	}
	
	public void connect(int port, String host) throws Exception{
		Socket myClient = null;
		try {
			myClient = new Socket(host, port);
			connectPage.dispose();
			userInfoPage.setVisible(true);
			ps = new PrintStream(myClient.getOutputStream());
			in = new BufferedReader(new InputStreamReader(myClient.getInputStream()));
			System.out.println("Client connected to server at: " + myClient);
		}catch(Exception e) {
			status.setText("Server not active");
			System.out.println("Could not connect to server");
		}
		status.setText("Server connected on port: " +port);		
		
	}
	

}
