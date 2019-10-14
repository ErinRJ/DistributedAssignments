import java.io.*; 
import java.rmi.*;

public class FileClient{
   public static void main(String argv[]) {
      try {
		  //connect to the registry
         FileInterface fi = (FileInterface) Naming.lookup(FileInterface.SERVICENAME);
		 //call function and print out result
		 String result = fi.hello();
		 System.out.println(result);
         
      } catch(Exception e) {
         System.err.println("FileServer exception: "+ e.getMessage());
         e.printStackTrace();
      }
   }
}