import java.net.Socket;

import java.util.Arrays; 

import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class Connector implements Runnable {
	static Socket client = null;
	//initialize input streams from the serialized arraylist
	static FileInputStream fis =null;
	static ObjectInputStream ois = null;
	
	static FileOutputStream fos = null;
	static ObjectOutputStream oos = null;
	
	static ObjectInputStream employee_ois = null;
	static FileInputStream employee_fis = null;
	
	static ArrayList<Person> database = null;
	static ArrayList<Employees> empdb = null;
	
	Connector(Socket client){
		this.client = client;
	}
	
	public void checkExists() throws IOException, FileNotFoundException, ClassNotFoundException{
		System.out.println("Checking for exisiting databases");
		// Open existing fitness patrons database, if it doesn't exist, create one
		File tempfile = new File("fitnessDB.out");
		if(!tempfile.exists()) {
			System.out.println("Creating a new database: fitnessDB.out");
			database = new ArrayList<Person>();
			serializeDb();
		}
		else {
			//open the serialized arraylist of fitness members (objects)
			fis = new FileInputStream("fitnessDB.out");
			ois = new ObjectInputStream(fis);
			database = (ArrayList) ois.readObject();
			System.out.println("Connecting to Employee database");
		}	
		
		File emp_tempfile= new File("Employees.out");
		if(!tempfile.exists()) {
			System.out.println("Creating a new database: Employees.out");
			empdb= new ArrayList<Employees>();
			serializeDb();
		}
		else {
			//do the same for the serialized arraylist of employee objects
			employee_fis = new FileInputStream("Employees.out");
			employee_ois = new ObjectInputStream(employee_fis);
			empdb = (ArrayList) employee_ois.readObject();
		}	
		
	}
	
	public void filter(String text, BufferedReader in, PrintStream out) throws IOException, NoSuchAlgorithmException, ClassNotFoundException{
		//split the string by comma delimiter
		String[] personInfo = text.split(",");
		if(personInfo[0].equals("ADD")) {
			inputPerson(personInfo, in, out);
		}
		else if(personInfo[0].equals("DEL")) {
			deletePerson(personInfo[1], in, out);
		}
		else if(personInfo[0].equals("FIND")) {
			findPerson(personInfo[1], in, out);
		}
		else if(personInfo[0].equals("LOGIN")) {
			login(personInfo, in, out);
		}
	}
	
	public static void inputPerson(String[] info, BufferedReader in, PrintStream out) throws IOException, FileNotFoundException {	
		//put array elements into the Person object
		database.add(new Person(info[1], info[2], info[3], info[4], info[5]));
		serializeDb();
		System.out.println("Added new member to the database");
	}
	
	public static void deletePerson(String name, BufferedReader in, PrintStream out) {
		System.out.println("Looking for person to delete...");
		for(int i=0; i < database.size(); i++) {
			if((database.get(i).name).equals(name)) {
				try {
					database.remove(i);
					serializeDb();
					System.out.println(name + " has been removed from the database");
					break;
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void findPerson(String name, BufferedReader in, PrintStream out) {
		boolean doesExist = false;
		for(int i=0; i < database.size(); i++) {
			if((database.get(i).name).equals(name)) {
				//send info to client
				String tempString = database.get(i).name + "," + database.get(i).address + "," + database.get(i).phoneNum + "," + database.get(i).email + "," + database.get(i).memType; 
				out.println(tempString);
				doesExist = true;
				System.out.println(tempString + " :has been found and sent to client");
				break;
			}
		}
		if (!doesExist) {
			out.println("Member does not exist");
		}
	}
	
	public static void login(String[] emp, BufferedReader in, PrintStream out) throws NoSuchAlgorithmException, IOException, ClassNotFoundException{
		//call generateHash(), then bytestoStringHex()
		//compare to arraylist of employees
		String username = emp[1];
		String password = emp[2];
		boolean doesExist = false;
		int employee_id = 0;
		//search for employee in database
		for(int i=0; i < empdb.size(); i++) {
			System.out.println("Looking through the arraylist: " + empdb.get(i).username);
			if((empdb.get(i).username).equals(username)) {
				//send info to client
				employee_id = i;
				doesExist = true;
				System.out.println(empdb.get(i).username + " is a registered employee");
				break;
			}
			if (!doesExist) {
				System.out.println("Employee does not exist");
			}
		}
		
		String hash = generateHash(password, "MD5");
		if(hash.equals(empdb.get(employee_id).hash)) {
			System.out.println("Access granted!");
			out.println("Access granted");
		}
		else {
			System.out.println("Access denied.");
			out.println("Access denied");
		}
		
		
	}
	
	private static String generateHash(String data, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		byte[] hash = digest.digest(data.getBytes());
		System.out.println("byte array hash: " + hash);
		return bytesToStringHex(hash);
	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	private static String bytesToStringHex(byte[] bytes) {
		char [] hexChars = new char[bytes.length*2];
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i*2] = hexArray[v >>> 4];
			hexChars[i*2+1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	
	public static void serializeDb() throws IOException {
		fos = new FileOutputStream("fitnessDB.out");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(database);
		oos.flush();
	}
	
	
	public void run(){
		try {
			System.out.println("client connected: " + client);
			
			// STARTUP COMMUNICATION CHANNELS

			BufferedReader in = null;
			PrintStream out = null;
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());
			
			//make sure the patron database already exists
			checkExists();
			while(true) {
				String text = in.readLine();
				System.out.println("Received: " + text);
				//determine the command sent from the client
				try {
					filter(text, in, out);
				}catch(NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}	

			
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
			
		
		try {
			// close output to serialized file
			oos.close();
			fos.close();
		////		 close input from serialized file
//			ois.close();
//			fis.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
