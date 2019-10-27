import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.*;
import java.util.ArrayList;


public class FileImpl extends UnicastRemoteObject implements FileInterface {

    //declare postings 'database'
    static ArrayList<Posting> postingsDB = null;
    //to communicate with the postings database
    static FileInputStream fis =null;
    static ObjectInputStream ois = null;
    static FileOutputStream fos = null;
    static ObjectOutputStream oos = null;

	//constructor
   public FileImpl() throws IOException, ClassNotFoundException {
       super();
       checkExists();
   }

   public void checkExists() throws IOException, ClassNotFoundException {
       System.out.println("Checking for exisiting databases");
       // Open existing fitness patrons database, if it doesn't exist, create one
       File tempfile = new File("postings.out");
       if(!tempfile.exists()) {
           System.out.println("Creating a new database: postings.out");
           postingsDB = new ArrayList<Posting>();
           serializeDb();
       }
       else {
           //open the serialized arraylist of dog sitting postings
           fis = new FileInputStream("postings.out");
           ois = new ObjectInputStream(fis);
           postingsDB = (ArrayList) ois.readObject();
           System.out.println("Connecting to postings database");
       }
   }

    public static void serializeDb() throws IOException {
        fos = new FileOutputStream("postings.out");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(postingsDB);
        oos.flush();
    }

   public String createPost(String location, String dog, String duration) throws RemoteException{
       //state the information received from the client
       System.out.println("RECEIVED: " + location + ", " + dog + ", " + duration);
       //add the new information to the database of postings
       postingsDB.add(new Posting(location, dog, duration));
       //save the results by serializing it
       try {
           serializeDb();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
   public String viewPosts() throws RemoteException{
       return null;
   }
   public String button3() throws RemoteException{
      return "Button 3 has been pressed";
   }
   public String button4() throws RemoteException{
      return "Button 4 has been pressed";
   }
   public String button5() throws RemoteException{
      return "Button 5 has been pressed";
   }
}