import java.io.BufferedReader;
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

public class mockDB {
	
	static ArrayList<Person> database = null;
	static Socket client = null;
	static ServerSocket myServer = null;
	static BufferedReader in = null;
	static FileOutputStream fos = null;
	static ObjectOutputStream oos = null;
	
	public static void inputPerson(String text) throws IOException {	
		//split the string by comma delimiter
		String[] tempString = text.split(",");
		
		//put array elements into the Person object
		database.add(new Person(tempString[0], tempString[1], tempString[2], tempString[3], tempString[4]));
		serializeDb();
	}
	
	public static void serializeDb() throws IOException {
		fos = new FileOutputStream("fitnessDB.out");
		oos = new ObjectOutputStream(fos);
		oos.writeObject(database);
		oos.flush();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//initialize input streams from the serialized arraylist
		FileInputStream fis =null;
		ObjectInputStream ois = null;
		String text = null;
		
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
		
		// CONNECT TO THE CLIENT
		myServer = new ServerSocket(5000);
		System.out.println("Server started on port 5000");
		client = myServer.accept();
		System.out.println("client connected: " + client);
		
		// STARTUP COMMUNICATION CHANNELS
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		text = in.readLine();
		System.out.println("Received: " + text);
		//input the person into the database
		inputPerson(text);
		

		//test ---- how to fetch a value of the arraylist
		System.out.println(database.get(1).address);
			
		
		
		//search through arraylist for correct name
		
		// could include functionality to create a txt file of 
		//      all fitness members in a specific fitness class
		
		// close output to serialized file
		oos.close();
		fos.close();
//		 close input from serialized file
		ois.close();
		fis.close();
	}
}

