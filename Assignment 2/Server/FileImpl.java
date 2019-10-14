import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class FileImpl extends UnicastRemoteObject implements FileInterface {
	
	//constructor
   public FileImpl() throws RemoteException{
      super();
   }
	//method to be called from client
   public String hello()throws RemoteException{
      try {
         return("Hello from the implementation class");
      } catch(Exception e){
         System.out.println("FileImpl: "+e.getMessage());
         e.printStackTrace();
         return(null);
      }
   }
}