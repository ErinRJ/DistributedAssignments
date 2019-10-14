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

   public String button1() throws RemoteException{
      return "Button 1 has been pressed";
   }
   public String button2() throws RemoteException{
      return "Button 2 has been pressed";
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