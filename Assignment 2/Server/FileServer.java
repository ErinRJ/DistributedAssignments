import java.io.*;
import java.rmi.*;

public class FileServer {
   public static void main(String argv[]) {
	   //create and bind to registry
      try {
		  //create object of implementation class
         FileImpl imp = new FileImpl();
		 //bind the object to the registry
		 Naming.rebind(FileInterface.SERVICENAME, imp);
      } catch(Exception e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
      }
   }
}