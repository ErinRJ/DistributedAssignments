import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;


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

   //check if the database already exists, if not create one
   private void checkExists() throws IOException, ClassNotFoundException {
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

   //save the state of the database to an *.out file
    public static void serializeDb() throws IOException {
        fos = new FileOutputStream("postings.out");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(postingsDB);
        oos.flush();
    }

    //CREATE POST == DONE
   public void createPost(String location, String dog, String duration, String owner) throws RemoteException{
       //state the information received from the client
       System.out.println("RECEIVED: " + location + ", " + dog + ", " + duration + "," + owner);
       int size = postingsDB.size() + 1;
       //add the new information to the database of postings
       postingsDB.add(new Posting(size, location, dog, duration, owner, false, "null"));
       //save the results by serializing it
       try {
           serializeDb();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public String[] viewPosts() throws RemoteException{
       //check if database is empty
       //needs to be updated to include already marked ones
       if(postingsDB.size() == 0){
           System.out.println("There are no available postings at the moment");
           String[] tempArray = new String[1];
           tempArray[0] = "No Available Postings";
           return tempArray;
       }
       else {
           //return postingsDB as an array
           String[] array;
           array = new String[postingsDB.size()];
           for (int i = 0; i < postingsDB.size(); i++) {
               array[i] = String.valueOf(postingsDB.get(i).id) + "," + postingsDB.get(i).location + ","
                       + postingsDB.get(i).dogB + "," + postingsDB.get(i).duration + "," + postingsDB.get(i).accepted;
           }
           return array;
       }
   }

   //find all of the posted submitted by the user
   public Object[] viewPersonalPosts(String owner) throws RemoteException{
       //search through the database, collect all postings by that owner
       //put in arraylist, then array
       ArrayList<String> list = new ArrayList<String>();
       for (Posting posting : postingsDB) {
           if ((posting.owner).equals(owner)) {
               list.add(String.valueOf(posting.id) + "," + posting.location + ","
                       + posting.dogB + "," + posting.duration + "," + posting.owner + ","
                       + posting.accepted + "," + posting.sitter);
           }
       }
       Object[] array = list.toArray();
       return array;
   }

   //find all the jobs taken by the sitter
    public Object[] viewJobsTaken(String sitter) throws RemoteException{
        //search through the database, collect all jobs taken by that sitter
        ArrayList<String> list = new ArrayList<String>();
        for (Posting posting : postingsDB) {
            if ((posting.sitter).equals(sitter)) {
                list.add(String.valueOf(posting.id) + "," + posting.location + ","
                        + posting.dogB + "," + posting.duration + "," + posting.owner + ","
                        + posting.accepted + "," + posting.sitter);
            }
        }
        Object[] array = list.toArray();
        return array;
    }

   //REMOVE POST == DONE
   public void removePost(int job, String sitter) throws RemoteException{
       //find the job, set the accepted flag to true and the sitter name. Don't remove from the database
       for(int i=0; i<postingsDB.size(); i++){
           if(postingsDB.get(i).id == (job)){
               postingsDB.get(i).accepted = true;
               postingsDB.get(i).sitter = sitter;
               System.out.println("Job with id: " + job + "has ben taken by: " + sitter);
               System.out.println("New position values: accepted: " + postingsDB.get(i).accepted + "sitter: " + postingsDB.get(i).sitter);
               //stop the search
               break;
           }
       }
       //save the updated information
       try {
           serializeDb();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public String button4() throws RemoteException{
      return "Button 4 has been pressed";
   }
   public String button5() throws RemoteException{
      return "Button 5 has been pressed";
   }
}