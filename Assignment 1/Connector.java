import java.net.Socket;
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
	static BufferedReader in = null;
	static FileOutputStream fos = null;
	static ObjectOutputStream oos = null;
	static ArrayList<Person> database = null;
	
	
	Connector(Socket client){
		this.client = client;
	}
	
	public void checkExists() throws IOException, FileNotFoundException, ClassNotFoundException{
		// Open existing database, if it doesn't exist, create one
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
	}
	
	public void filter(String text) throws IOException{
		//split the string by comma delimiter
		String[] personInfo = text.split(",");
		if(personInfo[0].equals("ADD")) {
			inputPerson(personInfo);
		}
		else if(personInfo[0].equals("DEL")) {
			deletePerson(personInfo[1]);
		}
	}
	
	public static void inputPerson(String[] info) throws IOException, FileNotFoundException {	
		
		//put array elements into the Person object
		database.add(new Person(info[1], info[2], info[3], info[4], info[5]));
		serializeDb();
	}
	
	public static void deletePerson(String name) {
		for(int i=0; i < database.size(); i++) {
			if((database.get(0).name).equals(name)) {
				database.remove(0);
			}
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
			checkExists();
			
			// STARTUP COMMUNICATION CHANNELS
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//could put while loop here
			String text = in.readLine();
			System.out.println("Received: " + text);
			//determine the command
			filter(text);
//				
//			//test ---- how to fetch a value of the arraylist
			System.out.println(database.get(0).address);
//				
			
			
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
