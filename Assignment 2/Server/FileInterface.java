import java.rmi.Remote;
import java.rmi.RemoteException;
public interface FileInterface extends Remote {
   public final static String SERVICENAME = "hello";

   public String createPost(String location, String dog, String duration) throws RemoteException;
   public String viewPosts() throws RemoteException;
   public String button3() throws RemoteException;
   public String button4() throws RemoteException;
   public String button5() throws RemoteException;

}