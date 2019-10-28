import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

//this class binds the implementation class to the registry
public class FileServer {
   public static void main(String argv[]) {
         System.out.println("Server started");

	   //create and bind to registry
      try {
		  //create object of interface/implementation class
         FileInterface imp = new FileImpl();
		 //bind the object to the registry
		 Naming.rebind(FileInterface.SERVICENAME, imp);
         System.out.println("Everything is okay");
      } catch(Exception e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
      }
   }
}