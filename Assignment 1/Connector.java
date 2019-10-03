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


public class Connector implements Runnable {
	static Socket client = null;
	//initialize input streams from the serialized arraylist
	static FileInputStream fis =null;
	static ObjectInputStream ois = null;
	static FileOutputStream fos = null;
	static ObjectOutputStream oos = null;
	static ArrayList<Person> database = null;
	
	
	Connector(Socket client){
		this.client = client;
	}
	
	public void checkExists() throws IOException, FileNotFoundException, ClassNotFoundException{
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
		}		
		
		//also create an employees database?
	}
	
	public void filter(String text, BufferedReader in, PrintStream out) throws IOException{
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
			
			//CHECK LOGIN INFORMATION
			//accept input
			//filter to login()
			//within login(), call generateHash(), bytestoStringHex(), and check values against employees class
			
			checkExists();
			while(true) {
				String text = in.readLine();
				System.out.println("Received: " + text);
				//determine the command
				filter(text, in, out);
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
